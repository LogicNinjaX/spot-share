package com.example.spot_share.service;

import com.example.spot_share.entity.User;
import com.example.spot_share.util.dto.UserDto;
import com.example.spot_share.util.exception.UserNotFoundException;

import java.util.UUID;

public interface UserService {

    User saveUser(UserDto userDto);

    User updateUser(UUID userId, User user) throws UserNotFoundException;
}
