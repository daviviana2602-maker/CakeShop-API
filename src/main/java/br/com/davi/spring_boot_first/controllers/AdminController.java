package br.com.davi.spring_boot_first.controllers;

import br.com.davi.spring_boot_first.dto.response.DisableUserResponse;
import br.com.davi.spring_boot_first.service.DisableUserService;
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


    @PostMapping("/create")
    public DisableUserResponse disableId(
        @PathVariable Long id
    )
    {
        return disableUserService.disableUser(id);
    }



}