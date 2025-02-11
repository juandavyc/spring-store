package com.juandavyc.gadgetplus.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor


public class OrderDTO {

    private Long id;

//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS")
//    private LocalDateTime createdAt;

    private String clientName;

    private BillDTO bill;

    private List<ProductsDTO> products;

}
