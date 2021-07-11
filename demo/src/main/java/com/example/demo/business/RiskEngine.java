package com.example.demo.business;

import com.example.demo.business.model.Payment;
import com.example.demo.persistance.PaymentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class RiskEngine {
    private final double Risk = 0.7;
    private PaymentRepository paymentRepository;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public RiskEngine(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public void processPayment(Payment payment) {
        Random random = new Random();
        if(random.nextDouble() <= 0.7) {
            paymentRepository.save(payment);
            logger.info("approved payment: " + payment);
        }
        else {
            logger.info("rejected payment: " + payment);
        }
    }
}
