package com.stockapi.controller;

import com.stockapi.dto.OwningStockDTO;
import com.stockapi.dto.UserDTO;
import com.stockapi.dto.UserLoginDTO;
import com.stockapi.dto.UserTransactionHistoryDTO;
import com.stockapi.helper.ResponseHandler;
import com.stockapi.security.jwt.JwtUntil;
import com.stockapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/user/inventory")
    public List<OwningStockDTO> getUserOwningStock() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        return userService.getUserOwningStock(userDetails.getUsername());
    }

    @GetMapping("/user/history")
    public List<UserTransactionHistoryDTO> getUserTransactionHistory() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        return userService.getUserTransactionHistory(userDetails.getUsername());
    }

    @GetMapping("/user/money")
    public double getUserMoney() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        return userService.getUserMoney(userDetails.getUsername());
    }



}
