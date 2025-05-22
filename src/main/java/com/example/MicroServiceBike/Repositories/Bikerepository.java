package com.example.MicroServiceBike.Repositories;

import com.example.MicroServiceBike.Models.Bike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Bikerepository extends JpaRepository<Bike, Long> {
}
