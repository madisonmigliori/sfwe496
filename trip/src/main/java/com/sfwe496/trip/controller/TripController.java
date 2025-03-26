package com.sfwe496.trip.controller;

import com.sfwe496.trip.service.TripService;
import com.sfwe496.trip.model.Trip;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trips")
public class TripController {
    private final TripService tripService;

    @Autowired
    private Environment environment;

    @GetMapping("/active-profile")
    public String getActiveProfiles() {
        String[] activeProfiles = environment.getActiveProfiles();
        return "Active Profiles: " + Arrays.toString(activeProfiles);
    }


    public TripController(TripService tripService) {
        this.tripService = tripService;
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
    public Trip getTripById(@PathVariable Long id) {
        return tripService.getTripById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteTrip(@PathVariable Long id) {
        tripService.deleteTrip(id);
    }
}
