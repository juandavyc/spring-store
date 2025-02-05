package com.juandavyc.gadgetplus.repositories;

import com.juandavyc.gadgetplus.entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

}
