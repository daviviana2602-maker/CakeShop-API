package br.com.davi.spring_boot_first.dto.response;

import br.com.davi.spring_boot_first.enums.ErrorCodeEnum;
import lombok.*;

@Getter
@AllArgsConstructor

public class ErrorResponse {

    private ErrorCodeEnum errorCode;
    private String message;

}
