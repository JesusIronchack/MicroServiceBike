package com.example.MicroServiceBike.Service;

import com.example.MicroServiceBike.Client.StationClient;
import com.example.MicroServiceBike.Models.Bike;
import com.example.MicroServiceBike.Repositories.Bikerepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BikeService {
    @Autowired
    private Bikerepository bikeRepository;

    @Autowired
    private StationClient stationClient;

    public List<Bike> getAllBikes() {
        return bikeRepository.findAll();
    }

    public Bike createBike(Bike bike) {
        return bikeRepository.save(bike);
    }

    public Bike updateBike(Long id, Bike bike) {
        bike.setId(id);
        return bikeRepository.save(bike);
    }

    public void deleteBike(Long id) {
        bikeRepository.deleteById(id);
    }

}
