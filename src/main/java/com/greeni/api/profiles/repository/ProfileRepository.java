package com.greeni.api.profiles.repository;

import com.greeni.api.profiles.domain.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    List<Profile> findAllByMemberId(Long memberId);
}
