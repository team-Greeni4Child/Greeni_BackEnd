package com.greeni.api.diaries.repository;

import com.greeni.api.diaries.domain.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface DiaryRepository extends JpaRepository<Diary, Long> {

    int countByProfileId(Long profileId);

    Optional<Diary> findTopByProfileIdAndCreatedAtBetweenOrderByCreatedAtDesc(Long profileId, LocalDateTime start, LocalDateTime end);
}
