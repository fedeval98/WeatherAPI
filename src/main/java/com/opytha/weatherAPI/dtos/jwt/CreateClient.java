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

    @NotBlank (message = "El nombre no puede estar vacio")
    @Size (min = 3, max = 50, message = "El nombre debe tener mínimo 3 y máximo 50 caracteres")
    private String firstName;

    @NotBlank (message = "El apellido no puede estar vacio")
    @Size (min = 3, max = 50, message = "El apellido debe tener mínimo 3 y máximo 50 caracteres")
    private String lastName;

    @Email (message = "El email debe ser válido")
    @NotBlank (message = "El email no puede estar vacío")
    @Size (max = 50, message = "El email no puede superar los 50 caracteres")
    private String email;

    @NotBlank (message = "La contraseña no puede estar vacía")
    @Size (min = 8, max = 128, message = "La contraseña debe tener entre 8 y 128 caracteres.")
    @Pattern (
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,128}$",
            message = "La contraseña debe tener al menos una mayúscula, una minúscula y un número"
    )
    private String password;
}
