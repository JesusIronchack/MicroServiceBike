package com.example.MicroServiceBike.Controller;

import com.example.MicroServiceBike.DTO.BikeBasicDTO;
import com.example.MicroServiceBike.DTO.BikeDTO;
import com.example.MicroServiceBike.Models.Bike;
import com.example.MicroServiceBike.Service.BikeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;


import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BikeController.class)
public class BikeControllerTest {
    @Autowired
    private MockMvc mockMvc; // Simula las peticiones HTTP

    @MockBean
    private BikeService bikeService; // Simula el servicio (no usa la implementación real)

    @Autowired
    private ObjectMapper objectMapper; // Convierte objetos Java a JSON y viceversa

    private Bike testBike;
    private BikeDTO testBikeDTO;
    private BikeBasicDTO testBikeBasicDTO;

    /**
     * Se ejecuta antes de cada test para preparar los datos de prueba
     */
    @BeforeEach
    void setUp() {
        // Creamos objetos de prueba que usaremos en los tests
        testBike = new Bike();
        testBike.setId(1L);
        testBike.setModel("Mountain Bike");
        testBike.setStatus("AVAILABLE");

        testBikeDTO = new BikeDTO();
        testBikeDTO.setId(1L);
        testBikeDTO.setModel("Mountain Bike");
        testBikeDTO.setStatus("AVAILABLE");

        testBikeBasicDTO = new BikeBasicDTO();
        testBikeBasicDTO.setId(1L);
        testBikeBasicDTO.setModel("Mountain Bike");
    }

    /**
     * TEST 1: Verificar que se puede crear una bicicleta correctamente
     */
    @Test
    void testCreateBike() throws Exception {
        // ARRANGE (Preparar): Le decimos al mock qué debe devolver
        when(bikeService.createBike(any(Bike.class))).thenReturn(testBikeBasicDTO);

        // ACT & ASSERT (Actuar y Verificar): Hacemos la petición y verificamos la respuesta
        mockMvc.perform(post("/api/bikes")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(testBike)))
                .andExpect(status().isCreated()) // Esperamos código 201
                .andExpect(jsonPath("$.id").value(1L)) // Verificamos que el ID sea 1
                .andExpect(jsonPath("$.model").value("Mountain Bike")); // Verificamos el modelo
    }

}
