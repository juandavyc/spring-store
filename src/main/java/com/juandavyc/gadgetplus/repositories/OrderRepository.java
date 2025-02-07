package com.juandavyc.gadgetplus.repositories;

import com.juandavyc.gadgetplus.entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;


import java.time.LocalDateTime;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {


    @Modifying
    void deleteAllByCreatedAtBefore(LocalDateTime createdAt);

    void deleteByClientName(String clientName);

    Boolean existsByClientName(String clientName);



}
