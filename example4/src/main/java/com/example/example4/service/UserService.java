package com.example.example4.service;

import com.example.example4.domain.User;
import com.example.example4.domain.UserDto;
import com.example.example4.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto create(String name, String gneder) {
        return UserDto.of(userRepository.create(name, gneder));
    }

    public List<UserDto> findByGender(String gender) {
        return userRepository.findByGender(gender)
                .stream()
                .map(UserDto::of)
                .collect(Collectors.toList());
    }

    public UserDto updateName(int id, String name) {
        return UserDto.of(userRepository.updateName(id, name));
    }

    public UserDto delete(int id) {
        return UserDto.of(userRepository.delete(id));
    }
}
