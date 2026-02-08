package com.greeni.api.diaries.repository;

import com.greeni.api.diaries.domain.Voice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoiceRepository extends JpaRepository<Voice, Long> {
    List<Voice> findByDiaryIdOrderByCreatedAtAsc(Long diaryId);
}
