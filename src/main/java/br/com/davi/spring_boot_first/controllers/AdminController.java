package br.com.davi.spring_boot_first.controllers;

import br.com.davi.spring_boot_first.dto.response.UserStatusResponse;
import br.com.davi.spring_boot_first.service.DisableUserService;
import br.com.davi.spring_boot_first.service.ReactivateUserService;
import lombok.RequiredArgsConstructor;
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


    @PostMapping("/disable/{userId}")
    public UserStatusResponse disableId(
        @PathVariable Long userId
    )
    {
        return disableUserService.disableUser(userId);
    }


    @PostMapping("/reactivate/{userId}")
    public UserStatusResponse ReactivateId(
        @PathVariable Long userId
    )
    {
        return reactivateUserService.reactivateUser(userId);
    }



}