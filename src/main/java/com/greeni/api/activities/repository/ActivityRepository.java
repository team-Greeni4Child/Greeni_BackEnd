package com.greeni.api.activities.repository;

import com.greeni.api.activities.domain.Activity;
import com.greeni.api.profiles.domain.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface ActivityRepository extends JpaRepository<Activity, Long> {

	@Query("""
        select a from Activity a
        where a.profile.id = :profileId
        order by a.createdAt desc, a.id desc
    """)
	List<Activity> findFirstPage(@Param("profileId") Long profileId, Pageable pageable);

	@Query("""
        select a from Activity a
        where a.profile.id = :profileId
          and (
              a.createdAt < :cursorCreatedAt
              or (a.createdAt = :cursorCreatedAt and a.id < :cursorId)
          )
        order by a.createdAt desc, a.id desc
    """)
	List<Activity> findNextPage(
		@Param("profileId") Long profileId,
		@Param("cursorCreatedAt") LocalDateTime cursorCreatedAt,
		@Param("cursorId") Long cursorId,
		Pageable pageable
	);

	List<Activity> findTop3ByProfileAndCreatedAtBetweenOrderByCreatedAtDesc(
		Profile profile, LocalDateTime from, LocalDateTime to);
}
