package com.juandavyc.gadgetplus.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductsDTO {


   /// private Long id;

    private BigInteger quantity;
    //private
    private String name;
}
