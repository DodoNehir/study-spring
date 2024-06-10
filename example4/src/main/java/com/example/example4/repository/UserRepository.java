package com.example.example4.repository;

import com.example.example4.domain.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class UserRepository {

    private final UserJdbcRepository userJdbcRepository;

    public UserRepository(UserJdbcRepository userJdbcRepository) {
        this.userJdbcRepository = userJdbcRepository;
    }

    @Transactional
    public User create(String name, String gender) {
        return userJdbcRepository.save(new User(name, gender));
    }

    @Transactional
    public User getById(int id) {
        return userJdbcRepository.findById(id).orElseThrow(() -> new RuntimeException("사용자가 없습니다."));
    }

    @Transactional
    public List<User> findByGender(String gender) {
        return userJdbcRepository.findAllByGenderAndDeletedYn(gender, false);
    }

    @Transactional
    public User updateName(int id, String name) {
        User byId = userJdbcRepository.findById(id).get();
        byId.updateName(name);
        userJdbcRepository.save(byId);
        return byId;
    }

    @Transactional
    public User delete(int id) {
        User byId = userJdbcRepository.findById(id).get();
        byId.delete();
        userJdbcRepository.save(byId);
        return byId;
    }
}
