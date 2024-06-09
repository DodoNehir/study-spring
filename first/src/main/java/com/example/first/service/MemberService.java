package com.example.first.service;

import com.example.first.domain.Member;
import com.example.first.repository.MemberRepository;

import java.util.Optional;

public class MemberService {
    private MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public int signUp(Member member) {
        return memberRepository.save(member);
    }

    public Optional<Member> findMemberById(int id) {
        return Optional.of(memberRepository.findById(id).get());
    }

    public Optional<Member> findMemberByName(String name) {
        return Optional.of(memberRepository.findByName(name).get());
    }

}
