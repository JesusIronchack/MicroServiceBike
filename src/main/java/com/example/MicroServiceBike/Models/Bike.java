package com.example.MicroServiceBike.Models;

import com.example.MicroServiceBike.DTO.NotificationsDTO;
import com.example.MicroServiceBike.DTO.StationDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String model;
    private String Status;

    private Long stationId;
    private Long notificationId;

}
