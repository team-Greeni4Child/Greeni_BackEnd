package com.greeni.api.members.repository;

import com.greeni.api.members.domain.Term;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TermRepository extends JpaRepository<Term, Long> {
}
