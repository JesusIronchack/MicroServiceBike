package com.example.MicroServiceBike.Controller;

import com.example.MicroServiceBike.DTO.BikeDTO;
import com.example.MicroServiceBike.DTO.StationDTO;
import com.example.MicroServiceBike.Models.Bike;
import com.example.MicroServiceBike.Service.BikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bikes")
public class BikeController {
    @Autowired
    private BikeService bikeService;

    @PostMapping
    public Bike createBike(@RequestBody Bike bike) {

        return bikeService.createBike(bike);
    }

    @GetMapping
    public List<BikeDTO> getAllBikes() {
        return bikeService.getAllBikes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bike> getBikeById(@PathVariable Long id) {
        Bike bike = bikeService.getBikeById(id);
        return ResponseEntity.ok(bike);
    }


    @PutMapping("/{id}")
    public Bike updateBike(@PathVariable Long id, @RequestBody Bike bike) {

        return bikeService.updateBike(id, bike);
    }

    @DeleteMapping("/{id}")
    public void deleteBike(@PathVariable Long id) {

        bikeService.deleteBike(id);
    }

    @PutMapping("/{bikeId}/stations/{stationId}")
    public ResponseEntity<Bike> assignStation(@PathVariable Long bikeId, @PathVariable Long stationId) {
        Bike bike = bikeService.assignStationToBike(bikeId, stationId);
        return ResponseEntity.ok(bike);
    }

    @PutMapping("/{bikeId}/notifications/{notificationId}")
    public ResponseEntity<Bike> assignNotifications(@PathVariable Long bikeId, @PathVariable Long notificationId) {
        Bike bike = bikeService.assignNotifications(bikeId, notificationId);
        return ResponseEntity.ok(bike);
    }

}
