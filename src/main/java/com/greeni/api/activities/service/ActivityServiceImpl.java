package com.greeni.api.activities.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.greeni.api.activities.converter.ActivityConverter;
import com.greeni.api.activities.domain.Activity;
import com.greeni.api.activities.domain.enums.ActivityType;
import com.greeni.api.activities.dto.ActivityRequestDTO;
import com.greeni.api.activities.dto.ActivityResponseDTO;
import com.greeni.api.activities.repository.ActivityRepository;
import com.greeni.api.apiPayload.handler.GeneralException;
import com.greeni.api.apiPayload.status.ProfileErrorStatus;
import com.greeni.api.badges.service.BadgeService;
import com.greeni.api.profiles.domain.Profile;
import com.greeni.api.profiles.repository.ProfileRepository;
import com.greeni.api.profiles.service.ProfileQueryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ActivityServiceImpl implements ActivityService {

	private final ProfileRepository profileRepository;
	private final ActivityRepository activityRepository;
	private final ProfileQueryService profileQueryService;
	private final BadgeService badgeService;

	// 활동 요약 목록 조회
	@Override
	@Transactional(readOnly = true)
	public ActivityResponseDTO.GetActivitySummaryListResponse getActivitySummaryList(Long memberId, Long profileId,
		LocalDateTime cursorCreatedAt, Long cursorId, int size) {
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
			.collect(Collectors.groupingBy(activity -> activity.getCreatedAt().toLocalDate(), LinkedHashMap::new,
				Collectors.toList()));

		List<ActivityResponseDTO.DailyActivityGroup> days = grouped.entrySet().stream()
			.map(g -> ActivityConverter.toDailyActivityGroupDTO(g.getKey(), g.getValue()))
			.toList();

		// DTO 변환 후 반환
		return ActivityConverter.toGetDailyActivityListResponseDTO(days, nextCursorCreatedAt, nextCursorId, hasNext);
	}

	// 일일 활동 요약 목록 조회
	@Override
	@Transactional(readOnly = true)
	public ActivityResponseDTO.DailyList getDailyActivityList(Long memberId, Long profileId) {

		// 프로필 추출 및 검증
		Profile profile = profileQueryService.findProfileAndValidate(profileId, memberId);

		// 오늘 날짜 추출
		LocalDate today = LocalDate.now();
		LocalDateTime startTime = today.atStartOfDay();
		LocalDateTime endTime = today.atTime(LocalTime.MAX);

		List<Activity> activityList = activityRepository
			.findTop3ByProfileAndCreatedAtBetweenOrderByCreatedAtDesc(profile, startTime, endTime);

		return ActivityConverter.toDailyActivityListResponseDTO(activityList);
	}

	// 다섯고개 활동요약 생성
	@Override
	public ActivityResponseDTO.ActivityCreateResponse createFiveQuestionsActivity(Long memberId,
		ActivityRequestDTO.FiveQuestionCreateRequest request) {

		// 프로필 추출 및 검증
		Profile profile = profileQueryService.findProfileAndValidate(request.profileId(), memberId);

		// description과 새 활동요약 생성
		String description =
			request.count() == 0 ? "다섯고개에서 정답을 맞히지 못했어요." : "다섯고개에서 " + request.count() + "턴만에 정답을 맞혔어요.";
		Activity newActivity = ActivityConverter.toActivity(
			ActivityType.FIVE_QUESTIONS, description, profile, null
		);

		// 오늘의 첫 활동요약인지 확인하고 저장
		//checkTodayActivity(profile);
		checkTodayActivity(profile.getId());
		activityRepository.save(newActivity);

		// 다섯고개 배지 확인
		badgeService.checkAndAwardBadge(profile, ActivityType.FIVE_QUESTIONS);

		return ActivityConverter.toActivityCreateResponseDTO(newActivity);
	}

	// 역할놀이 활동요약 생성
	@Override
	public ActivityResponseDTO.ActivityCreateResponse createRolePlayingActivity(Long memberId,
		ActivityRequestDTO.RolePlayingCreateRequest request) {

		// 프로필 추출 및 검증
		Profile profile = profileQueryService.findProfileAndValidate(request.profileId(), memberId);

		// description과 새 활동요약 생성
		String description = "역할놀이에서 " + request.roleName().getName() + " 역할을 맡았어요.";
		Activity newActivity = ActivityConverter.toActivity(
			ActivityType.ROLE_PLAYING, description, profile, null
		);

		// 오늘의 첫 활동요약인지 확인하고 저장
		//checkTodayActivity(profile);
		checkTodayActivity(profile.getId());
		activityRepository.save(newActivity);

		// 역할놀이 배지 확인
		badgeService.checkAndAwardBadge(profile, ActivityType.ROLE_PLAYING);

		return ActivityConverter.toActivityCreateResponseDTO(newActivity);
	}

	// 오늘의 첫 활동요약인지 확인
//	@Override
//	@Transactional
//	public void checkTodayActivity(Profile profile) {
//
//		// 오늘 날짜 추출
//		LocalDate today = LocalDate.now();
//		LocalDateTime startTime = today.atStartOfDay();
//		LocalDateTime endTime = today.atTime(LocalTime.MAX);
//
//		// 프로필 기준 오늘 날짜에 활동요약이 존재하지 않는다면
//		// 출석 일수 증가 및 출석 배지 확인
//		if (!activityRepository.existsByProfileAndCreatedAtBetween(profile, startTime, endTime)) {
//			System.out.println("hello world");
//			profile.attend();
//			System.out.println("attendance : " + profile.getAttendance());
//			badgeService.checkAndAwardBadge(profile, ActivityType.ATTENDANCE);
//		}
//	}

	@Override
	@Transactional
	public void checkTodayActivity(Long profileId){
		// 오늘 날짜 추출
		LocalDate today = LocalDate.now();
		LocalDateTime startTime = today.atStartOfDay();
		LocalDateTime endTime = today.atTime(LocalTime.MAX);

		Profile profile = profileRepository.findById(profileId)
				.orElseThrow(() -> new GeneralException(ProfileErrorStatus.PROFILE_NOT_FOUND));

		// 프로필 기준 오늘 날짜에 활동요약이 존재하지 않는다면
		// 출석 일수 증가 및 출석 배지 확인
		if (!activityRepository.existsByProfileAndCreatedAtBetween(profile, startTime, endTime)) {
			System.out.println("hello world");
			profile.attend();
			System.out.println("attendance : " + profile.getAttendance());
			badgeService.checkAndAwardBadge(profile, ActivityType.ATTENDANCE);
		}
	}
}
