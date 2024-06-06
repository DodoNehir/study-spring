package com.example.simpledomain.repository;

import com.example.simpledomain.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository = new MemoryMemberRepository();

    @AfterEach
    public void afterEach() {
        // Test 하나가 끝난 뒤 콜백으로 실행되는 afterEach()
        // 끝날 때 마다 데이터를 지워준다.
        repository.clearStore();
    }

    @Test
    public void saveTest() {
        // given
        Member member = new Member(1L, "one");

        // when
        repository.save(member);

        // then
        Member result = repository.findById(member.getId()).get();
        assertThat(member).isEqualTo(result);
    }

    @Test
    public void findByNameTest() {
        // given
        Member member1 = new Member();
        member1.setName("one");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("two");
        repository.save(member2);

        // when
        Member result = repository.findByName("two").get();

        // then
        assertThat(result).isEqualTo(member2);
    }

    @Test
    public void findAllTest() {
        // given
        Member member1 = new Member();
        member1.setName("one");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("two");
        repository.save(member2);

        Member member3 = new Member();
        member3.setName("three");
        repository.save(member3);

        // when
        List<Member> resultAll = repository.findAll();

        // then
        assertThat(resultAll.size()).isEqualTo(3);

    }
}