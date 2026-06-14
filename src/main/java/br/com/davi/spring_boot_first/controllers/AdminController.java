package br.com.davi.spring_boot_first.controllers;

import br.com.davi.spring_boot_first.dto.response.UserRoleResponse;
import br.com.davi.spring_boot_first.dto.response.UserStatusResponse;
import br.com.davi.spring_boot_first.service.DisableUserService;
import br.com.davi.spring_boot_first.service.PromoteUserService;
import br.com.davi.spring_boot_first.service.ReactivateUserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/v1/admin")
@RequiredArgsConstructor


public class AdminController {

    private final DisableUserService disableUserService;
    private final ReactivateUserService reactivateUserService;
    private final PromoteUserService promoteUserService;


    @PostMapping("/disable/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    public UserStatusResponse disableId(
        @PathVariable Long userId
    )
    {
        return disableUserService.disableUser(userId);
    }


    @PostMapping("/reactivate/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    public UserStatusResponse reactivateId(
        @PathVariable Long userId
    )
    {
        return reactivateUserService.reactivateUser(userId);
    }


    @PostMapping("/promote/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    public UserRoleResponse promoteId(
        @PathVariable Long userId
    )
    {
        return promoteUserService.promoteUser(userId);
    }



}