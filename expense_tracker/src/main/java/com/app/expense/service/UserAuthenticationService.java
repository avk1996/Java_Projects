package com.app.expense.service;

import com.app.expense.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserAuthenticationService {
    public User loginCheck(String identifier, String password);
}
