package com.example.jdbcpractice.repository;

import com.example.jdbcpractice.entity.Dummy;

import java.util.Optional;

public interface DummyRepository {
    Dummy save(Dummy dummy);

    Optional<Dummy> findByName(String name);

    int updateNameById(int id, String name);

    int deletedById(int id);
}
