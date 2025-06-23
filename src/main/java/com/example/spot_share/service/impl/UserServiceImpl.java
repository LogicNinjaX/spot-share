package com.example.spot_share.service.impl;

import com.example.spot_share.entity.User;
import com.example.spot_share.repository.UserRepository;
import com.example.spot_share.service.UserService;
import com.example.spot_share.util.dto.UserDto;
import com.example.spot_share.util.exception.UserNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private ModelMapper modelMapper;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User saveUser(UserDto userDto) {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User user = modelMapper.map(userDto, User.class);
        return userRepository.save(user);
    }

    @Override
    public User updateUser(UUID userId, User user){
        User userEntity = userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException("User not found"));

        userEntity.setUsername(user.getUsername());
        userEntity.setPassword(user.getPassword());
        userEntity.setEmail(user.getEmail());
        return userRepository.save(userEntity);
    }
}
