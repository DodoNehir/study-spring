package com.example.first.controller;

import com.example.first.domain.Member;
import com.example.first.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MemberController {
    private MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/signup/{username}")
    public Member createMember(@PathVariable String username) {
        Member member = new Member();
        member.setName(username);
        return memberService.signUp(member);
    }

    @GetMapping("/member")
    public Member getById(@RequestParam Long id) {
        return memberService.findMemberById(id).get();
    }

    @GetMapping("/member/{name}")
    public Member getByName(@PathVariable String name) {
        return memberService.findMemberByName(name).get();
    }

}
