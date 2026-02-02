package com.greeni.api.activities.service;

import com.greeni.api.activities.converter.ActivityConverter;
import com.greeni.api.activities.domain.Activity;
import com.greeni.api.activities.dto.ActivityResponseDTO;
import com.greeni.api.activities.repository.ActivityRepository;
import com.greeni.api.apiPayload.handler.GeneralException;
import com.greeni.api.apiPayload.status.ProfileErrorStatus;
import com.greeni.api.profiles.domain.Profile;
import com.greeni.api.profiles.repository.ProfileRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ActivityServiceImpl implements ActivityService {

    private final ProfileRepository profileRepository;
    private final ActivityRepository activityRepository;

    // 활동 요약 목록 조회
    @Override
    @Transactional(readOnly = true)
    public ActivityResponseDTO.GetActivitySummaryListResponse getActivitySummaryList(Long memberId, Long profileId, LocalDateTime cursorCreatedAt, Long cursorId, int size) {
        // Profile 엔티티 조회
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new GeneralException(ProfileErrorStatus.PROFILE_NOT_FOUND));

        // 본인 Profile인지 검증
        if (!profile.getMember().getId().equals(memberId)) {
            throw new GeneralException(ProfileErrorStatus.UNAUTHORIZED_PROFILE_ACCESS);
        }

        // Activity 엔티티 조회
        Pageable pageable = PageRequest.of(0, size + 1);

        List<Activity> fetched;
        if (cursorCreatedAt == null || cursorId == null) {
            fetched = activityRepository.findFirstPage(profileId, pageable);
        } else {
            fetched = activityRepository.findNextPage(profileId, cursorCreatedAt, cursorId, pageable);
        }

        boolean hasNext = fetched.size() > size;
        List<Activity> activities = hasNext ? fetched.subList(0, size) : fetched;

        LocalDateTime nextCursorCreatedAt = null;
        Long nextCursorId = null;

        if (!activities.isEmpty()) {
            Activity last = activities.getLast();
            nextCursorCreatedAt = last.getCreatedAt();
            nextCursorId = last.getId();
        }

        // 날짜별 그룹핑
        LinkedHashMap<LocalDate, List<Activity>> grouped = activities.stream()
                .collect(Collectors.groupingBy(activity -> activity.getCreatedAt().toLocalDate(), LinkedHashMap::new, Collectors.toList()));

        List<ActivityResponseDTO.DailyActivityGroup> days = grouped.entrySet().stream()
                .map(g -> ActivityConverter.toDailyActivityGroupDTO(g.getKey(), g.getValue()))
                .toList();

        // DTO 변환 후 반환
        return ActivityConverter.toGetDailyActivityListResponseDTO(days, nextCursorCreatedAt, nextCursorId, hasNext);
    }

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
