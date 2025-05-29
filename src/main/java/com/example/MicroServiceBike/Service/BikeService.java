package com.example.MicroServiceBike.Service;

import com.example.MicroServiceBike.Client.NotificationClient;
import com.example.MicroServiceBike.Client.StationClient;
import com.example.MicroServiceBike.DTO.BikeBasicDTO;
import com.example.MicroServiceBike.DTO.BikeDTO;
import com.example.MicroServiceBike.DTO.NotificationsDTO;
import com.example.MicroServiceBike.DTO.StationDTO;
import com.example.MicroServiceBike.Models.Bike;
import com.example.MicroServiceBike.Repositories.BikeRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BikeService {
    @Autowired
    private BikeRepository bikeRepository;

    @Autowired
    private NotificationClient notificationClient;

    @Autowired
    private StationClient stationClient;


    public List<BikeDTO> getAllBikes() {
        try {
            List<Bike> bikes = bikeRepository.findAll();

            return bikes.stream().map(bike -> {
                StationDTO stationDTO = null;
                try {
                    if (bike.getStationId() != null) {
                        stationDTO = stationClient.getStationById(bike.getStationId());
                    }
                } catch (FeignException e) {
                    System.out.println("Warning: Station service is unavailable.");
                }

                NotificationsDTO notificationsDTO = null;
                try{
                    if (bike.getNotificationId() != null) {
                            notificationsDTO = notificationClient.getNotificationById(bike.getNotificationId());
                        }
                } catch (FeignException e) {
                    System.out.println("Warning: Notification service is unavailable.");
                }

                return new BikeDTO(bike.getId(), bike.getModel(), bike.getStatus(), notificationsDTO, stationDTO);
            }).collect(Collectors.toList());
        } catch (RuntimeException e) {
            System.out.println("Error displaying bikes: " + e.getMessage());
            return List.of();
        }
    }


    public BikeDTO getBikeById(Long id) {
        Bike bike = bikeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bike not found with ID: " + id));

        NotificationsDTO notificationsDTO = null;
        try {
            if (bike.getNotificationId() != null) {
                notificationsDTO = notificationClient.getNotificationById(bike.getNotificationId());
            }
        } catch (FeignException e) {
            System.out.println("Warning: Notification service is unavailable.");
        }

        StationDTO stationDTO = null;
        try {
            if (bike.getStationId() != null) {
                stationDTO = stationClient.getStationById(bike.getStationId());
            }
        } catch (FeignException e) {
            System.out.println("Warning: Station service is unavailable.");
        }

        return new BikeDTO(bike.getId(), bike.getModel(), bike.getStatus(), notificationsDTO, stationDTO);
    }


    public BikeBasicDTO createBike(Bike bike) {
        try {
            Bike savedBike = bikeRepository.save(bike);
            return new BikeBasicDTO(savedBike.getId(), savedBike.getModel(), savedBike.getStatus(), savedBike.getStationId());
        }catch (RuntimeException e) {
            throw new RuntimeException("Error creating bike: " + e.getMessage());
        }
    }


    public Bike updateBike(Long id, Bike bike) {
        try{
            bike.setId(id);
            return bikeRepository.save(bike);
        } catch (RuntimeException e) {
            throw new RuntimeException("Error updating bike: " + e.getMessage());
        }
    }


    public void deleteBike(Long id) {
        bikeRepository.deleteById(id);
    }


    public StationDTO getStationByBike(Long stationId) {
        return stationClient.getStationById(stationId);
    }


    public Bike assignStationToBike(Long bikeId, Long stationId) {
        try {
            Bike bike = bikeRepository.findById(bikeId)
                    .orElseThrow(() -> new RuntimeException("Bike not found"));

                StationDTO stationDTO = stationClient.getStationById(stationId);
                bike.setStationId(stationDTO.getId());
            return bikeRepository.save(bike);

        } catch (FeignException.NotFound e) {
            throw new RuntimeException("Station with ID " + stationId + " not found in station-service");
        }
    }

    public Bike assignNotifications(Long bikeId, Long notificationId) {
        try {
        Bike bike = bikeRepository.findById(bikeId)
                .orElseThrow(() -> new RuntimeException("Bike not found"));

            NotificationsDTO notificationsDTO = notificationClient.getNotificationById(notificationId);
            bike.setNotificationId(notificationsDTO.getId());

            return bikeRepository.save(bike);

        } catch (FeignException.NotFound e) {
            throw new RuntimeException("Station with ID " + notificationId + " not found in station-service");
        }
    }


    public List<BikeBasicDTO> getBikesByStation(Long stationId) {
        try {
            List<Bike> bikes = bikeRepository.findByStationId(stationId);

            return bikes.stream()
                    .map(bike -> new BikeBasicDTO(bike.getId(), bike.getModel(), bike.getStatus(), bike.getStationId()))
                    .collect(Collectors.toList());
        } catch (RuntimeException e) {
            throw new RuntimeException("No bikes found for station ID " + stationId + ": " + e.getMessage());
        }
    }

}
