package com.example.MicroServiceBike.Client;

import com.example.MicroServiceBike.DTO.NotificationsDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "MicroServiceNotification")
public interface NotificationClient {
    @GetMapping("/notifications/{id}")
    NotificationsDTO getNotificationById(@PathVariable Long id);
}
