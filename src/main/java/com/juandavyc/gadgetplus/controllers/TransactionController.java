package com.juandavyc.gadgetplus.controllers;

import com.juandavyc.gadgetplus.services.TransactionService;
import jakarta.transaction.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.PublicKey;
import java.util.Map;

@RestController
@RequestMapping("transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<Map<String,String>> startTransaction(
            @RequestParam(name = "id") Long orderId
    ) {
        this.transactionService.executeTransaction(orderId);
        return ResponseEntity.ok(Map.of("Transaction", "OK"));
    }


}
