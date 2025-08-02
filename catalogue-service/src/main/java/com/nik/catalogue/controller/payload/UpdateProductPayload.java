package com.nik.catalogue.controller.payload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateProductPayload(
        @Size(min = 3, max = 50, message = "{catalogue.products.update.errors.title_size_is_invalid}")
        @NotNull(message = "{catalogue.products.update.errors.title_is_null}")
        String title,

        @Size(max = 1000, message = "{catalogue.products.update.errors.details_size_is_invalid}")
        String details) {
}
