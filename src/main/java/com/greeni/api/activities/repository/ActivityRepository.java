package com.greeni.api.activities.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.greeni.api.activities.domain.Activity;
import com.greeni.api.profiles.domain.Profile;

public interface ActivityRepository extends JpaRepository<Activity, Long> {

	List<Activity> findTop3ByProfileAndCreatedAtBetweenOrderByCreatedAtDesc(
		Profile profile, LocalDateTime from, LocalDateTime to);
}
