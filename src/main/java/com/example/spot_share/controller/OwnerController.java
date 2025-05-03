package com.example.spot_share.controller;


import com.example.spot_share.enums.Role;
import com.example.spot_share.security.JwtUtil;
import com.example.spot_share.service.UserService;
import com.example.spot_share.util.dto.*;
import org.modelmapper.ModelMapper;
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
public class OwnerController {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public OwnerController(UserService userService, ModelMapper modelMapper, AuthenticationManager authenticationManager,
                           JwtUtil jwtUtil) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/owners/register")
    public String registerParker(@RequestBody RegisterDto registerDto){
        UserDto userDto = modelMapper.map(registerDto, UserDto.class);
        userDto.setRole(Role.OWNER);
        userService.saveUser(userDto);
        return "success...";
    }


    @PostMapping("/owners/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginDto loginDto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
        String token = jwtUtil.generateToken(loginDto.getUsername());
        return ResponseEntity.ok(Collections.singletonMap("token", token));
    }


}
