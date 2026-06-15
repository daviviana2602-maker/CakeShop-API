package br.com.davi.spring_boot_first.controllers;

import br.com.davi.spring_boot_first.service.DeleteAccountService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/v1/profile")
@RequiredArgsConstructor


public class ProfileController {

    private final DeleteAccountService deleteAccountService;


    @PostMapping("delete/{userId}")
    @PreAuthorize("isAuthenticated()")
    @SecurityRequirement(name = "bearerAuth")
    public Long deleteProfile(
        @PathVariable Long userId
    )
    {
        return deleteAccountService.deleteUser(userId);
    }

}
