package com.greeni.api.profiles.repository;

import com.greeni.api.profiles.domain.mapping.ProfileBadge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfileBadgeRepository extends JpaRepository<ProfileBadge, Long> {

    List<ProfileBadge> findByProfileIdOrderByCreatedAtDesc(Long profileId);
}
