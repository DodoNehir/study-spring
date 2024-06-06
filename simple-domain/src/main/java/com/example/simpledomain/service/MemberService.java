package com.example.simpledomain.service;

import com.example.simpledomain.domain.Member;
import com.example.simpledomain.repository.MemberRepository;
import com.example.simpledomain.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 회원 가입
     */
    public Long join(Member member) {
        // 같은 이름인 중복 회원은 안 된다.
        validateDuplication(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplication(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalArgumentException("이미 존재하는 회원입니다.");
                });
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
        // null일 떄는 어떻게 하나?..
    }

    public void clear() {
        MemoryMemberRepository mmr = (MemoryMemberRepository) memberRepository;
        mmr.clearStore();
    }
//    public Member getMemberByName(String name) {
//        return memberRepository.findByName(name).get();
//    }
}
