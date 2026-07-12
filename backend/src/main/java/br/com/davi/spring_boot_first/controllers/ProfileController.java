package br.com.davi.spring_boot_first.controllers;

import br.com.davi.spring_boot_first.dto.request.UpdatePasswordRequest;
import br.com.davi.spring_boot_first.dto.request.UpdateProfileRequest;
import br.com.davi.spring_boot_first.dto.response.UpdateProfileResponse;
import br.com.davi.spring_boot_first.service.DeleteAccountService;
import br.com.davi.spring_boot_first.service.UpdatePasswordService;
import br.com.davi.spring_boot_first.service.UpdateProfileService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/v1/profile")
@RequiredArgsConstructor


public class ProfileController {

    private final DeleteAccountService deleteAccountService;
    private final UpdateProfileService updateProfileService;
    private final UpdatePasswordService updatePasswordService;


    @DeleteMapping("/")
    @PreAuthorize("isAuthenticated()")
    @SecurityRequirement(name = "bearerAuth")
    public Long deleteProfile(
    )
    {
        return deleteAccountService.deleteUser();
    }


    @PatchMapping("/")
    @PreAuthorize("isAuthenticated()")
    @SecurityRequirement(name = "bearerAuth")
    public UpdateProfileResponse updateProfile(
        @Valid @RequestBody UpdateProfileRequest updateProfileRequest
    )
    {
        return updateProfileService.changeProfile(
                updateProfileRequest.getName(),
                updateProfileRequest.getNewEmail()
        );
    }


    @PatchMapping("/password")
    @PreAuthorize("isAuthenticated()")
    @SecurityRequirement(name = "bearerAuth")
    public Long updatePassword(
        @Valid @RequestBody UpdatePasswordRequest updatePasswordRequest
    )
    {
        return updatePasswordService.changePassword(
                updatePasswordRequest.getCurrentPassword(),
                updatePasswordRequest.getNewPassword()
        );
    }


}