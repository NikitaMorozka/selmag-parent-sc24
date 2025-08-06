package com.nik.catalogue.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "catalogue", name = "t_product")
@NamedQueries(
        @NamedQuery(name = "Product.findAllByTitleLikeIgnoringCase",
        query = "select p from Product p where p.title ilike :filter")
)
@EqualsAndHashCode
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "c_title", nullable = false)
    @Size(min = 3, max = 50)
    private String title;

    @Column(name = "c_details")
    @Size(max = 1000)
    private String details;
}
