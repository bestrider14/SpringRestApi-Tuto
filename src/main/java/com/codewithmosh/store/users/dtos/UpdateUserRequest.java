package com.codewithmosh.store.users.dtos;

import com.codewithmosh.store.users.validation.Lowercase;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateUserRequest {
    @NotBlank(message = "Name is required.")
    private String name;

    @NotBlank(message = "Email is required.")
    @Email(message = "Email must be valid.")
    @Lowercase(message = "Email must be in lower case.")
    private String email;
}
