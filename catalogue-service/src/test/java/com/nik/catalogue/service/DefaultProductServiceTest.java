package com.nik.catalogue.service;

import com.nik.catalogue.entity.Product;
import com.nik.catalogue.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class DefaultProductServiceTest {

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    DefaultProductService productService;

    @Test
    @DisplayName("findAllProducts вернет список всех товаров без фильтра")
    void findAllProducts_NoFilter_ReturnsAllProducts() {
        // given
        var products = List.of(
                new Product(1, "Молоко", "Свежее молоко"),
                new Product(2, "Хлеб", "Свежий хлеб")
        );
        when(this.productRepository.findAll()).thenReturn(products);

        // when
        var result = this.productService.findAllProducts(null);

        // then
        assertThat(result).isEqualTo(products);
        verify(this.productRepository).findAll();
        verifyNoMoreInteractions(this.productRepository);
    }

    @Test
    @DisplayName("findAllProducts вернет отфильтрованный список товаров")
    void findAllProducts_WithFilter_ReturnsFilteredProducts() {
        // given
        var filter = "молоко";
        var products = List.of(new Product(1, "Молоко", "Свежее молоко"));
        when(productRepository.findAllByTitleIsLikeIgnoreCase(anyString()))
                .thenReturn(products);
        // when
        var result = this.productService.findAllProducts(filter);

        // then
        assertThat(result).isEqualTo(products);
    }

    @Test
    @DisplayName("findProduct вернет товар по ID")
    void findProduct_ValidId_ReturnsProduct() {
        // given
        var product = new Product(1, "Молоко", "Свежее молоко");
        when(this.productRepository.findById(1)).thenReturn(Optional.of(product));

        // when
        var result = this.productService.findProduct(1);

        // then
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(product);
        verify(this.productRepository).findById(1);
        verifyNoMoreInteractions(this.productRepository);
    }

    @Test
    @DisplayName("findProduct вернет пустой Optional для несуществующего товара")
    void findProduct_InvalidId_ReturnsEmptyOptional() {
        // given
        when(this.productRepository.findById(999)).thenReturn(Optional.empty());

        // when
        var result = this.productService.findProduct(999);

        // then
        assertThat(result).isEmpty();
        verify(this.productRepository).findById(999);
        verifyNoMoreInteractions(this.productRepository);
    }

    @Test
    @DisplayName("createProduct создаст новый товар")
    void createProduct_ValidData_ReturnsCreatedProduct() {
        // given
        var productToSave = new Product(null, "Молоко", "Свежее молоко");

        var savedProduct = new Product(1, "Молоко", "Свежее молоко");
        when(this.productRepository.save(productToSave)).thenReturn(savedProduct);

        // when
        var result = this.productService.createProduct("Молоко", "Свежее молоко");

        // then
        assertThat(result).isEqualTo(savedProduct);
    }

    @Test
    @DisplayName("updateProduct обновит существующий товар")
    void updateProduct_ValidId_UpdatesProduct() {
        // given
        var existingProduct = new Product(1, "Молоко", "Свежее молоко");
        var updatedProduct = new Product(1, "Молоко обновленное", "Свежее молоко 3.2%");
        when(this.productRepository.findById(1)).thenReturn(Optional.of(existingProduct));
        when(this.productRepository.save(updatedProduct)).thenReturn(updatedProduct);

        // when
        this.productService.updateProduct(1, "Молоко обновленное", "Свежее молоко 3.2%");

        // then
        Optional<Product> result = productService.findProduct(1);
        assertThat(result.get()).isEqualTo(updatedProduct);

    }

    @Test
    @DisplayName("deleteProduct удалит товар")
    void deleteProduct_ValidId_DeletesProduct() {
        // given
        this.productService.deleteProduct(1);

        // then
        verify(this.productRepository).deleteById(1);
        verifyNoMoreInteractions(this.productRepository);
    }
} 