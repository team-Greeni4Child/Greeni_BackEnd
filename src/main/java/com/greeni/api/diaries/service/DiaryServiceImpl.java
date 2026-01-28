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
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class DiaryServiceImpl implements DiaryService {

    private final DiaryRepository diaryRepository;
    private final ProfileRepository profileRepository;

    @Override
    @Transactional(readOnly=true)
    public DiaryResponseDTO.MonthDiaryListDTO getMonthDiaryList(int year, int month, Long memberId, Long profileId) {

        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new GeneralException(ProfileErrorStatus.PROFILE_NOT_FOUND));

        // member의 profile인지 검증
        if (!profile.getMember().getId().equals(memberId)) {
            throw new GeneralException(ProfileErrorStatus.UNAUTHORIZED_PROFILE_ACCESS);
        }

        // 월 검증
        if(month < 1 || month > 12){
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

        List<Diary> diaryList = diaryRepository.findByProfileIdAndCreatedAtGreaterThanEqualAndCreatedAtLessThanOrderByCreatedAtAsc(profileId, start, end);
        return DiaryConverter.toMonthDiaryListDTO(profileId, diaryList);
    }

}
