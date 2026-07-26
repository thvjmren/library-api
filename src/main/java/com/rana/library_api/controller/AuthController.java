package com.rana.library_api.controller;

import com.rana.library_api.dto.LoginRequest;
import com.rana.library_api.dto.LoginResponse;
import com.rana.library_api.dto.UserDto;
import com.rana.library_api.entity.User;
import com.rana.library_api.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.rana.library_api.dto.LoginRequest;
import com.rana.library_api.dto.LoginResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

        @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody UserDto dto) {
        return new ResponseEntity<>(userService.register(dto), HttpStatus.CREATED);
    }    

        @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(userService.login(request));
    }
}