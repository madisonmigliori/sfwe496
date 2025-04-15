package com.sfwe496.gatewayserver.fallback;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @GetMapping("/trips")
    public ResponseEntity<String> tripServiceFallback() {
        return ResponseEntity.ok("Trip Service is temporarily unavailable. Please try again later.");
    }

    @GetMapping("/user")
    public ResponseEntity<String> userServiceFallback() {
        return ResponseEntity.ok("User Service is down. Please come back soon.");
    }
}
