package com.example.spot_share.service;

import com.example.spot_share.entity.User;
import com.example.spot_share.util.dto.UserDto;

public interface UserService {

    User saveUser(UserDto userDto);
}
