package com.juandavyc.gadgetplus.repositories;

import com.juandavyc.gadgetplus.dto.ReportProduct;
import com.juandavyc.gadgetplus.entities.ProductCatalogEntity;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductCatalogRepository extends JpaRepository<ProductCatalogEntity, UUID> {

    Optional<ProductCatalogEntity> findByName(String name);


//    @Query(value = "SELECT pce FROM ProductCatalogEntity pce WHERE pce.name LIKE CONCAT(:pattern,'__-%')")
//    List<ProductCatalogEntity> findByNamePattern(String pattern);

    List<ProductCatalogEntity> findByNameLike(String name);

    @Query("SELECT pce FROM ProductCatalogEntity pce LEFT JOIN FETCH pce.categories ce WHERE ce.id=:categoryId")
    List<ProductCatalogEntity> getByCategories(Long categoryId);

    @Query("SELECT new " +
            "com.juandavyc.gadgetplus.dto.ReportProduct" +
            "(pce.brand, AVG(pce.price), SUM(pce.price))" +
            "FROM ProductCatalogEntity pce GROUP BY pce.brand")
    List<ReportProduct> findAvgAndSum();

    Page<ProductCatalogEntity> findByBrand(String brand, Pageable pageable);


    @Procedure(procedureName = "count_total_products_by_brand",outputParameterName = "response")
    Integer countProductsByBrandStorageProcedure(@Param(value="brand") String brand);

}