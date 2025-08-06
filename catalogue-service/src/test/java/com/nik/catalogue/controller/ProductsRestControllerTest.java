package com.nik.catalogue.controller;

import com.nik.catalogue.controller.payload.NewProductPayload;
import com.nik.catalogue.entity.Product;
import com.nik.catalogue.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class ProductsRestControllerTest {

    @Mock
    ProductService productService;

    @Mock
    MessageSource messageSource;

    @Mock
    BindingResult bindingResult;

    @InjectMocks
    ProductsRestController productsRestController;

    @Test
    @DisplayName("findProducts вернет список товаров без фильтра")
    void findProducts_NoFilter_ReturnsProductList() {
        // given
        var products = List.of(
                new Product(1, "Молоко", "Свежее молоко"),
                new Product(2, "Хлеб", "Свежий хлеб")
        );
        when(this.productService.findAllProducts(null)).thenReturn(products);

        // when
        var result = this.productsRestController.findProducts(null);

        // then
        assertThat(result).isEqualTo(products);
        verify(this.productService).findAllProducts(null);
        verifyNoMoreInteractions(this.productService);
    }

    @Test
    @DisplayName("findProducts вернет отфильтрованный список товаров")
    void findProducts_WithFilter_ReturnsFilteredProductList() {
        // given
        var filter = "молоко";
        var products = List.of(new Product(1, "Молоко", "Свежее молоко"));
        when(this.productService.findAllProducts(filter)).thenReturn(products);

        // when
        var result = this.productsRestController.findProducts(filter);

        // then
        assertThat(result).isEqualTo(products);
        verify(this.productService).findAllProducts(filter);
        verifyNoMoreInteractions(this.productService);
    }

    @Test
    @DisplayName("createProduct создаст товар и вернет ResponseEntity")
    void createProduct_ValidPayload_ReturnsCreatedProduct() throws BindException {
        // given
        var payload = new NewProductPayload("Молоко", "Свежее молоко 3.2%");
        var createdProduct = new Product(1, "Молоко", "Свежее молоко 3.2%");
        when(this.bindingResult.hasErrors()).thenReturn(false);
        when(this.productService.createProduct("Молоко", "Свежее молоко 3.2%")).thenReturn(createdProduct);

        // when
        var result = this.productsRestController.createProduct(payload, this.bindingResult, UriComponentsBuilder.newInstance());

        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(result.getBody()).isEqualTo(createdProduct);
        verify(this.productService).createProduct("Молоко", "Свежее молоко 3.2%");
        verifyNoMoreInteractions(this.productService);
    }

    @Test
    @DisplayName("createProduct выбросит BindException при ошибках валидации")
    void createProduct_InvalidPayload_ThrowsBindException() {
        // given
        var payload = new NewProductPayload("", null); // невалидный payload
        when(this.bindingResult.hasErrors()).thenReturn(true);

        // when & then
        assertThatThrownBy(() -> this.productsRestController.createProduct(payload, this.bindingResult, UriComponentsBuilder.newInstance()))
                .isInstanceOf(BindException.class);
    }
}
