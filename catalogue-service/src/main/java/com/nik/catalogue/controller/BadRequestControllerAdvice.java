package com.nik.catalogue.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Locale;


@ControllerAdvice// делает класс глобальным обработчиком для всех контроллеров.
//Чтобы обрабатывать ошибки (Exception) централизованно, а не писать try-catch в каждом контроллере.
//Чтобы добавлять общие данные (модель) во все контроллеры.
//Чтобы применять @InitBinder для всех контроллеров
@RequiredArgsConstructor
public class BadRequestControllerAdvice {
    private final MessageSource messageSource;

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ProblemDetail> handleBindException(BindException exception, Locale locale) {

        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
                this.messageSource.getMessage("errors.400.title", new Object[0], "errors.400.title", locale));

        problemDetail.setProperty("errors",
                exception.getAllErrors().stream()
                        .map(ObjectError::getDefaultMessage)
                        .toList());

        return ResponseEntity
                .badRequest()
                .body(problemDetail);
    }
}
