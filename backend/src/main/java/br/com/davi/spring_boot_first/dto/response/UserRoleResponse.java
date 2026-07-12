package br.com.davi.spring_boot_first.dto.response;

import br.com.davi.spring_boot_first.enums.UserRoleEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor

public class UserRoleResponse {
    private Long userId;
    private String nome;
    private String email;
    private UserRoleEnum role;
}