package com.example.exresttemplate.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
@Entity
public class Member {
    @Id
    private int memberID;

    @Column
    private String teamId;

    @Column
    private String username;
}
