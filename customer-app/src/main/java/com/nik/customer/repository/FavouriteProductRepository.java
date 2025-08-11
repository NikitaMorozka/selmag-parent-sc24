package com.nik.customer.repository;

import com.nik.customer.entity.FavouriteProduct;
import reactor.core.publisher.Mono;

public interface FavouriteProductRepository {
    Mono<FavouriteProduct> save(FavouriteProduct favouriteProduct);

    Mono<Void> deleteByProductId(int productId);

    Mono<FavouriteProduct> findByProductId(int productId);
}
