package com.example.demo.facade.listeners;

import com.example.demo.business.model.Payment;
import com.example.demo.business.services.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentListener {
    private PaymentService paymentService;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public PaymentListener(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @RabbitListener(queues = "myRiskQueue")
    public void receive(Payment payment) {
        logger.info("Received payment request: " + payment);
        paymentService.processPayment(payment);
    }
}
