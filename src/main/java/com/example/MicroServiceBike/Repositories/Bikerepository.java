package com.example.MicroServiceBike.Repositories;

import com.example.MicroServiceBike.Models.Bike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Bikerepository extends JpaRepository<Bike, Long> {
    List<Bike> findByStationId(Long stationId);
}
