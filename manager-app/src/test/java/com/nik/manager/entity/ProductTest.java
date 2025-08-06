package com.nik.manager.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductTest {

    @Test
    @DisplayName("Product создастся с валидными данными")
    void Product_ValidData_CreatesProduct() {
        var product = new Product(1, "Молоко", "Свежее молоко 3.2%");

        assertThat(product.id()).isEqualTo(1);
        assertThat(product.title()).isEqualTo("Молоко");
        assertThat(product.details()).isEqualTo("Свежее молоко 3.2%");
    }

    @Test
    @DisplayName("Product создастся с null details")
    void Product_NullDetails_CreatesProduct() {
        var product = new Product(1, "Молоко", null);

        assertThat(product.id()).isEqualTo(1);
        assertThat(product.title()).isEqualTo("Молоко");
        assertThat(product.details()).isNull();
    }

    @Test
    @DisplayName("Product создастся с пустыми details")
    void Product_EmptyDetails_CreatesProduct() {
        var product = new Product(1, "Молоко", "");

        assertThat(product.id()).isEqualTo(1);
        assertThat(product.title()).isEqualTo("Молоко");
        assertThat(product.details()).isEqualTo("");
    }

    @Test
    @DisplayName("Product создастся с нулевым ID")
    void Product_ZeroId_CreatesProduct() {
        var product = new Product(0, "Молоко", "Свежее молоко");

        assertThat(product.id()).isEqualTo(0);
        assertThat(product.title()).isEqualTo("Молоко");
        assertThat(product.details()).isEqualTo("Свежее молоко");
    }

    @Test
    @DisplayName("Product создастся с отрицательным ID")
    void Product_NegativeId_CreatesProduct() {
        var product = new Product(-1, "Молоко", "Свежее молоко");

        assertThat(product.id()).isEqualTo(-1);
        assertThat(product.title()).isEqualTo("Молоко");
        assertThat(product.details()).isEqualTo("Свежее молоко");
    }
} 