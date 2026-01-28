package com.greeni.api.diaries.service;

import com.greeni.api.apiPayload.handler.GeneralException;
import com.greeni.api.apiPayload.status.DiaryErrorStatus;
import com.greeni.api.apiPayload.status.ProfileErrorStatus;
import com.greeni.api.diaries.converter.DiaryConverter;
import com.greeni.api.diaries.domain.Diary;
import com.greeni.api.diaries.dto.DiaryResponseDTO;
import com.greeni.api.diaries.repository.DiaryRepository;
import com.greeni.api.profiles.domain.Profile;
import com.greeni.api.profiles.repository.ProfileRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DiaryServiceImpl implements DiaryService {

    private final ProfileRepository profileRepository;
    private final DiaryRepository diaryRepository;

    // 오늘의 일기 키워드 조회
    @Override
    public DiaryResponseDTO.GetTodayDiaryKeywordResponse getTodayDiaryKeyword(Long memberId, Long profileId) {
        // Profile 엔티티 조회
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new GeneralException(ProfileErrorStatus.PROFILE_NOT_FOUND));

        // 본인 Profile인지 검증
        if (!profile.getMember().getId().equals(memberId)) {
            throw new GeneralException(ProfileErrorStatus.UNAUTHORIZED_PROFILE_ACCESS);
        }

        LocalDate today = LocalDate.now(ZoneId.of("Asia/Seoul"));
        LocalDateTime start = today.atStartOfDay();
        LocalDateTime end = today.plusDays(1).atStartOfDay();

        // Diary 엔티티 조회
        Diary diary = diaryRepository.
                findTopByProfileIdAndCreatedAtBetweenOrderByCreatedAtDesc(profileId, start, end)
                .orElseThrow(() -> new GeneralException(DiaryErrorStatus.DIARY_NOT_FOUND_TODAY));

        // DTO 변환 후 반환
        return DiaryConverter.toTodayDiaryKeywordResponseDTO(profileId, diary.getKeyword());
    }
}
