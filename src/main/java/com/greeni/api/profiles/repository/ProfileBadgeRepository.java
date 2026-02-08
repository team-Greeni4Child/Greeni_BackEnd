package com.greeni.api.profiles.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.greeni.api.badges.domain.Badge;
import com.greeni.api.profiles.domain.Profile;
import com.greeni.api.profiles.domain.mapping.ProfileBadge;

public interface ProfileBadgeRepository extends JpaRepository<ProfileBadge, Long> {

	List<ProfileBadge> findByProfileIdOrderByCreatedAtDesc(Long profileId);

	long countByProfile(Profile profile);

	boolean existsByProfileAndBadge(Profile profile, Badge badge);
}
