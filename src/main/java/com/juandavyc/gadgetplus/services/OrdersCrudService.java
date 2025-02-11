package com.juandavyc.gadgetplus.services;

import com.juandavyc.gadgetplus.dto.OrderDTO;

import java.lang.invoke.StringConcatException;

public interface OrdersCrudService {

    String create(OrderDTO order);

    OrderDTO read(Long id);

    OrderDTO update(OrderDTO order, Long id);

    void deleteById(Long id);

    void delete(String clientName);

}
