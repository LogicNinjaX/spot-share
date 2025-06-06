package com.example.spot_share.controller;

import com.example.spot_share.entity.User;
import com.example.spot_share.enums.Role;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class ParkerController {


    private UserService userService;
    private ModelMapper modelMapper;
    private AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil;

    public ParkerController(UserService userService, ModelMapper modelMapper, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/parkers/register")
    public ResponseEntity<ApiResponse<User>> registerParker(@Valid @RequestBody RegisterDto registerDto){
        UserDto userDto = modelMapper.map(registerDto, UserDto.class);
        userDto.setRole(Role.PARKER);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "parker registration successful", userService.saveUser(userDto)));
    }


    @PostMapping("/parkers/login")
    public ResponseEntity<Map<String, String>> login(@Valid @RequestBody LoginDto loginDto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
        String token = jwtUtil.generateToken(loginDto.getUsername());
        return ResponseEntity.ok(Collections.singletonMap("token", token));
    }

}
