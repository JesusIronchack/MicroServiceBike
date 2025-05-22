package com.example.MicroServiceBike.Controller;

import com.example.MicroServiceBike.Models.Bike;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public List<Bike> getAllBikes() {
        return bikeService.getAllBikes();
    }

    @PutMapping("/{id}")
    public Bike updateBike(@PathVariable Long id, @RequestBody Bike bike) {
        return bikeService.updateBike(id, bike);
    }

    @DeleteMapping("/{id}")
    public void deleteBike(@PathVariable Long id) {
        bikeService.deleteBike(id);
    }

    @GetMapping("/{bikeId}/station/{stationId}")
    public StationDTO assignStation(@PathVariable Long bikeId, @PathVariable Long stationId) {
        return bikeService.getStationByBike(stationId);
    }
}
