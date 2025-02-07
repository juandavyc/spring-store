package com.juandavyc.gadgetplus.services;

import com.juandavyc.gadgetplus.dto.BillDTO;
import com.juandavyc.gadgetplus.dto.OrderDTO;
import com.juandavyc.gadgetplus.dto.ProductsDTO;
import com.juandavyc.gadgetplus.entities.BillEntity;
import com.juandavyc.gadgetplus.entities.OrderEntity;
import com.juandavyc.gadgetplus.entities.ProductEntity;
import com.juandavyc.gadgetplus.repositories.OrderRepository;
import com.juandavyc.gadgetplus.repositories.ProductCatalogRepository;
import com.juandavyc.gadgetplus.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.bytebuddy.asm.Advice;
import org.springframework.hateoas.server.core.DelegatingLinkRelationProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

@Slf4j
@Transactional
public class OrdersCrudServiceImpl implements OrdersCrudService {

    private final OrderRepository orderRepository;

    private final ProductCatalogRepository productCatalogRepository;
    private final ProductRepository productRepository;
    private final ProductCatalogServiceImpl productCatalogServiceImpl;
    private final DelegatingLinkRelationProvider _relProvider;


    @Override
    public String create(OrderDTO order) {

        final var toInsert = this.mapOrderFromDto(order);
        return this.orderRepository.save(toInsert).getId().toString();

    }

    @Override
    public OrderDTO read(Long id) {

        return this.mapOrderFromEntity(
                this.orderRepository.findById(id)
                        .orElseThrow()
        );
    }

    @Override
    public OrderDTO update(OrderDTO orderDTO, Long id) {

        final var toUpdate = this.orderRepository.findById(id).orElseThrow();

        toUpdate.setClientName(orderDTO.getClientName());
        toUpdate.getBill().setClientRfc(orderDTO.getBill().getClientRfc());

        if (!orderDTO.getProducts().isEmpty()) {

            orderDTO.getProducts()
                    .forEach(productDTO -> {

                        final var productEntityFound = toUpdate.getProducts().stream()
                                .filter(pe -> Objects.equals(pe.getCatalog().getName(), productDTO.getName()))
                                .findFirst();

                        if (productEntityFound.isPresent()) {

                            ProductEntity existingProduct = productEntityFound.get();
                            existingProduct.setQuantity(existingProduct.getQuantity().add(productDTO.getQuantity()));

                        } else {

                            final var productFromCatalog = this.productCatalogRepository
                                    .findByName(productDTO.getName())
                                    .orElseThrow(() -> new IllegalArgumentException("cant found " + productDTO.getName()));

                            final var newProduct = ProductEntity.builder()
                                    .order(toUpdate)
                                    .quantity(productDTO.getQuantity())
                                    .catalog(productFromCatalog)
                                    .build();

                            toUpdate.getProducts().add(newProduct);
                        }

                    });

            final var total = toUpdate.getProducts()
                    .stream()
                    .map(productEntity ->
                            productEntity.getCatalog().getPrice()
                                    .multiply(BigDecimal.valueOf(productEntity.getQuantity().longValue())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            toUpdate.getBill().setTotalAmount(total);
        }

        final var updated = this.orderRepository.save(toUpdate);

        return mapOrderFromEntity(updated);
    }

    @Override
    public void delete(Long id) {
        if(orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
        }
        else{
            throw new IllegalArgumentException("Client no exists, {}"+id);
        }
    }
    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(String clientName) {
        if(orderRepository.existsByClientName(clientName)) {
            orderRepository.deleteByClientName(clientName);
        }
        else{
            throw new IllegalArgumentException("Client no exists, {}"+clientName);
        }
    }

    private OrderDTO mapOrderFromEntity(OrderEntity orderEntity) {
        final var modelMapper = new ModelMapper();

        // mapeo personalizado
        // cuando mapee setName, lo va hacer de x manera
        modelMapper
                .typeMap(ProductEntity.class, ProductsDTO.class)
                .addMappings(mapper ->
                        mapper.map(
                                entity -> entity.getCatalog().getName(), ProductsDTO::setName
                        )
                );

        return modelMapper.map(orderEntity, OrderDTO.class);
    }

    private OrderEntity mapOrderFromDto(OrderDTO orderDTO) {


        final var orderResponse = new OrderEntity();
        final var modelMapper = new ModelMapper();

//        System.out.println("mapper");
//
        modelMapper.typeMap(BillDTO.class, BillEntity.class)
                .addMappings(mapper ->
                        mapper.map(BillDTO::getIdBill, BillEntity::setId)
                );


        log.info("Before {}", orderResponse);
        modelMapper.map(orderDTO, orderResponse);
        log.info("After {}", orderResponse);

        final var total = this.getAndSetProductsAndTotal(orderDTO.getProducts(), orderResponse);
        orderResponse.getBill().setTotalAmount(total);

        log.info("After with products {}", orderResponse);


        return modelMapper.map(orderResponse, OrderEntity.class);
    }


    private BigDecimal getAndSetProductsAndTotal(List<ProductsDTO> productsDto, OrderEntity orderEntity) {

        return productsDto.stream()
                .map(productDTO -> {
                    final var productFromCatalog = productCatalogRepository
                            .findByName(productDTO.getName())
                            .orElseThrow();

                    return productFromCatalog.getPrice().multiply(BigDecimal.valueOf(productDTO.getQuantity().longValue()));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
