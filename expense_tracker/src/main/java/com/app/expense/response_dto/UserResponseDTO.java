package com.app.expense.response_dto;

import com.app.expense.helper.Role;
import lombok.Data;

@Data
public class UserResponseDTO {
    private String name;
    private String email;
    private Role role;
    private Integer id;
}
