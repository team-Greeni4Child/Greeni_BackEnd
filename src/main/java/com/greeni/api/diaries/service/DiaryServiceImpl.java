package com.greeni.api.diaries.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.greeni.api.apiPayload.handler.GeneralException;
import com.greeni.api.apiPayload.status.DiaryErrorStatus;
import com.greeni.api.apiPayload.status.ProfileErrorStatus;
import com.greeni.api.diaries.converter.DiaryConverter;
import com.greeni.api.diaries.domain.Diary;
import com.greeni.api.diaries.dto.DiaryResponseDTO;
import com.greeni.api.diaries.repository.DiaryRepository;
import com.greeni.api.profiles.domain.Profile;
import com.greeni.api.profiles.repository.ProfileRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class DiaryServiceImpl implements DiaryService {

	private final ProfileRepository profileRepository;
	private final DiaryRepository diaryRepository;

	// 오늘의 일기 키워드 조회
	@Override
	@Transactional(readOnly = true)
	public DiaryResponseDTO.GetTodayDiaryKeywordResponse getTodayDiaryKeyword(Long memberId, Long profileId) {

		findProfileAndValidate(profileId, memberId);

		LocalDate today = LocalDate.now(ZoneId.of("Asia/Seoul"));
		LocalDateTime start = today.atStartOfDay();
		LocalDateTime end = today.plusDays(1).atStartOfDay();

		// Diary 엔티티 조회
		Diary diary = diaryRepository
			.findFirstByProfileIdAndCreatedAtBetween(profileId, start, end)
			.orElseThrow(() -> new GeneralException(DiaryErrorStatus.DIARY_NOT_FOUND_TODAY));

		// DTO 변환 후 반환
		return DiaryConverter.toTodayDiaryKeywordResponseDTO(profileId, diary.getKeyword());
	}

	// 이번 달 일기 감정 통계 조회
	@Override
	@Transactional(readOnly = true)
	public DiaryResponseDTO.GetMonthlyDiaryEmotionResponse getMonthlyDiaryEmotion(Long memberId, Long profileId) {

		findProfileAndValidate(profileId, memberId);

		LocalDate now = LocalDate.now(ZoneId.of("Asia/Seoul"));
		LocalDate firstDay = now.withDayOfMonth(1);
		LocalDate firstDayNextMonth = firstDay.plusMonths(1);

		LocalDateTime start = firstDay.atStartOfDay();
		LocalDateTime end = firstDayNextMonth.atStartOfDay();

		// Diary 리스트 조회
		List<Diary> diaries = diaryRepository.findByProfileIdAndCreatedAtBetween(profileId, start, end);

		// DTO 변환 후 반환
		return DiaryConverter.toMonthlyDiaryEmotionResponseDTO(profileId, diaries);
	}

	@Override
	@Transactional(readOnly = true)
	public DiaryResponseDTO.MonthDiaryListDTO getMonthDiaryList(int year, int month, Long memberId, Long profileId) {

		findProfileAndValidate(profileId, memberId);

		// 월 검증
		if (month < 1 || month > 12) {
			throw new GeneralException(DiaryErrorStatus.INVALID_MONTH);
		}

		// 년도 검증
		YearMonth requestYm = YearMonth.of(year, month);
		YearMonth nowYm = YearMonth.now(ZoneId.of("Asia/Seoul"));

		if (requestYm.isAfter(nowYm)) {
			throw new GeneralException(DiaryErrorStatus.FUTURE_TIME);
		}

		LocalDate startDate = LocalDate.of(year, month, 1);
		LocalDate endDate = startDate.plusMonths(1);

		LocalDateTime start = startDate.atStartOfDay();
		LocalDateTime end = endDate.atStartOfDay();

		List<Diary> diaryList = diaryRepository.findByProfileIdAndCreatedAtGreaterThanEqualAndCreatedAtLessThanOrderByCreatedAtAsc(
			profileId, start, end);
		return DiaryConverter.toMonthDiaryListDTO(profileId, diaryList);
	}

	@Override
	@Transactional(readOnly = true)
	public DiaryResponseDTO.DailyDiaryDTO getDailyDiary(int year, int month, int day, Long memberId, Long profileId) {

		findProfileAndValidate(profileId, memberId);

		// 월 검증
		if (month < 1 || month > 12) {
			throw new GeneralException(DiaryErrorStatus.INVALID_MONTH);
		}

		// 날짜 검증
		YearMonth requestYm = YearMonth.of(year, month);
		int lastDay = requestYm.lengthOfMonth();
		if (day < 1 || day > lastDay) {
			throw new GeneralException(DiaryErrorStatus.INVALID_DAY);
		}

		// 미래 날짜 검증
		LocalDate requestDate = LocalDate.of(year, month, day);
		LocalDate today = LocalDate.now(ZoneId.of("Asia/Seoul"));

		if (requestDate.isAfter(today)) {
			throw new GeneralException(DiaryErrorStatus.FUTURE_TIME);
		}

		// 조회
		Diary diary = diaryRepository.findByProfileIdAndDiaryDate(profileId, requestDate)
			.orElseThrow(() -> new GeneralException(DiaryErrorStatus.NOT_WRITE_DIARY));

		return DiaryConverter.toDailyDiaryDTO(profileId, diary);
	}

	@Override
	public Profile findProfileAndValidate(Long profileId, Long memberId) {
		// Profile 엔티티 조회
		Profile profile = profileRepository.findById(profileId)
			.orElseThrow(() -> new GeneralException(ProfileErrorStatus.PROFILE_NOT_FOUND));

		// member의 profile인지 검증
		if (!profile.getMember().getId().equals(memberId)) {
			throw new GeneralException(ProfileErrorStatus.UNAUTHORIZED_PROFILE_ACCESS);
		}
		return profile;
	}

}
