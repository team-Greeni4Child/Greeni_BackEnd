package com.greeni.api.diaries.repository;

import com.greeni.api.diaries.domain.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
}
