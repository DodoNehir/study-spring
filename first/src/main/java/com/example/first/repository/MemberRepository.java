package com.example.first.repository;

import com.example.first.domain.Member;

import java.util.List;

public interface MemberRepository {
    public void save(Member member);
    public Member findById(String id);
    public Member findByName(String name);
    public List<Member> findAll();
}
