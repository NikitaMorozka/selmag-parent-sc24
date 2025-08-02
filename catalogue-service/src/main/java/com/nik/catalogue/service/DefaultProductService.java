package com.nik.catalogue.service;

import com.nik.catalogue.entity.Product;
import com.nik.catalogue.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class DefaultProductService implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<Product> findAllProducts(String filter) {
        if(filter != null && !filter.isBlank()){
            return this.productRepository.findAllByTitleIsLikeIgnoreCase("%" + filter + "%");
        }
        return this.productRepository.findAll();
    }

    @Override
    @Transactional
    public Product createProduct(String title, String details) {
        return this.productRepository.save(new Product(null, title, details));
    }

    @Override
    public Optional<Product> findProduct(int productId) {
        return this.productRepository.findById(productId);
    }

    @Override
    @Transactional
    public void updateProduct(Integer id, String title, String details) {
        this.productRepository.findById(id).ifPresentOrElse(product -> {
            product.setTitle(title);
            product.setDetails(details);
        }, () -> {
            throw new NoSuchElementException();
        });
    }

    @Override
    @Transactional
    public void deleteProduct(Integer id) {
        this.productRepository.deleteById(id);
    }
}
