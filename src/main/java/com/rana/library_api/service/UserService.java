package com.rana.library_api.service;

import com.rana.library_api.dto.UserDto;
import com.rana.library_api.entity.Role;
import com.rana.library_api.entity.User;
import com.rana.library_api.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDto register(UserDto dto) {

    User user = new User();

    user.setUsername(dto.getUsername());
    user.setEmail(dto.getEmail());
    user.setPassword(passwordEncoder.encode(dto.getPassword()));
    user.setRole(Role.USER);

    User savedUser = userRepository.save(user);

    UserDto response = new UserDto();
    response.setUsername(savedUser.getUsername());
    response.setEmail(savedUser.getEmail());

    return response;
}
}