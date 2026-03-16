package com.greeni.api.members.repository;

import com.greeni.api.members.converter.MemberTermConverter;
import com.greeni.api.members.domain.mapping.MemberTerm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberTermRepository extends JpaRepository<MemberTerm, Long> {
}
