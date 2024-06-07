package com.example.first.service;

import com.example.first.repository.MemberRepository;
import org.springframework.stereotype.Service;

public class MemberService {
    private MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


}
