package com.greeni.api.activities.repository;

import com.greeni.api.activities.domain.Activity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository<Activity, Long> {

    Page<Activity> findAllByProfileIdOrderByCreatedAtDesc(Long profileId, Pageable pageable);
}
