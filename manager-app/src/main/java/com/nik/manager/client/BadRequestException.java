package com.nik.manager.client;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class BadRequestException extends RuntimeException{
    private final List<String> errors;

    public BadRequestException(List<String> errors) {
        this.errors = errors;
    }
}
