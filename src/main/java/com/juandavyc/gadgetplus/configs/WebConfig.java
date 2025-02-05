package com.juandavyc.gadgetplus.configs;

import com.juandavyc.gadgetplus.entities.ProductCatalogEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@Configuration
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class WebConfig {

//    @Bean
//    public PagedResourcesAssembler<ProductCatalogEntity> pagedResourcesAssembler(){
//        return new PagedResourcesAssembler<>();
//    }

}
