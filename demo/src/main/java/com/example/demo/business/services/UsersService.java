package com.example.demo.business.services;

import com.example.demo.business.model.PaymentMethod;
import com.example.demo.business.model.User;
import com.example.demo.persistance.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.LinkedList;
import java.util.UUID;

@Service
public class UsersService {
    private UserRepository userRepo;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostConstruct
    public void initDB() {
        User user = addUser("aaa");
        if(user != null) {
            addPaymentMethodToUser(user.getId(), "fun", "1243");
            addPaymentMethodToUser(user.getId(), "bun", "1434");
        }
        user = addUser("bbb");
        if(user != null) {
            addPaymentMethodToUser(user.getId(), "funny", "6789");
            addPaymentMethodToUser(user.getId(), "bunny", "0635");
        }
        user = addUser("ccc");
        if(user != null) {
            addPaymentMethodToUser(user.getId(), "funniest", "2567");
        }
        user = addUser("ddd");
    }

    public UsersService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public User addUser(String email) {
        logger.info("adding user with email :" + email);
        if(userRepo.findByEmail(email) != null) {
            logger.warn("user with email " + email + " already exists!");
            return null;
        }
        User user = new User(UUID.randomUUID(), email, new LinkedList<>());
        userRepo.save(user);
        logger.info("added user with email :" + email);
        return user;
    }

    public boolean addPaymentMethodToUser(UUID userId, String paymentName, String cardNumber) {
        logger.info("adding payment method name " + paymentName + " and card number " + cardNumber + " to user with id " + userId);
        User user = userRepo.findById(userId);
        if(user == null) {
            logger.warn("user with id " + userId + " not found");
            return false;
        }
        PaymentMethod paymentMethod = new PaymentMethod(UUID.randomUUID(), paymentName, cardNumber);
        user.getPaymentMethods().add(paymentMethod);
        userRepo.save(user);
        logger.info("added payment method name " + paymentName + " and card number " + cardNumber + " to user with id " + userId);
        return true;
    }

    public User getUserById(UUID id) {
        return userRepo.findById(id);
    }

    public User getUserByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    public Collection<User> findAll() {
        return userRepo.findAll();
    }
}