package com.greeni.api.activities.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.greeni.api.activities.converter.ActivityConverter;
import com.greeni.api.activities.domain.Activity;
import com.greeni.api.activities.dto.ActivityResponseDTO;
import com.greeni.api.activities.repository.ActivityRepository;
import com.greeni.api.apiPayload.handler.GeneralException;
import com.greeni.api.apiPayload.status.ProfileErrorStatus;
import com.greeni.api.profiles.domain.Profile;
import com.greeni.api.profiles.repository.ProfileRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ActivityServiceImpl implements ActivityService {

	private final ActivityRepository activityRepository;
	private final ProfileRepository profileRepository;

	@Override
	@Transactional(readOnly = true)
	public ActivityResponseDTO.DailyList getDailyActivityList(Long memberId, Long profileId) {

		Profile profile = profileRepository.findById(profileId)
			.orElseThrow(() -> new GeneralException(ProfileErrorStatus.PROFILE_NOT_FOUND));

		if (!profile.getMember().getId().equals(memberId)) {
			throw new GeneralException(ProfileErrorStatus.UNAUTHORIZED_PROFILE_ACCESS);
		}

		LocalDate today = LocalDate.now();
		LocalDateTime startTime = today.atStartOfDay();
		LocalDateTime endTime = today.atTime(LocalTime.MAX);

		List<Activity> activityList = activityRepository
			.findTop3ByProfileAndCreatedAtBetweenOrderByCreatedAtDesc(profile, startTime, endTime);

		return ActivityConverter.toDailyActivityListResponseDTO(activityList);
	}
}
