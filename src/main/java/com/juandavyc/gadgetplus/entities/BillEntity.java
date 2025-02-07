package com.juandavyc.gadgetplus.entities;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "bill")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillEntity {

    @Id
    @Column(nullable = false, length = 64)
    private String id;

    @Column
    private BigDecimal totalAmount; // para aritmetica

    @Column(name = "client_rfc", length = 14, nullable = false)
    private String clientRfc;


    // forma 1 desaparecer recursividad infinta
    @ToString.Exclude
    @OneToOne(mappedBy = "bill", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private OrderEntity order;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BillEntity that = (BillEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}
