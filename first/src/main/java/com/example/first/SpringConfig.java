package com.example.first;

import com.example.first.repository.JdbcTemplateMemberRepository;
import com.example.first.repository.MemberRepository;
import com.example.first.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    private DataSource dataSource;

    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        // 갈아끼우기 위해 @Repository 안 쓰고 @Bean 으로 조립! ㅎㅎ
        return new JdbcTemplateMemberRepository(dataSource);
    }
}
