package br.com.davi.spring_boot_first.exception;

public class ConflictException extends RuntimeException {

    public ConflictException(String message) {
        super(message);
    }

}
