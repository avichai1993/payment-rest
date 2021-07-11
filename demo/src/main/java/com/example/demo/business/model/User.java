package com.example.demo.business.model;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.Collection;
import java.util.LinkedList;
import java.util.UUID;

@Document(collection = "users")
public class User {
    @Id
    private UUID id;
    private String email;
    private Collection<PaymentMethod> paymentMethods;

    public User(UUID id, String email, Collection<PaymentMethod> paymentMethods) {
        this.id = id;
        this.email = email;
        this.paymentMethods = paymentMethods;
    }

    public User() {}

    public User(String email) {
        setEmail(email);
        setPaymentMethods(new LinkedList<>());
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Collection<PaymentMethod> getPaymentMethods() {
        return paymentMethods;
    }

    public void setPaymentMethods(Collection<PaymentMethod> paymentMethods) {
        this.paymentMethods = paymentMethods;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", paymentMethods=" + paymentMethods +
                '}';
    }
}
