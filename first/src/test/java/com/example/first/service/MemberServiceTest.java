package com.example.first.service;

import com.example.first.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Test
    void signUp() {
        // given
        Member member = new Member();
        member.setName("abdfaede");

        // when
        int newId = memberService.signUp(member);

        // then
        Member findMember = memberService.findMemberById(newId).get();
        assertThat(findMember.getId()).isEqualTo(newId);
    }

}