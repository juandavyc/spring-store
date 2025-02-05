package com.juandavyc.gadgetplus.dto;

import com.juandavyc.gadgetplus.entities.CodeCategoryEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CategoryDTO {

    private CodeCategoryEnum code;
    private String description;
}
