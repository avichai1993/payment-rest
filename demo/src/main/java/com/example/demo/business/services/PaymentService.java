package com.example.demo.business.services;

import com.example.demo.business.model.Currency;
import com.example.demo.business.model.Payment;
import com.example.demo.business.model.User;
import com.example.demo.business.RiskEngine;
import com.example.demo.facade.listeners.PaymentPublisher;
import com.example.demo.persistance.PaymentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class PaymentService {
    private PaymentPublisher paymentPublisher;
    private UsersService userService;
    private PaymentRepository paymentRepository;
    private RiskEngine riskEngine;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public PaymentService(PaymentPublisher paymentPublisher, UsersService userService, RiskEngine riskEngine, PaymentRepository paymentRepository) {
        this.paymentPublisher = paymentPublisher;
        this.userService = userService;
        this.paymentRepository = paymentRepository;
        this.riskEngine = riskEngine;
    }

    @PostConstruct
    public void initDB() {
        User user1 = userService.getUserByEmail("aaa");
        User user2 = userService.getUserByEmail("bbb");
        if(user1 != null && user2 != null) {
            pay(new Payment(43, Currency.ILS, user1.getId(), user2.getId(), user1.getPaymentMethods().stream().findFirst().get().getId()));
        }
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public void pay(Payment payment) {
        logger.info("sending payment to risk engine: " + payment);
        User user = userService.getUserById(payment.getUserId());
        if(user != null &&
                user.getPaymentMethods().stream().anyMatch(paymentMethod -> paymentMethod.getId().equals(payment.getPaymentMethodId())) &&
                userService.getUserById(payment.getPayeeId()) != null) {
            paymentPublisher.publish(payment);
        }
        else {
            logger.warn("payment is incorrect");
        }
    }

    public void processPayment(Payment payment) {
        logger.info("risk engine processing payment: " + payment);
        riskEngine.processPayment(payment);
        logger.info("risk engine processed payment: " + payment);
    }
}
