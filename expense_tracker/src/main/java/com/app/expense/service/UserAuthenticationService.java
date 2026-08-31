package com.app.expense.service;

import com.app.expense.request_dto.UserResetPasswordDTO;
import com.app.expense.response_dto.UserResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface UserAuthenticationService {
    public String resetPassword(UserResetPasswordDTO userResetPasswordDTO);

    public UserResponseDTO login(String identifier, String s);
}
