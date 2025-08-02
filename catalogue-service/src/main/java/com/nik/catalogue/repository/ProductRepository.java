package com.nik.catalogue.repository;

import com.nik.catalogue.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query(name = "Product.findAllByTitleLikeIgnoringCase" , nativeQuery = true )
    List<Product> findAllByTitleIsLikeIgnoreCase(@Param("filter") String filter);
}
