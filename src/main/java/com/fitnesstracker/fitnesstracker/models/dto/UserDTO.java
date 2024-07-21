package com.fitnesstracker.fitnesstracker.models.dto;

import lombok.Data;

@Data
public class UserDTO {

    private Long id;

    private String role;

    private String username;

    private String email;
}
