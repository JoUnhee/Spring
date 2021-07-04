package com.cloneproject.repository;

import com.cloneproject.domain.Member;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface SpringDataMemberRepository extends Repository<Member,Long>, MemberRepository {

    @Override
    List<Member> findByTeam(String team);
    @Override
    Optional<Member> findByName(String name);

}
