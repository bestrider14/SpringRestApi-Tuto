package com.codewithmosh.store.users.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ChangePasswordRequest {

    @NotBlank(message = "Old password is required.")
    @Size(min = 6, max = 100, message = "Password must be between 6 to 100 characters long.")
    private String oldPassword;

    @NotBlank(message = "New password is required.")
    @Size(min = 6, max = 100, message = "Password must be between 6 to 100 characters long.")
    private String newPassword;
}
