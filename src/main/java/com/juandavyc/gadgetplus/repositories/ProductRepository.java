package com.juandavyc.gadgetplus.repositories;

import com.juandavyc.gadgetplus.entities.OrderEntity;
import com.juandavyc.gadgetplus.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    Optional<ProductEntity> findByCatalogNameAndOrderId(String catalogName, Long id);
}
