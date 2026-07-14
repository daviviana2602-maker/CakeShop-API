package br.com.davi.spring_boot_first.controllers;

import br.com.davi.spring_boot_first.dto.response.UserResponse;
import br.com.davi.spring_boot_first.dto.response.UserRoleResponse;
import br.com.davi.spring_boot_first.dto.response.UserStatusResponse;
import br.com.davi.spring_boot_first.service.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/v1/admin")
@RequiredArgsConstructor


public class AdminController {

    private final DisableUserService disableUserService;
    private final ReactivateUserService reactivateUserService;
    private final PromoteUserService promoteUserService;
    private final DemoteUserService demoteUserService;
    private final SearchUserService searchUserService;


    @PostMapping("{userId}/disable")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    public UserStatusResponse disableId(
        @PathVariable Long userId
    )
    {
        return disableUserService.disableUser(userId);
    }


    @PostMapping("{userId}/reactivate")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    public UserStatusResponse reactivateId(
        @PathVariable Long userId
    )
    {
        return reactivateUserService.reactivateUser(userId);
    }


    @PostMapping("{userId}/promote")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    public UserRoleResponse promoteId(
        @PathVariable Long userId
    )
    {
        return promoteUserService.promoteUser(userId);
    }


    @PostMapping("{userId}/demote")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    public UserRoleResponse demoteId(
        @PathVariable Long userId
    )
    {
        return demoteUserService.demoteUser(userId);
    }


    @GetMapping("/search")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    public UserResponse searchUser(
            @RequestParam String identifier
    )
    {
        return searchUserService.search(identifier);
    }


}