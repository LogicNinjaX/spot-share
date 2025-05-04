package com.example.spot_share.security;

import com.example.spot_share.repository.UserRepository;
import com.example.spot_share.util.exception.UserNotFoundException;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;


    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Cacheable(value = "users", key = "#username")
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        com.example.spot_share.entity.User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("user not found with username: "+username));

        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));

        return new CustomUserDetails(
                user.getUserId(),
                user.getUsername(),
                user.getPassword(),
                user.getRole(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                authorities);
    }
}
