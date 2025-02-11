package com.juandavyc.gadgetplus.entities;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.juandavyc.gadgetplus.entities.ProductEntity;
import lombok.extern.slf4j.Slf4j;

@Entity
@Table(name = "orders")
//lombok

@AllArgsConstructor
@NoArgsConstructor
@Builder

@Getter
@Setter
@ToString
@Slf4j
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

    @Column(nullable = true)
    private LocalDateTime lastUpdated;

    @Transient // la va a ignorar
    private Boolean isSaved = false;

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

    @PrePersist
    public void prePersist() {
        //log.info("prePersist: {}",this.getCreatedAt());
        this.setCreatedAt(LocalDateTime.now());
        log.info("prePersist: {}",this.getCreatedAt());
    }
    @PostPersist
    public void postPersist() {
        log.info("postPersist: {}",this.getIsSaved());
        this.setIsSaved(true);
        log.info("postPersist: {}",this.getIsSaved());
    }


    @PreUpdate
    public void preUpdate() {
        log.info("preUpdate: {}",this.getLastUpdated());
        this.setLastUpdated(LocalDateTime.now());
        log.info("preUpdate: {}",this.getLastUpdated());
    }

    @PostUpdate
    public void postUpdate() {
        log.info("postUpdate: {}",this.getLastUpdated());
    }


    @PreRemove
    public void preRemove() {
        //this.products = new ArrayList<>();
        this.products.clear();
    }

    @PostRemove
    public void postRemove() {
        log.warn("entity was removed");
    }
}
