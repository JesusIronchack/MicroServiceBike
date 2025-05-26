package com.example.MicroServiceBike.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationsDTO {
    private Long id;
    private String message;
}
