package com.akshat.userService.service.impl;

import com.akshat.userService.model.entity.User;
import com.akshat.userService.model.utilmodel.JWTClaims;
import com.akshat.userService.repository.UserRepository;
import com.akshat.userService.service.UserAuthService;
import com.akshat.userService.util.JWTUtil;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserAuthServiceImpl implements UserAuthService {
    private final JWTUtil jwtUtil;
    private final UserRepository userRepository;

    @Override
    public UserDetails validateToken(String token) {
        JWTClaims claims = jwtUtil.decodeToken(token);

        Optional<User> user = userRepository.findById(claims.getId());
        if (user.isEmpty()) {
            throw new JwtException("Invalid login session.");
        }

        if (!user.get().getToken().equals(token)) {
            throw new JwtException("Login session expired.");
        }

        return loadUserAuth(user.get());
    }

    @Override
    public UserDetails loadUserAuth(User user) {
        return new org.springframework.security.core.userdetails.User(
                user.getName(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority(user.getRole().name()))
        );
    }
}
