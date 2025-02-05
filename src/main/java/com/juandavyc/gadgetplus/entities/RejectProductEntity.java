package com.juandavyc.gadgetplus.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "reject_products")
@IdClass(RejectProductId.class) // llave compuesta
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RejectProductEntity {

    // llave compuesta
    @Id
    private String productName;
    @Id
    private String brandName;

    private Integer quantity;
}
