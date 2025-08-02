package com.nik.catalogue.controller;

import com.nik.catalogue.controller.payload.NewProductPayload;
import com.nik.catalogue.entity.Product;
import com.nik.catalogue.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/catalogue-api/products")
public class ProductsRestController {

    private final ProductService productService;
    private final MessageSource messageSource;

    @GetMapping
    public List<Product> findProducts(@RequestParam(name = "filter", required = false) String filter) {
        return this.productService.findAllProducts(filter);
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@Valid @RequestBody NewProductPayload payload,
                                                 BindingResult bindingResult,
                                                 UriComponentsBuilder uriComponentsBuilder) throws BindException {
        if (bindingResult.hasErrors()) {
            if(bindingResult instanceof BindException exception){
                throw exception;
            }else {
                throw new BindException(bindingResult);
            }
        } else {
            var product = this.productService.createProduct(payload.title(), payload.details());

            return ResponseEntity
                    .created(uriComponentsBuilder
                            .replacePath("/catalogue-api/products/{productId}")
                            .build(Map.of("productId", product.getId())))
                    .body(product);
        }
    }
}