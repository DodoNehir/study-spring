package com.example.simpledomain.service;

import com.example.simpledomain.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MemberServiceTest {

    MemberService memberService;

    @AfterEach
    void 데이터비우기() {
        memberService.clear();
    }

    @Test
    void 회원가입() {
        // given
        Member member = new Member();
        member.setName("one");

        // when
        Long savedId = memberService.join(member);

        // then
        Member findOne = memberService.findOne(savedId).get();
        assertThat(member.getName()).isEqualTo(findOne.getName());
    }

    @Test
    void 중복검증() {
        // given
        Member member1 = new Member();
        member1.setName("one");

        Member member2 = new Member();
        member2.setName("one");

        // when
        memberService.join(member1);

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () ->
                memberService.join(member2));

        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");


        // then
    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}