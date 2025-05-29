package com.example.MicroServiceBike.Service;

import com.example.MicroServiceBike.DTO.BikeDTO;
import com.example.MicroServiceBike.DTO.NotificationsDTO;
import com.example.MicroServiceBike.DTO.StationDTO;
import com.example.MicroServiceBike.Models.Bike;
import com.example.MicroServiceBike.Repositories.BikeRepository;
import com.example.MicroServiceBike.Client.NotificationClient;
import com.example.MicroServiceBike.Client.StationClient;
import feign.FeignException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BikeServiceTest {
    @Mock
    private BikeRepository bikeRepository;

    @Mock
    private NotificationClient notificationClient;

    @Mock
    private StationClient stationClient;

    @InjectMocks
    private BikeService bikeService;


    private Bike testBike1;
    private Bike testBike2;
    private StationDTO testStationDTO;
    private NotificationsDTO testNotificationsDTO;


    @BeforeEach
    void setUp() {
        testBike1 = new Bike();
        testBike1.setId(1L);
        testBike1.setModel("Mountain Bike");
        testBike1.setStatus("AVAILABLE");
        testBike1.setStationId(10L);
        testBike1.setNotificationId(20L);

        testBike2 = new Bike();
        testBike2.setId(2L);
        testBike2.setModel("Road Bike");
        testBike2.setStatus("IN_USE");

        testStationDTO = new StationDTO();
        testStationDTO.setId(10L);
        testStationDTO.setName("Central Station");
        testStationDTO.setLocation("Downtown");

        testNotificationsDTO = new NotificationsDTO();
        testNotificationsDTO.setId(20L);
        testNotificationsDTO.setMessage("Maintenance required");
    }


    @Test
    void testGetAllBikes() {
        List<Bike> mockBikes = Arrays.asList(testBike1, testBike2);
        when(bikeRepository.findAll()).thenReturn(mockBikes);
        when(stationClient.getStationById(10L)).thenReturn(testStationDTO);
        when(notificationClient.getNotificationById(20L)).thenReturn(testNotificationsDTO);


        List<BikeDTO> result = bikeService.getAllBikes();

        assertNotNull(result, "El resultado no debería ser null");
        assertEquals(2, result.size(), "Deberían retornarse 2 bicicletas");

        // Verificamos la primera bicicleta
        BikeDTO bike1DTO = result.get(0);
        assertEquals(1L, bike1DTO.getId());
        assertEquals("Mountain Bike", bike1DTO.getModel());
        assertEquals("AVAILABLE", bike1DTO.getStatus());

        // Verificamos la segunda bicicleta
        BikeDTO bike2DTO = result.get(1);
        assertEquals(2L, bike2DTO.getId());
        assertEquals("Road Bike", bike2DTO.getModel());
        assertEquals("IN_USE", bike2DTO.getStatus());

        // Verificamos que se llamaron los métodos correctos
        verify(bikeRepository, times(1)).findAll();
        verify(stationClient, times(1)).getStationById(10L);
        verify(notificationClient, times(1)).getNotificationById(20L);
    }


    @Test
    void testGetAllBikes_NotificationServiceUnavailable() {
        List<Bike> mockBikes = Arrays.asList(testBike1);
        when(bikeRepository.findAll()).thenReturn(mockBikes);
        when(stationClient.getStationById(10L)).thenReturn(testStationDTO);
        // Simulamos que el servicio de notificaciones falla
        when(notificationClient.getNotificationById(20L)).thenThrow(mock(FeignException.class));


        List<BikeDTO> result = bikeService.getAllBikes();


        assertNotNull(result);
        assertEquals(1, result.size());

        BikeDTO bikeDTO = result.get(0);
        assertEquals(1L, bikeDTO.getId());
    }


    @Test
    void testGetBikeByIdWithStationAndNotification() {

        when(bikeRepository.findById(1L)).thenReturn(Optional.of(testBike1));
        when(stationClient.getStationById(10L)).thenReturn(testStationDTO);
        when(notificationClient.getNotificationById(20L)).thenReturn(testNotificationsDTO);


        BikeDTO result = bikeService.getBikeById(1L);


        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Mountain Bike", result.getModel());
        assertEquals("AVAILABLE", result.getStatus());

        verify(bikeRepository, times(1)).findById(1L);
        verify(stationClient, times(1)).getStationById(10L);
        verify(notificationClient, times(1)).getNotificationById(20L);
    }


}
