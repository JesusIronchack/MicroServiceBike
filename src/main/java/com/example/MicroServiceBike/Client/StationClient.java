package com.example.MicroServiceBike.Client;

import com.example.MicroServiceBike.DTO.StationDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "MicroServiceStation")
public interface StationClient {
    @GetMapping("/station/{id}")
    StationDTO getStationById(@PathVariable Long id);
}
