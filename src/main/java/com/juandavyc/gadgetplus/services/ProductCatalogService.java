package com.juandavyc.gadgetplus.services;

import com.juandavyc.gadgetplus.dto.ReportProduct;
import com.juandavyc.gadgetplus.entities.ProductCatalogEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface ProductCatalogService {



    ProductCatalogEntity findById(UUID id);

    ProductCatalogEntity findByName(String name);

    List<ProductCatalogEntity> findNameLike(String name);

    List<ProductCatalogEntity> findByCategoryJoin(Long id);

    List<ReportProduct> findAndMakeReport();

    Page<ProductCatalogEntity> findAll(String field, Boolean desc, Integer page);


    Page<ProductCatalogEntity> findByBrand(String brand, Integer page);


    Integer countProductsByBrand(String brand);
}
