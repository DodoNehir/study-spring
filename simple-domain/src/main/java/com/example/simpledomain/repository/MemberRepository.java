package com.example.simpledomain.repository;

import com.example.simpledomain.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);
    Optional<Member> findById(Long id);
    Optional<Member> findByName(String name);
    List<Member> findAll();

    // find 할 떄 결과가 null일 수 있는데 그 null 그대로 반환하지 않고 Optional로 감싸서 반환한다.
}
