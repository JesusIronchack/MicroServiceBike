package com.example.MicroServiceBike.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BikeDTO {
    private Long id;
    private String model;
    private String status;
    private StationDTO station;
    private NotificationsDTO notifications;

    public BikeDTO(Long id, String model, String status, NotificationsDTO notifications, StationDTO station) {
        this.id = id;
        this.model = model;
        this.status = status;
        this.notifications = notifications;
        this.station = station;
    }
}
