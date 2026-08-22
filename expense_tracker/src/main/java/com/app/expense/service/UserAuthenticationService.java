package com.app.expense.service;

import com.app.expense.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserAuthenticationService {
    public User login(String identifier, String s);
}
