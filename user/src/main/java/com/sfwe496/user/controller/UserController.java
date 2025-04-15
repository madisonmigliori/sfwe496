package com.sfwe496.user.controller;

import com.sfwe496.user.model.User;
import com.sfwe496.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;

import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Autowired
    private Environment environment;

    @GetMapping("/users/active-profile")
    public String getActiveProfiles() {
        String[] activeProfiles = environment.getActiveProfiles();
        return "Active Profiles: " + Arrays.toString(activeProfiles);
    }

    @GetMapping("/users/{id}")
    @CircuitBreaker(name = "userServiceCB", fallbackMethod = "fallbackGetUserById")
    @Retry(name = "userServiceCB")
    @RateLimiter(name = "userServiceCB")
    @Bulkhead(name = "userServiceCB", type = Bulkhead.Type.SEMAPHORE)
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    public ResponseEntity<User> fallbackGetUserById(Long id, Throwable t) {
        User fallbackUser = new User();
        fallbackUser.setId(-1L);
        fallbackUser.setName("Unavailable");
        fallbackUser.setEmail("unavailable@example.com");
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(fallbackUser);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(user));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam String email) {
        boolean success = userService.resetPassword(email);
        if (success) {
            return ResponseEntity.ok("Password reset link sent to email.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with this email not found.");
        }
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        return ResponseEntity.ok(userService.updateUser(id, user));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        if (!userService.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("User deleted successfully");
    }
}
