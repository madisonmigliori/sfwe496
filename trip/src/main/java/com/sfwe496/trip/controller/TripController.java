package com.sfwe496.trip.controller;

import com.sfwe496.trip.service.TripService;
import com.sfwe496.trip.model.Trip;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/trips")
public class TripController {

    private final TripService tripService;

    @Autowired
    private Environment environment;

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @GetMapping("/active-profile")
    public String getActiveProfiles() {
        return "Active Profiles: " + Arrays.toString(environment.getActiveProfiles());
    }

    @PostMapping
    public Trip createTrip(@RequestBody Trip trip) {
        return tripService.createTrip(trip);
    }

    @GetMapping
    public List<Trip> getAllTrips() {
        return tripService.getAllTrips();
    }

    @GetMapping("/{id}")
    @CircuitBreaker(name = "tripService", fallbackMethod = "fallbackTrip")
    @Retry(name = "tripService")
    @RateLimiter(name = "tripService")
    @Bulkhead(name = "tripService", type = Bulkhead.Type.SEMAPHORE)
    public Trip getTripById(@PathVariable Long id) {
        return tripService.getTripById(id);
    }

    public Trip fallbackTrip(Long id, Throwable throwable) {
        Trip fallback = new Trip();
        fallback.setId(id);
        fallback.setDestination("Service Down");
        return fallback;
    }

    @GetMapping("/{id}/resilient")
    @CircuitBreaker(name = "tripService", fallbackMethod = "fallbackTripAsync")
    @Retry(name = "tripService")
    @TimeLimiter(name = "tripService")
    @RateLimiter(name = "tripService")
    @Bulkhead(name = "tripService", type = Bulkhead.Type.THREADPOOL)
    public CompletableFuture<Trip> getTripByIdResilient(@PathVariable Long id) {
        return CompletableFuture.supplyAsync(() -> tripService.getTripById(id));
    }

    public CompletableFuture<Trip> fallbackTripAsync(Long id, Throwable throwable) {
        Trip fallback = new Trip();
        fallback.setId(id);
        fallback.setDestination("Async Fallback");
        return CompletableFuture.completedFuture(fallback);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Trip> updateTrip(@PathVariable Long id, @RequestBody Trip trip) {
        return ResponseEntity.ok(tripService.updateTrip(id, trip));
    }

    @DeleteMapping("/{id}")
    public void deleteTrip(@PathVariable Long id) {
        tripService.deleteTrip(id);
    }
}
