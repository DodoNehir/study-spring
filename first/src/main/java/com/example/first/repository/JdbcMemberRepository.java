package com.example.first.repository;

import com.example.first.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcMemberRepository implements MemberRepository {

    // ?? DB 랑 어떻게 연결하드라..

    @Override
    public void save(Member member) {

    }

    @Override
    public Member findById(String id) {
        return null;
    }

    @Override
    public Member findByName(String name) {
        return null;
    }

    @Override
    public List<Member> findAll() {
        return List.of();
    }
}
