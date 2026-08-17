package com.app.expense.service;

import com.app.expense.dao.UserAuthenticationDao;
import com.app.expense.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAuthenticationServiceImpl implements UserAuthenticationService{

    @Autowired
    private UserAuthenticationDao userAuthenticationDao;

    @Override
    public User loginCheck(String identifier, String password) {
        try {
            return userAuthenticationDao.findByNameAndPassword(identifier, password)
                    .orElseThrow(()->new RuntimeException("Invalid Username/password"));
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
