package com.example.spot_share.controller;

import com.example.spot_share.entity.User;
import com.example.spot_share.security.CustomUserDetails;
import com.example.spot_share.service.UserService;
import com.example.spot_share.util.api_response.ApiResponse;
import com.example.spot_share.util.dto.user.UserUpdateRequest;
import com.example.spot_share.util.dto.user.UserUpdateResponse;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PutMapping("/users")
    public ResponseEntity<ApiResponse<UserUpdateResponse>> updateUser(
            @Valid @RequestBody UserUpdateRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails){
        var updatedUser = userService.updateUser(userDetails.getUserId(), modelMapper.map(request, User.class));

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(true, "User updated successfully", modelMapper.map(updatedUser,UserUpdateResponse.class)));
    }
}
