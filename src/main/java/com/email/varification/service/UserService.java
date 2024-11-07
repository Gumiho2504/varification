package com.email.varification.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.email.varification.model.User;
import com.email.varification.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> getAllUser() {
        return userRepository.findAll();
    }
}
