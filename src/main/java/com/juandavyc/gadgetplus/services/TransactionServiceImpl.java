package com.juandavyc.gadgetplus.services;

import com.juandavyc.gadgetplus.repositories.BillRepository;
import com.juandavyc.gadgetplus.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.swing.border.Border;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
//@Transactional(noRollbackFor = IllegalArgumentException.class) //
public class TransactionServiceImpl implements TransactionService {
    private final OrderRepository orderRepository;
    private final BillRepository billRepository;

    @Override
    @Transactional
    public void executeTransaction(Long id) {
        log.info("transaction active 1 {}", TransactionSynchronizationManager.isActualTransactionActive());
        log.info("transaction name 1 {}", TransactionSynchronizationManager.getCurrentTransactionName());

        this.updateOrder(id);

    }
    // igual que tener solo @Transactional, todo sobre la transsaccion inicial
    //@Transactional(propagation = Propagation.REQUIRED)
    // se ejecuta en otra transaccion
    //@Transactional(propagation = Propagation.REQUIRES_NEW)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void updateOrder(Long id) {
        log.info("transaction active 2 {}", TransactionSynchronizationManager.isActualTransactionActive());
        log.info("transaction name 2 {}", TransactionSynchronizationManager.getCurrentTransactionName());
        final var order = orderRepository.findById(id).orElseThrow();

        order.setCreatedAt(LocalDateTime.now());
        orderRepository.save(order);

        this.validProducts(id);
        this.updateBill(order.getBill().getId());

    }
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateBill(String id) {
        log.info("transaction active 4 {}", TransactionSynchronizationManager.isActualTransactionActive());
        log.info("transaction name 4 {}", TransactionSynchronizationManager.getCurrentTransactionName());
        final var bill = billRepository.findById(id).orElseThrow();
        bill.setClientRfc("777888");
        billRepository.save(bill);
    }
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public void validProducts(Long id) {
        log.info("transaction active 3 {}", TransactionSynchronizationManager.isActualTransactionActive());
        log.info("transaction name 3 {}", TransactionSynchronizationManager.getCurrentTransactionName());
        final var order = orderRepository.findById(id).orElseThrow();

        if (order.getProducts().isEmpty()) {
            throw new IllegalArgumentException("There are no products");
        }
    }


}
