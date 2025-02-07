package com.juandavyc.gadgetplus.entities;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.juandavyc.gadgetplus.entities.ProductEntity;

@Entity
@Table(name = "orders")
//lombok

@AllArgsConstructor
@NoArgsConstructor
@Builder

@Getter
@Setter
@ToString

public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) /*postgres and mysql*/
    private Long id;

    @Column(nullable = false)
    private LocalDateTime createdAt;


    @Column(length = 32, nullable = false)
    private String clientName;
    // si hago un save, se guarde primero el hijo
    // CascadeType.PERSIST
    // CascadeType.MERGE guardar ambas
    // order.getBill().setRfc("new-rfc")
    // cascade ={...}
    @OneToOne(fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    @JoinColumn(name = "id_bill", nullable = false, unique = true)
    private BillEntity bill;

    //
    @OneToMany(mappedBy = "order",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<ProductEntity> products = new ArrayList<>();


    public void addProduct(ProductEntity product) {
        this.products.add(product);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        OrderEntity that = (OrderEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}
