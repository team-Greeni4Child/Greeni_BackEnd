package com.greeni.api.activities.repository;

import com.greeni.api.activities.domain.Activity;
import com.greeni.api.profiles.domain.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface ActivityRepository extends JpaRepository<Activity, Long> {

    Page<Activity> findAllByProfileIdOrderByCreatedAtDesc(Long profileId, Pageable pageable);

	List<Activity> findTop3ByProfileAndCreatedAtBetweenOrderByCreatedAtDesc(
		Profile profile, LocalDateTime from, LocalDateTime to);
}
