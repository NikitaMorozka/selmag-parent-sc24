package com.nik.catalogue.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductTest {

    @Test
    @DisplayName("Product создастся с валидными данными")
    void Product_ValidData_CreatesProduct() {
        // given & when
        var product = new Product(1, "Молоко", "Свежее молоко 3.2%");

        // then
        assertThat(product.getId()).isEqualTo(1);
        assertThat(product.getTitle()).isEqualTo("Молоко");
        assertThat(product.getDetails()).isEqualTo("Свежее молоко 3.2%");
    }

    @Test
    @DisplayName("Product создастся с null details")
    void Product_NullDetails_CreatesProduct() {
        // given & when
        var product = new Product(1, "Молоко", null);

        // then
        assertThat(product.getId()).isEqualTo(1);
        assertThat(product.getTitle()).isEqualTo("Молоко");
        assertThat(product.getDetails()).isNull();
    }

    @Test
    @DisplayName("Product создастся с пустыми details")
    void Product_EmptyDetails_CreatesProduct() {
        // given & when
        var product = new Product(1, "Молоко", "");

        // then
        assertThat(product.getId()).isEqualTo(1);
        assertThat(product.getTitle()).isEqualTo("Молоко");
        assertThat(product.getDetails()).isEqualTo("");
    }

    @Test
    @DisplayName("Product создастся с нулевым ID")
    void Product_ZeroId_CreatesProduct() {
        // given & when
        var product = new Product(0, "Молоко", "Свежее молоко");

        // then
        assertThat(product.getId()).isEqualTo(0);
        assertThat(product.getTitle()).isEqualTo("Молоко");
        assertThat(product.getDetails()).isEqualTo("Свежее молоко");
    }

    @Test
    @DisplayName("Product создастся с отрицательным ID")
    void Product_NegativeId_CreatesProduct() {
        // given & when
        var product = new Product(-1, "Молоко", "Свежее молоко");

        // then
        assertThat(product.getId()).isEqualTo(-1);
        assertThat(product.getTitle()).isEqualTo("Молоко");
        assertThat(product.getDetails()).isEqualTo("Свежее молоко");
    }

    @Test
    @DisplayName("Product создастся с null ID")
    void Product_NullId_CreatesProduct() {
        // given & when
        var product = new Product(null, "Молоко", "Свежее молоко");

        // then
        assertThat(product.getId()).isNull();
        assertThat(product.getTitle()).isEqualTo("Молоко");
        assertThat(product.getDetails()).isEqualTo("Свежее молоко");
    }
} 