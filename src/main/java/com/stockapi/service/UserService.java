package com.stockapi.service;

import com.stockapi.dto.UserDTO;
import com.stockapi.model.User;
import com.stockapi.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    public UserDTO getUserDTOFromEmail(String email) {
        User user = userRepository.findUserByEmail(email);
        return modelMapper.map(user, UserDTO.class);
    }


}
