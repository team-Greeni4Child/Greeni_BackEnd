package com.greeni.api.diaries.repository;

import com.greeni.api.diaries.domain.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface DiaryRepository extends JpaRepository<Diary, Long> {

    int countByProfileId(Long profileId);

    List<Diary> findByProfileIdAndCreatedAtGreaterThanEqualAndCreatedAtLessThanOrderByCreatedAtAsc(Long profileId, LocalDateTime start, LocalDateTime end);

}
