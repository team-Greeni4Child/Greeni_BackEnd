package com.greeni.api.activities.repository;

import com.greeni.api.activities.domain.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
}
