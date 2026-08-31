package com.app.expense.request_dto;

import lombok.Data;

@Data
public class UserResetPasswordDTO {
    private String identifier;
    private String oldPassword;
    private String newPassword;
}
