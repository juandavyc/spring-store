package com.juandavyc.gadgetplus.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigInteger;

@Entity
@Table(name = "products")
//lombok
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) /*postgres and mysql*/
    private Long id;

    private BigInteger quantity;

    @ManyToOne
    @JoinColumn(name = "id_order")
    @ToString.Exclude
    private OrderEntity order;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_product_catalog", unique = true)

    private ProductCatalogEntity catalog;

}
