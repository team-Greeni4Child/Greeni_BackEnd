package com.greeni.api.profiles.repository;

import com.greeni.api.profiles.domain.mapping.ProfileBadge;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileBadgeRepository extends JpaRepository<ProfileBadge, Long> {

    Slice<ProfileBadge> findByProfileIdOrderByIdDesc(Long profileId, Pageable pageable);
}
