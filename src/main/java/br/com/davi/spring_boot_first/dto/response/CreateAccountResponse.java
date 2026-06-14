package br.com.davi.spring_boot_first.dto.response;

import br.com.davi.spring_boot_first.enums.UserRoleEnum;
import br.com.davi.spring_boot_first.enums.UserStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor


public class CreateAccountResponse {
    private Long id;
    private String name;
    private String email;
    private UserRoleEnum role;
    private UserStatusEnum status;
}
