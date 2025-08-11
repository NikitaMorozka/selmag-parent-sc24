package com.nik.customer.service;

import com.nik.customer.entity.FavouriteProduct;
import reactor.core.publisher.Mono;

public interface FavoriteProductService {

    Mono<FavouriteProduct> addProductToFavourites(int productId);

    Mono<Void> removeProductFromFavourites(int productId);

    Mono<FavouriteProduct> findFavouriteProductByProduct(int productId);
}
