package com.example.demo.persistance;

import com.example.demo.business.model.Payment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PaymentRepository  extends MongoRepository<Payment, String> {
}
