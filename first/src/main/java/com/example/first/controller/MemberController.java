package com.example.first.controller;

import com.example.first.service.MemberService;
import org.springframework.stereotype.Controller;

@Controller
public class MemberController {
    private MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
}
