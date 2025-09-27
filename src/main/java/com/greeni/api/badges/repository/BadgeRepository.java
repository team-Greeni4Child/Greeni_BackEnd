package com.greeni.api.badges.repository;

import com.greeni.api.badges.domain.Badge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BadgeRepository extends JpaRepository<Badge,Long> {
}
