package br.com.davi.spring_boot_first.dto.response;

import br.com.davi.spring_boot_first.enums.UserStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor

public class DisableUserResponse {
    private Long id;
    private String nome;
    private String email;
    private UserStatusEnum status;
}
