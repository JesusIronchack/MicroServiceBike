package com.example.MicroServiceBike.Controller;

import com.example.MicroServiceBike.DTO.BikeBasicDTO;
import com.example.MicroServiceBike.DTO.BikeDTO;
import com.example.MicroServiceBike.DTO.MessageResponseDTO;
import com.example.MicroServiceBike.DTO.StationDTO;
import com.example.MicroServiceBike.Models.Bike;
import com.example.MicroServiceBike.Service.BikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bikes")
public class BikeController {
    @Autowired
    private BikeService bikeService;

    @PostMapping
    public ResponseEntity<?> createBike(@RequestBody Bike bike) {
        try {
            BikeBasicDTO bikeBasicDTO = bikeService.createBike(bike);
            return ResponseEntity.status(HttpStatus.CREATED).body(bikeBasicDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponseDTO("Error creating bike: " + e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllBikes() {
        try {
            List<BikeDTO> bikes = bikeService.getAllBikes();
            return ResponseEntity.ok(bikes);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new MessageResponseDTO("Error retrieving bikes: " + e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBikeById(@PathVariable Long id) {
        try {
            BikeDTO bikeDTO = bikeService.getBikeById(id);
            return ResponseEntity.ok(bikeDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponseDTO("Bike not found: " + e.getMessage()));
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateBike(@PathVariable Long id, @RequestBody Bike bike) {
        try {
            return ResponseEntity.ok(bikeService.updateBike(id, bike));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponseDTO("Error updating bike with ID: " + id + " - " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public void deleteBike(@PathVariable Long id) {

        bikeService.deleteBike(id);
    }

    @PutMapping("/{bikeId}/stations/{stationId}")
    public ResponseEntity<?> assignStation(@PathVariable Long bikeId, @PathVariable Long stationId) {
        try {
            Bike bike = bikeService.assignStationToBike(bikeId, stationId);
            return ResponseEntity.ok(bike);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponseDTO("Error assigning station to bike ID: " + bikeId + " - " + e.getMessage()));
        }
    }

    @PutMapping("/{bikeId}/notifications/{notificationId}")
    public ResponseEntity<?> assignNotifications(@PathVariable Long bikeId, @PathVariable Long notificationId) {
        try {
            Bike bike = bikeService.assignNotifications(bikeId, notificationId);
            return ResponseEntity.ok(bike);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponseDTO("Error assigning notifications to bike ID: " + bikeId + " - " + e.getMessage()));
        }
    }

    @GetMapping("/stations/{stationId}")
    public ResponseEntity<?> getBikesByStation(@PathVariable Long stationId) {
        try {
            List<BikeBasicDTO> bikes = bikeService.getBikesByStation(stationId);
            if (bikes.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new MessageResponseDTO("No bikes found for station ID: " + stationId));
            }
            return ResponseEntity.ok(bikes);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponseDTO("Error retrieving bikes for station ID: " + stationId + " - " + e.getMessage()));
        }
    }

}
