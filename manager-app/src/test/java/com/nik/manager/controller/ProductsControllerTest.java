package com.nik.manager.controller;

import com.nik.manager.client.BadRequestException;
import com.nik.manager.client.ProductsRestClient;
import com.nik.manager.controller.payload.NewProductPayload;
import com.nik.manager.entity.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.ui.ConcurrentModel;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class ProductsControllerTest {

    @Mock
    ProductsRestClient productsRestClient;

    @InjectMocks
    ProductsController productsController;

    @Test
    @DisplayName("createProduct создаст новый товар и перенаправит на страницу товара")
    void createProduct_RequestIsValid_ReturnsRedirectionToProductPage() {
        var payload = new NewProductPayload("Товар", "Описание товара");
        var model = new ConcurrentModel();

        when(this.productsRestClient.createProduct("Товар", "Описание товара"))
                .thenReturn(new Product(1, "Товар", "Описание товара"));

        var product = this.productsController.createProduct(payload, model);
        assertThat(product).isEqualTo("redirect:/catalogue/products/1");

        verify(this.productsRestClient).createProduct("Товар", "Описание товара");
        verifyNoMoreInteractions(this.productsRestClient);
    }

    @Test
    @DisplayName("createProduct вернет страницу с ошибками")
    void createProduct_RequestIsValid_ReturnsProductForWithErrors() {
        var payload = new NewProductPayload(" ", null);
        var model = new ConcurrentModel();

        when(this.productsRestClient.createProduct(" ", null))
                .thenThrow(new BadRequestException(List.of("Ошибка1", "Ошибка2")));

        var product = this.productsController.createProduct(payload, model);
        assertThat(product).isEqualTo("catalogue/products/new_product");
        assertThat(payload).isEqualTo(model.getAttribute("payload"));
        assertThat(List.of("Ошибка1", "Ошибка2")).isEqualTo(model.getAttribute("errors"));

        verify(this.productsRestClient).createProduct(" ", null);
        verifyNoMoreInteractions(this.productsRestClient);
    }

    @Test
    @DisplayName("getProductsList вернет список товаров без фильтра")
    void getProductsList_NoFilter_ReturnsProductList() {
        var model = new ConcurrentModel();
        var products = List.of(
                new Product(1, "Молоко", "Свежее молоко"),
                new Product(2, "Хлеб", "Свежий хлеб")
        );

        when(this.productsRestClient.findAllProducts(null))
                .thenReturn(products);

        var result = this.productsController.getProductsList(model, null);
        assertThat(result).isEqualTo("catalogue/products/list");
        assertThat(model.getAttribute("products")).isEqualTo(products);
        assertThat(model.getAttribute("filter")).isNull();

        verify(this.productsRestClient).findAllProducts(null);
        verifyNoMoreInteractions(this.productsRestClient);
    }

    @Test
    @DisplayName("getProductsList вернет отфильтрованный список товаров")
    void getProductsList_WithFilter_ReturnsFilteredProductList() {
        var model = new ConcurrentModel();
        var filter = "молоко";
        var products = List.of(new Product(1, "Молоко", "Свежее молоко"));

        when(this.productsRestClient.findAllProducts(filter))
                .thenReturn(products);

        var result = this.productsController.getProductsList(model, filter);
        assertThat(result).isEqualTo("catalogue/products/list");
        assertThat(model.getAttribute("products")).isEqualTo(products);
        assertThat(model.getAttribute("filter")).isEqualTo(filter);

        verify(this.productsRestClient).findAllProducts(filter);
        verifyNoMoreInteractions(this.productsRestClient);
    }

    @Test
    @DisplayName("getProductsList вернет пустой список товаров")
    void getProductsList_EmptyResult_ReturnsEmptyList() {
        var model = new ConcurrentModel();
        var products = List.<Product>of();

        when(this.productsRestClient.findAllProducts(null))
                .thenReturn(products);

        var result = this.productsController.getProductsList(model, null);
        assertThat(result).isEqualTo("catalogue/products/list");
        assertThat(model.getAttribute("products")).isEqualTo(products);

        verify(this.productsRestClient).findAllProducts(null);
        verifyNoMoreInteractions(this.productsRestClient);
    }

    @Test
    @DisplayName("getNewProductPage вернет страницу создания товара")
    void getNewProductPage_ReturnsCreateProductPage() {
        var result = this.productsController.getNewProductPage();
        assertThat(result).isEqualTo("catalogue/products/new_product");

        verifyNoInteractions(this.productsRestClient);
    }
}


