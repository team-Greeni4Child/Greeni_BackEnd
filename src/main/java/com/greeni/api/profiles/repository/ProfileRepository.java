package com.greeni.api.profiles.repository;

import com.greeni.api.profiles.domain.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    Optional<Profile> findByIdAndMemberId(Long profileId, Long memberId);
}
