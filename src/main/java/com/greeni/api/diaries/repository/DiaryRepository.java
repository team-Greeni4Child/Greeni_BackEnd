package com.greeni.api.diaries.repository;

import com.greeni.api.diaries.domain.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;
import java.util.Optional;

public interface DiaryRepository extends JpaRepository<Diary, Long> {

    int countByProfileId(Long profileId);

    Optional<Diary> findFirstByProfileIdAndCreatedAtBetween(Long profileId, LocalDateTime start, LocalDateTime end);

    List<Diary> findByProfileIdAndCreatedAtBetween(Long profileId, LocalDateTime start, LocalDateTime end);

    List<Diary> findByProfileIdAndCreatedAtGreaterThanEqualAndCreatedAtLessThanOrderByCreatedAtAsc(Long profileId, LocalDateTime start, LocalDateTime end);

    Optional<Diary> findByProfileIdAndDiaryDate(Long profileId, LocalDate today);
}
