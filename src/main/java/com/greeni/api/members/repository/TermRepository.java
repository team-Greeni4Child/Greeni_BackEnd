package com.greeni.api.members.repository;

import com.greeni.api.members.domain.Term;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TermRepository extends JpaRepository<Term, Long> {
    @Query("SELECT t.id FROM Term t WHERE t.required = true")
    List<Long> findRequiredTermIds();
}
