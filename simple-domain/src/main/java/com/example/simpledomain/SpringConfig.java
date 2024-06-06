package com.example.simpledomain;

import com.example.simpledomain.repository.MemberRepository;
import com.example.simpledomain.repository.MemoryMemberRepository;
import com.example.simpledomain.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    // 로직을 호출해서 서비스를 스프링 빈에 등록해준다.
    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }
}
