package com.example.example4.entity;

import jakarta.persistence.*;

@Table(name = "MEMBERS")
@Entity
public class Member {
    @Id
    private long memberId;

    @Column
    private String name;
}
