package com.nik.customer.repository;

import com.nik.customer.entity.ProductReview;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Service
public class InMemoryProductReviewRepository implements ProductReviewRepository {

    private final List<ProductReview> productReviews = Collections.synchronizedList(new LinkedList<>());
    @Override
    public Mono<ProductReview> save(ProductReview productReview) {
         this.productReviews.add(productReview);
         return Mono.just(productReview);
    }

    @Override
    public Flux<ProductReview> findAllProductId(int productId) {
        return Flux.fromIterable(this.productReviews).filter(productReview -> productReview.getProductId()==productId);
    }
}
