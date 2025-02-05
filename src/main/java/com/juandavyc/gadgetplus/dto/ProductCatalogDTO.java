package com.juandavyc.gadgetplus.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ProductCatalogDTO implements Serializable {

    private UUID id;
    private String name;
    private String description;
    private BigDecimal price;

    private List<CategoryDTO> categories;

}
