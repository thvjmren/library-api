package com.rana.library_api.service;

import com.rana.library_api.dto.LoginRequest;
import com.rana.library_api.dto.LoginResponse;
import com.rana.library_api.dto.UserDto;
import com.rana.library_api.entity.Role;
import com.rana.library_api.entity.User;
import com.rana.library_api.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.rana.library_api.dto.LoginRequest;
import com.rana.library_api.dto.LoginResponse;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository,
                   PasswordEncoder passwordEncoder,
                   JwtService jwtService) {

    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtService = jwtService;
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

    public LoginResponse login(LoginRequest request) {

    User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new RuntimeException("User Not Found"));

    if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
        throw new RuntimeException("Invalid Password");
    }

    String token = jwtService.generateToken(user.getEmail());

    return new LoginResponse(token);
}
}