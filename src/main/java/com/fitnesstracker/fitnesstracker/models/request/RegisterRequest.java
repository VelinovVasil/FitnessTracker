package com.fitnesstracker.fitnesstracker.models.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "Username must not be blank")
    private String username;

    @Email(message = "Invalid email")
    private String email;

    @NotBlank(message = "Password must not be blank")
    private String password;

}
