package com.example.first.repository;

import com.example.first.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    public int save(Member member);
    public Optional<Member> findById(int id);
    public Optional<Member> findByName(String name);
    public List<Member> findAll();
}
