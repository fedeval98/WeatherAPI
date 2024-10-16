package com.opytha.weatherAPI.dtos.jwt;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateClient {

    @NotBlank(message = "First name cannot be blank.")
    @Size(min = 3, max = 50, message = "First name must have between 3 and 50 characters.")
    private String firstName;

    @NotBlank(message = "Last name cannot be blank.")
    @Size(min = 3, max = 50, message = "Last name must have between 3 and 50 characters.")
    private String lastName;

    @Email(message = "The email must be valid.")
    @NotBlank(message = "The email cannot be blank.")
    @Size(max = 50, message = "The email cannot exceed 50 characters.")
    private String email;

    @NotBlank(message = "The password cannot be blank.")
    @Size(min = 8, max = 128, message = "The password must be between 8 and 128 characters.")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,128}$",
            message = "The password must contain at least one uppercase letter, one lowercase letter, and one number."
    )
    private String password;
}
