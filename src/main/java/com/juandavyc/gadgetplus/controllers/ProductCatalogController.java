package com.juandavyc.gadgetplus.controllers;

import com.juandavyc.gadgetplus.dto.ReportProduct;
import com.juandavyc.gadgetplus.entities.ProductCatalogEntity;
import com.juandavyc.gadgetplus.services.ProductCatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "product-catalog")
@RequiredArgsConstructor
public class ProductCatalogController {

    private final ProductCatalogService productCatalogService;

    @GetMapping(path = "{idCatalog}")
    public ResponseEntity<ProductCatalogEntity> getById(@PathVariable(name = "idCatalog") String id) {
        return ResponseEntity.ok(this.productCatalogService.findById(UUID.fromString(id)));
    }


    @GetMapping(path = "name/{name}")
    public ResponseEntity<ProductCatalogEntity> getByName(@PathVariable String name) {
        return ResponseEntity.ok(this.productCatalogService.findByName(name));
    }

    //@GetMapping(path = "rating/{rating}/discount/{discount}")
    @GetMapping(path = "filters")
    public ResponseEntity<List<ProductCatalogEntity>> getByNameLike(
            @RequestParam Long id
    ) {
        return ResponseEntity.ok(this.productCatalogService.findByCategoryJoin(id));
    }

    @GetMapping(path = "report")
    public ResponseEntity<List<ReportProduct>> getReport() {
        return ResponseEntity.ok(this.productCatalogService.findAndMakeReport());
    }


    @GetMapping(path = "all")
    public ResponseEntity<Page<ProductCatalogEntity>> getAll(
            @RequestParam(required = false) String field,
            @RequestParam Boolean desc,
            @RequestParam Integer page
    ) {
        return ResponseEntity.ok(this.productCatalogService.findAll(field, desc, page));
    }

    @GetMapping(path = "all/brand")
    public ResponseEntity<Page<ProductCatalogEntity>> getAllByBrand(
            @RequestParam String field,
            @RequestParam Integer page
    ) {
        return ResponseEntity.ok(this.productCatalogService.findByBrand(field, page));
    }

    @GetMapping(path = "brand-count/{brand}")
    public ResponseEntity<Integer> getCountBrand(
          @PathVariable String brand
    ) {
        return ResponseEntity.ok(this.productCatalogService.countProductsByBrand(brand));
    }

}
