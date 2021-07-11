package com.example.demo.persistance;

import com.example.demo.business.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

public interface UserRepository extends MongoRepository<User, String> {
    public User findById(UUID userId);
    public User findByEmail(String email);
}
