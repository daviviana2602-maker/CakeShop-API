package br.com.davi.spring_boot_first.exception;

import br.com.davi.spring_boot_first.enums.ErrorCodeEnum;

public class BadRequestException extends RuntimeException {

    private final ErrorCodeEnum errorCode;

    public BadRequestException(ErrorCodeEnum errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public ErrorCodeEnum getErrorCode() {
        return errorCode;
    }
}
