package br.com.davi.spring_boot_first.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor


public class UpdateProfileRequest {

    @Size(max = 50, message = "Product name cannot exceed 50 characters")
    private String name;

    @Email(message = "Email format is wrong")
    private String email;

}