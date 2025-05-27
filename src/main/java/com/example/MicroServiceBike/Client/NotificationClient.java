package com.example.MicroServiceBike.Client;

import com.example.MicroServiceBike.DTO.NotificationsDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "MicroServiceNotifications")
public interface NotificationClient {
    @GetMapping("/api/notifications/{id}")
    NotificationsDTO getNotificationById(@PathVariable Long id);
}
