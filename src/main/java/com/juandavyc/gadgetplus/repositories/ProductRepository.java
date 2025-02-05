package com.juandavyc.gadgetplus.repositories;

import com.juandavyc.gadgetplus.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
}
