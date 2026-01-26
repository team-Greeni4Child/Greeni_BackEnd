package com.greeni.api.diaries.service;

import com.greeni.api.apiPayload.handler.GeneralException;
import com.greeni.api.apiPayload.status.ProfileErrorStatus;
import com.greeni.api.diaries.converter.DiaryConverter;
import com.greeni.api.diaries.domain.Diary;
import com.greeni.api.diaries.domain.enums.Emotion;
import com.greeni.api.diaries.dto.DiaryResponseDTO;
import com.greeni.api.diaries.repository.DiaryRepository;
import com.greeni.api.profiles.domain.Profile;
import com.greeni.api.profiles.repository.ProfileRepository;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class DiaryServiceImpl implements DiaryService {

    private final ProfileRepository profileRepository;
    private final DiaryRepository diaryRepository;

    // 이번 달 일기 감정 통계 조회
    @Override
    public DiaryResponseDTO.GetMonthlyDiaryEmotionResponse getMonthlyDiaryEmotion(Long memberId, Long profileId) {
        // Profile 엔티티 조회
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new GeneralException(ProfileErrorStatus.PROFILE_NOT_FOUND));

        // 본인 Profile인지 검증
        if (!profile.getMember().getId().equals(memberId)) {
            throw new GeneralException(ProfileErrorStatus.UNAUTHORIZED_PROFILE_ACCESS);
        }

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
}
