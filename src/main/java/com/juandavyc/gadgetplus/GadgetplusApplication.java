package com.juandavyc.gadgetplus;

import com.juandavyc.gadgetplus.entities.*;
import com.juandavyc.gadgetplus.repositories.*;
import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;
import org.hibernate.boot.model.source.spi.EntityHierarchySource;
import org.hibernate.sql.ast.tree.from.ValuesTableGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.temporal.ValueRange;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

@SpringBootApplication
@EnableSpringDataWebSupport
public class GadgetplusApplication implements CommandLineRunner {

//    @Autowired
//    private OrderRepository orderRepository;
//
////	@Autowired
////	private BillRepository billRepository;
//
//    @Autowired
//    private ProductRepository productRepository;
//
//    @Autowired
//    private ProductCatalogRepository productCatalogRepository;
//
//    @Autowired
//    private CategoryRepository categoryRepository;
//
//    @Autowired
//    private RejectProductRepository rejectProductRepository;


    public static void main(String[] args) {
        SpringApplication.run(GadgetplusApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {


//        final var product = productCatalogRepository.findByName("Desktop for pc");
//
//        System.out.println(product);
//            var rejects = this.rejectProductRepository.findAll();
//
//            rejects.stream()
//                    .map(RejectProductEntity::getProductName)
//                    .forEach(System.out::println);

    }
}
