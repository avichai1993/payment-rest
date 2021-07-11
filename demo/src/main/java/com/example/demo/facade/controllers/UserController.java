package com.example.demo.facade.controllers;

import java.util.LinkedList;
import java.util.List;

import com.example.demo.business.model.User;
import com.example.demo.business.model.UserSummary;
import com.example.demo.business.services.UsersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    private UsersService usersService;

    UserController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/api/users")
    public ResponseEntity<List<UserSummary>> getAllUsers() {
        List<UserSummary> userSummaries = new LinkedList<>();
        usersService.findAll().forEach(user -> {
            userSummaries.add(new UserSummary(user.getId(), user.getEmail()));
        });
        return ResponseEntity.ok(userSummaries);
    }

    @GetMapping("/api/users/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        User user = usersService.getUserByEmail(email);
        if(user == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping("/api/users")
    public ResponseEntity<User> addNewUser(@RequestBody String newUserEmail) {
        return ResponseEntity.ok(usersService.addUser(newUserEmail));
    }
}
