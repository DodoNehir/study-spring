package com.example.simpledomain.repository;

import com.example.simpledomain.domain.Member;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MemoryMemberRepositoryTest {

    MemoryMemberRepository memoryMemberRepository = new MemoryMemberRepository();

    @Test
    public void test() {
        // given
        Member member1 = new Member(1L, "one");
        Member member2 = new Member(2L, "two");
        Member member3 = new Member(3L, "three");


        // when
        memoryMemberRepository.save(member1);
        memoryMemberRepository.save(member2);
        memoryMemberRepository.save(member3);


        // then
        assertEquals(3, memoryMemberRepository.findAll().size());

    }
}