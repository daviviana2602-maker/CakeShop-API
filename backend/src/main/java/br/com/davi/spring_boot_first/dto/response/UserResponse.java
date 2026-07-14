package br.com.davi.spring_boot_first.dto.response;


import br.com.davi.spring_boot_first.enums.UserRoleEnum;
import br.com.davi.spring_boot_first.enums.UserStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter


public class UserResponse {

    private Long Id;
    private String name;
    private String email;
    private UserRoleEnum role;
    private UserStatusEnum status;
    private LocalDateTime createdAt;

}