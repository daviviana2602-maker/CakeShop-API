package br.com.davi.spring_boot_first.dto.response;

import br.com.davi.spring_boot_first.enums.RoleEnum;
import br.com.davi.spring_boot_first.enums.UserStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor


public class CreateAccountResponse {
    private Long id;
    private String name;
    private String email;
    private RoleEnum role;
    private UserStatusEnum status;
}
