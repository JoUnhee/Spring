package com.cloneproject.repository;

import com.cloneproject.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);
    Optional<Member> findById(Long id);
    Optional<Member> findByName(String name);
    List<Member> findAll();
    List<Member> findByTeam(String team);
    void delete(Member member);
    void deleteById(Long id);
}

