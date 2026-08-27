package com.app.expense.service;

import com.app.expense.dao.UserAuthenticationDao;
import com.app.expense.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserAuthenticationServiceImpl implements UserAuthenticationService{

    private static final Logger logger = LoggerFactory.getLogger(UserAuthenticationServiceImpl.class);

    @Autowired
    private UserAuthenticationDao userAuthenticationDao;

    @Override
    public User login(String identifier, String password) {
        try {
            logger.info(">> {}", identifier);
            return userAuthenticationDao.login(identifier, password)
                    .orElseThrow(()->new RuntimeException("Invalid Username/password"));
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
