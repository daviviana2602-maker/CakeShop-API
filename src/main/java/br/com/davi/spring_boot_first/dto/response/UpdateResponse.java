package br.com.davi.spring_boot_first.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor


public class UpdateResponse {

    private Long id;
    private String name;
    private String email;

}
