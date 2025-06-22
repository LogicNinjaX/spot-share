package com.example.spot_share.controller;

import com.example.spot_share.entity.User;
import com.example.spot_share.enums.Role;
import com.example.spot_share.security.CustomUserDetails;
import com.example.spot_share.security.JwtUtil;
import com.example.spot_share.service.UserService;
import com.example.spot_share.util.api_response.ApiResponse;
import com.example.spot_share.util.dto.LoginDto;
import com.example.spot_share.util.dto.RegisterDto;
import com.example.spot_share.util.dto.UserDto;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;

    public AuthController(UserService userService, JwtUtil jwtUtil, ModelMapper modelMapper, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.modelMapper = modelMapper;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/owners/register")
    public ResponseEntity<ApiResponse<User>> registerOwner(@Valid @RequestBody RegisterDto registerDto){
        UserDto userDto = modelMapper.map(registerDto, UserDto.class);
        userDto.setRole(Role.OWNER);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "owner registration successful", userService.saveUser(userDto)));
    }

    @PostMapping("/parkers/register")
    public ResponseEntity<ApiResponse<User>> registerParker(@Valid @RequestBody RegisterDto registerDto){
        UserDto userDto = modelMapper.map(registerDto, UserDto.class);
        userDto.setRole(Role.PARKER);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "parker registration successful", userService.saveUser(userDto)));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Map<String, String>>> login(@Valid @RequestBody LoginDto loginDto) {
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        String token = jwtUtil.generateToken(userDetails.getUserId(), userDetails.getUsername(), userDetails.getRole().name());
        return ResponseEntity.ok(new ApiResponse<>(true, "login successful", Collections.singletonMap("token", token)));
    }
}
