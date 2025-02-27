package com.sfwe496.trip.repository;

import com.sfwe496.trip.model;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripRepository extends JpaRepository<Trip, Long> {
}
