package com.example.simpledomain.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Member {
    private Long id;
    private String name;

    public Member(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Member() {

    }
}
