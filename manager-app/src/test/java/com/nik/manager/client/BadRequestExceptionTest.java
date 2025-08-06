package com.nik.manager.client;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class BadRequestExceptionTest {

    @Test
    @DisplayName("BadRequestException создастся с ошибками")

    void BadRequestException_WithErrors_CreatesException() {
        var errors = List.of("Ошибка валидации", "Поле обязательно");
        var exception = new BadRequestException(errors);

        assertThat(exception.getErrors()).isEqualTo(errors);
        assertThat(exception.getErrors()).hasSize(2);
        assertThat(exception.getErrors()).contains("Ошибка валидации", "Поле обязательно");
    }

    @Test
    @DisplayName("BadRequestException создастся с пустым списком ошибок")
    void BadRequestException_EmptyErrors_CreatesException() {
        var errors = List.<String>of();
        var exception = new BadRequestException(errors);

        assertThat(exception.getErrors()).isEqualTo(errors);
        assertThat(exception.getErrors()).isEmpty();
    }

    @Test
    @DisplayName("BadRequestException создастся с одной ошибкой")
    void BadRequestException_SingleError_CreatesException() {
        var errors = List.of("Ошибка валидации");
        var exception = new BadRequestException(errors);

        assertThat(exception.getErrors()).isEqualTo(errors);
        assertThat(exception.getErrors()).hasSize(1);
        assertThat(exception.getErrors()).contains("Ошибка валидации");
    }
} 