package com.huuduc.snacksnap.service.impl;

import com.huuduc.snacksnap.data.dto.AuthenDTORequest;
import com.huuduc.snacksnap.data.dto.AuthenDTOResponse;
import com.huuduc.snacksnap.data.entity.User;
import com.huuduc.snacksnap.exception.NotFoundException;
import com.huuduc.snacksnap.repository.UserReopsitory;
import com.huuduc.snacksnap.security.CustomUserDetails;
import com.huuduc.snacksnap.security.JwtService;
import com.huuduc.snacksnap.service.AuthenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class AuthenServiceImpl implements AuthenService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserReopsitory userReopsitory;

    @Override
    public AuthenDTOResponse login(AuthenDTORequest authenDTORequest) {

        Authentication authentication= this.authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(authenDTORequest.getEmail(),authenDTORequest.getPassword())
                );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = this.jwtService.generateToken(authenDTORequest.getEmail());

        //get info user from authentication
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        User findUser = this.userReopsitory.findByEmail(user.getUsername()).orElseThrow(
                ()-> new NotFoundException(Collections.singletonMap("User email",null))
        );

        AuthenDTOResponse authenDTOResponse = new AuthenDTOResponse("Đăng nhập thành công",token,findUser.getId(),findUser.getRole().getId(),
                findUser.isActive());

        return authenDTOResponse;
    }
}
