package com.example.MicroServiceBike.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BikeBasicDTO {
    private Long id;
    private String model;
    private String status;
    private Long stationId;
}
