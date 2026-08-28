package com.app.expense.service;

import com.app.expense.dao.UserAuthenticationDao;
import com.app.expense.entity.User;
import com.app.expense.response_dto.UserResponseDTO;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserAuthenticationServiceImpl implements UserAuthenticationService{

    private static final Logger logger = LoggerFactory.getLogger(UserAuthenticationServiceImpl.class);

    @Autowired
    private UserAuthenticationDao userAuthenticationDao;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDTO login(String identifier, String password) {
        try {
            logger.info("In Service {}, {}", identifier, password);
            User newUser = userAuthenticationDao.login(identifier)
                    .orElseThrow(()->new RuntimeException("Invalid Username"));
            if(!passwordEncoder.matches(password, newUser.getPassword()))
                throw new RuntimeException("Invalid Password");

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(newUser.getName(), null, newUser.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            return modelMapper.map(newUser, UserResponseDTO.class);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
