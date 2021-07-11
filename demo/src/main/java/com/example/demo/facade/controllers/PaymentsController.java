package com.example.demo.facade.controllers;

import com.example.demo.business.services.PaymentService;
import com.example.demo.business.model.Payment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PaymentsController {

    private PaymentService paymentService;

    public PaymentsController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/api/payments")
    public ResponseEntity<List<Payment>> getAllPayments() {
        return ResponseEntity.ok(paymentService.getAllPayments());
    }

    @PostMapping("/api/payments")
    public ResponseEntity<String> pay(
            @RequestBody Payment payment
    ) {
        paymentService.pay(payment);
        return ResponseEntity.ok("payment received");
    }
}
