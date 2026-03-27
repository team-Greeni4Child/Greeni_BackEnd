package com.greeni.api.members.converter;

import com.greeni.api.members.domain.Member;
import com.greeni.api.members.domain.Term;
import com.greeni.api.members.domain.mapping.MemberTerm;

public class MemberTermConverter {

    public static MemberTerm toMemberTerm(Member member, Term term){
        return MemberTerm.builder()
                .member(member)
                .term(term)
                .build();
    }
}
