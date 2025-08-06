package com.nik.manager.controller;

import com.nik.manager.client.BadRequestException;
import com.nik.manager.client.ProductsRestClient;
import com.nik.manager.controller.payload.UpdateProductPayload;
import com.nik.manager.entity.Product;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.context.MessageSource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.ui.ConcurrentModel;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class ProductControllerTest {

    @Mock
    HttpServletResponse response;

    @Mock
    ProductsRestClient productsRestClient;

    @Mock
    MessageSource messageSource;

    @InjectMocks
    ProductController productController;

    @Test
    @DisplayName("product вернет товар по ID")
    void product_ValidId_ReturnsProduct() {
        var product = new Product(1, "Молоко", "Свежее молоко");
        when(this.productsRestClient.findProduct(1))
                .thenReturn(Optional.of(product));

        var result = this.productController.product(1);
        assertThat(result).isEqualTo(product);

        verify(this.productsRestClient).findProduct(1);
        verifyNoMoreInteractions(this.productsRestClient);
    }

    @Test
    @DisplayName("product выбросит исключение для несуществующего товара")
    void product_InvalidId_ThrowsNoSuchElementException() {
        when(this.productsRestClient.findProduct(999))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> this.productController.product(999))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("catalogue.errors.product.not_found");

        verify(this.productsRestClient).findProduct(999);
        verifyNoMoreInteractions(this.productsRestClient);
    }

    @Test
    @DisplayName("getProduct вернет страницу товара")
    void getProduct_ReturnsProductPage() {
        var result = this.productController.getProduct();
        assertThat(result).isEqualTo("catalogue/products/product");

        verifyNoInteractions(this.productsRestClient);
    }

    @Test
    @DisplayName("getProductEditPage вернет страницу редактирования")
    void getProductEditPage_ReturnsEditPage() {
        var result = this.productController.getProductEditPage();
        assertThat(result).isEqualTo("catalogue/products/edit");

        verifyNoInteractions(this.productsRestClient);
    }

    @Test
    @DisplayName("updateProduct обновит товар и перенаправит на страницу товара")
    void updateProduct_ValidData_ReturnsRedirectionToProductPage() {
        var product = new Product(1, "Молоко", "Свежее молоко");
        var payload = new UpdateProductPayload("Молоко обновленное", "Свежее молоко 3.2%");
        var model = new ConcurrentModel();

        doNothing().when(this.productsRestClient).updateProduct(1, "Молоко обновленное", "Свежее молоко 3.2%");

        var result = this.productController.updateProduct(product, payload, model);
        assertThat(result).isEqualTo("redirect:/catalogue/products/1");

        verify(this.productsRestClient).updateProduct(1, "Молоко обновленное", "Свежее молоко 3.2%");
        verifyNoMoreInteractions(this.productsRestClient);
    }

    @Test
    @DisplayName("updateProduct вернет страницу с ошибками при невалидных данных")
    void updateProduct_InvalidData_ReturnsEditPageWithErrors() {
        var product = new Product(1, "Молоко", "Свежее молоко");
        var payload = new UpdateProductPayload("", null);
        var model = new ConcurrentModel();

        doThrow(new BadRequestException(java.util.List.of("Ошибка валидации")))
                .when(this.productsRestClient).updateProduct(1, "", null);

        var result = this.productController.updateProduct(product, payload, model);
        assertThat(result).isEqualTo("catalogue/products/edit");
        assertThat(model.getAttribute("payload")).isEqualTo(payload);
        assertThat(model.getAttribute("errors")).isEqualTo(java.util.List.of("Ошибка валидации"));

        verify(this.productsRestClient).updateProduct(1, "", null);
        verifyNoMoreInteractions(this.productsRestClient);
    }

    @Test
    @DisplayName("deleteProduct удалит товар и перенаправит на список")
    void deleteProduct_ValidProduct_ReturnsRedirectionToList() {
        var product = new Product(1, "Молоко", "Свежее молоко");

        doNothing().when(this.productsRestClient).deleteProduct(1);

        var result = this.productController.deleteProduct(product);
        assertThat(result).isEqualTo("redirect:/catalogue/products/list");

        verify(this.productsRestClient).deleteProduct(1);
        verifyNoMoreInteractions(this.productsRestClient);
    }


    @Test
    @DisplayName("handlerNoSuchElementException обработает исключение и вернет страницу ошибки")
    void handlerNoSuchElementException_ReturnsErrorPage() {
        var exception = new NoSuchElementException("catalogue.errors.product.not_found");
        var model = new ConcurrentModel();
        var locale = java.util.Locale.getDefault();

        when(this.messageSource.getMessage("catalogue.errors.product.not_found", new Object[0], "catalogue.errors.product.not_found", locale))
                .thenReturn("Товар не найден");


        var result = this.productController.handlerNoSuchElementException(exception, model, response, locale);
        assertThat(result).isEqualTo("errors/404");
        assertThat(model.getAttribute("error")).isEqualTo("Товар не найден");

        verify(this.messageSource).getMessage("catalogue.errors.product.not_found", new Object[0], "catalogue.errors.product.not_found", locale);
        verifyNoMoreInteractions(this.messageSource);
    }
}


