package com.greeni.api.badges.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.greeni.api.badges.domain.Badge;

public interface BadgeRepository extends JpaRepository<Badge, Long> {

	Optional<Badge> findByName(String badgeName);
}
