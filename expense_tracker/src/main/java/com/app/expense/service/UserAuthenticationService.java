package com.app.expense.service;

import com.app.expense.entity.User;
import com.app.expense.response_dto.UserResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface UserAuthenticationService {
    public UserResponseDTO login(String identifier, String s);
}
