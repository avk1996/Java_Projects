package com.app.expense.service;

import com.app.expense.dao.UserAuthenticationDao;
import com.app.expense.entity.User;
import com.app.expense.helper.AuthHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserAuthenticationServiceImpl implements UserAuthenticationService{

    @Autowired
    private UserAuthenticationDao userAuthenticationDao;

    @Override
    public User loginCheck(String identifier, String password) {
        try {
            String getStoredPassword = userAuthenticationDao.findByPassword(identifier).orElse("");
            if(verifyLogin(password, getStoredPassword))
                return userAuthenticationDao.findByNameAndPassword(identifier, password)
                    .orElseThrow(()->new RuntimeException("Invalid Username/password"));
            else
                throw new RuntimeException("Invlid Username/password");
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Autowired
    PasswordEncoder passwordEncoder;

    Boolean verifyLogin(String inputPass, String storedPass){
        return passwordEncoder.matches(inputPass, storedPass);
    }
}
