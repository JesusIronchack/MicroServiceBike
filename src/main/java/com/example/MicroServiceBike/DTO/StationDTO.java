package com.example.MicroServiceBike.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StationDTO {
    private Long id;
    private String name;
    private String location;

}
