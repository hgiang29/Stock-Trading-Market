package com.stockapi.controller;

import com.stockapi.dto.UserDTO;
import com.stockapi.dto.UserLoginDTO;
import com.stockapi.helper.ResponseHandler;
import com.stockapi.model.User;
import com.stockapi.security.jwt.JwtUntil;
import com.stockapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class UserController {

    @Autowired
    JwtUntil jwtUntil;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    UserService userService;


    @PostMapping("/login")
    public ResponseEntity<Object> createAuthenticaionToken(@RequestBody UserLoginDTO request) throws Exception{

        userService.authenticate(request.getEmail(), request.getPassword());

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(request.getEmail());

        UserDTO userDTO = userService.getUserDTOFromEmail(userDetails.getUsername());
        String token =  jwtUntil.generateToken(userDetails);

        return ResponseHandler.generateAuthenticationResponse(userDTO, token);
    }

    @GetMapping("/user")
    public String getUserInfoFromToken() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        return userDetails.getUsername();
    }





}
