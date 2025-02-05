package com.juandavyc.gadgetplus.services;

import com.juandavyc.gadgetplus.dto.ReportProduct;
import com.juandavyc.gadgetplus.entities.ProductCatalogEntity;
import com.juandavyc.gadgetplus.repositories.ProductCatalogRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true) // solo lectura

public class ProductCatalogServiceImpl implements ProductCatalogService {

    private final ProductCatalogRepository productCatalogRepository;

//    public ProductCatalogServiceImpl(
//            ProductCatalogRepository productCatalogRepository,
//            PagedResourcesAssembler<ProductCatalogEntity> pagedResourcesAssembler) {
//        this.productCatalogRepository = productCatalogRepository;
//        this.pagedResourcesAssembler = pagedResourcesAssembler;
//    }

    private final int MAX_PAGE_SIZE = 5;
    private final int MIN_PAGE_SIZE = 2;


    @Override
    public ProductCatalogEntity findById(UUID id) {
        return productCatalogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
    }

    @Override
    public ProductCatalogEntity findByName(String name) {
        return this.productCatalogRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("Product by name not found"));
    }

    @Override
    public List<ProductCatalogEntity> findNameLike(String name) {
        return this.productCatalogRepository.findByNameLike(name);
    }

    @Override
    public List<ProductCatalogEntity> findByCategoryJoin(Long id) {
        return this.productCatalogRepository.getByCategories(id);
    }

    public List<ReportProduct> findAndMakeReport() {
        return this.productCatalogRepository.findAvgAndSum();
    }


    public Page<ProductCatalogEntity> findAll(String field, Boolean desc, Integer page) {
        var sorting = Sort.by("name");
        if (Objects.nonNull(field)) {
            switch (field) {
                case "brand" -> sorting = Sort.by("brand");
                case "price" -> sorting = Sort.by("price");
                case "rating" -> sorting = Sort.by("rating");
                case "launchingDate" -> sorting = Sort.by("launchingDate");
                default -> throw new IllegalArgumentException("Invalid field," + field);
            }
        }

        sorting = (desc) ? sorting.descending() : sorting.ascending();

        return this.productCatalogRepository.findAll(
                PageRequest.of(
                        page,
                        this.MAX_PAGE_SIZE,
                        sorting
                )
        );
    }

    @Override
    public Page<ProductCatalogEntity> findByBrand(String brand, Integer page) {
        return this.productCatalogRepository.findByBrand(
                        brand,
                        PageRequest.of(page, this.MIN_PAGE_SIZE)
        );
    }

    @Override
    public Integer countProductsByBrand(String brand) {
        return this.productCatalogRepository.countProductsByBrandStorageProcedure(brand);
    }


}