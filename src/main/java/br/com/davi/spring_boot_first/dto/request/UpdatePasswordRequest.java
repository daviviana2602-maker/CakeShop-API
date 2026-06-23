package br.com.davi.spring_boot_first.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor

public class UpdatePasswordRequest {

    @NotBlank(message = "current password is required")
    @Size(min = 3, max = 50, message = "Password must be between 3 and 50 characters")
    private String currentPassword;

    @NotBlank(message = "new password is required")
    @Size(min = 3, max = 50, message = "Password must be between 3 and 50 characters")
    private String newPassword;

}
