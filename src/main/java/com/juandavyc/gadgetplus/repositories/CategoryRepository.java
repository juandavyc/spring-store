package com.juandavyc.gadgetplus.repositories;

import com.juandavyc.gadgetplus.entities.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

}
