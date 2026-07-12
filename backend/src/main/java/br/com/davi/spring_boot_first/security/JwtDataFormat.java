package br.com.davi.spring_boot_first.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor

public class JwtDataFormat {
    private String userId;
    private String role;
    private String type;
}
