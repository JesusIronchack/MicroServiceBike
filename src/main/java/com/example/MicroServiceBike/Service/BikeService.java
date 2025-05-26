package com.example.MicroServiceBike.Service;

import com.example.MicroServiceBike.Client.NotificationClient;
import com.example.MicroServiceBike.Client.StationClient;
import com.example.MicroServiceBike.DTO.BikeDTO;
import com.example.MicroServiceBike.DTO.NotificationsDTO;
import com.example.MicroServiceBike.DTO.StationDTO;
import com.example.MicroServiceBike.Models.Bike;
import com.example.MicroServiceBike.Repositories.Bikerepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BikeService {
    @Autowired
    private Bikerepository bikeRepository;

    @Autowired
    private NotificationClient notificationClient;

    @Autowired
    private StationClient stationClient;

    public List<BikeDTO> getAllBikes() {
        List<Bike> bikes = bikeRepository.findAll();

        return bikes.stream().map(bike -> {
            BikeDTO bikeDTO = new BikeDTO(bike.getId(), bike.getModel(), bike.getStatus(), null, null);

            // Consultar la estación asociada si existe
            if (bike.getStationId() != null) {
                StationDTO station = stationClient.getStationById(bike.getStationId());
                bikeDTO.setStation(station);
            }

            // Consultar la notificación asociada si existe
            if (bike.getNotificationsId() != null) {
                NotificationsDTO notifications = notificationClient.getNotificationById(bike.getNotificationsId());
                bikeDTO.setNotifications(notifications);
            }

            return bikeDTO;
        }).collect(Collectors.toList());
    }

    public Bike getBikeById(Long id) {
        return bikeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bike not found with ID: " + id));
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

    public StationDTO getStationByBike(Long stationId) {
        return stationClient.getStationById(stationId);
    }

    public Bike assignStationToBike(Long bikeId, Long stationId) {
        Bike bike = bikeRepository.findById(bikeId)
                .orElseThrow(() -> new RuntimeException("Bike not found"));

        try {
            StationDTO station = stationClient.getStationById(stationId);
            bike.setStationId(station.getId());

        } catch (FeignException.NotFound e) {
            throw new RuntimeException("Station with ID " + stationId + " not found in station-service");
        }

        return bikeRepository.save(bike);
    }
}
