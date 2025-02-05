package com.juandavyc.gadgetplus.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Entity
// @index, es mejor en tablas grandes y estaticas...
@Table(name = "products_catalog", indexes = {
        @Index(name = "idx_product_name", columnList = "product_name")
})
//lombok
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductCatalogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "product_name", length = 64)
    private String name;
    @Column(name = "brand_name", length = 64)
    private String brand;
    private String description;
    private BigDecimal price;
    private LocalDate launchingDate;
    private Boolean isDiscount;
    private Short rating;

    @ManyToMany(
            fetch = FetchType.EAGER,
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH
            }
    )
    @JoinTable(
            name = "product_join_category",
            joinColumns = @JoinColumn(name = "id_product"),
            inverseJoinColumns = @JoinColumn(name = "id_category")
    )
    private List<CategoryEntity> categories = new LinkedList<>(); // arrayList same

    // relacion inversa, cuando quiera añadirle categorias
    public void addCategory(CategoryEntity category) {
        this.categories.add(category);
    }

}
