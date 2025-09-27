package com.greeni.api.members.repository;

import com.greeni.api.members.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long> {

    boolean existsByEmail(String email);
}
