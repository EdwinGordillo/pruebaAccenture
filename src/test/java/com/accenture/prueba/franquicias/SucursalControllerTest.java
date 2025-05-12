package com.accenture.prueba.franquicias;

import com.accenture.prueba.franquicias.Request.FranquiciaRequest;
import com.accenture.prueba.franquicias.Request.SucursalRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class SucursalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void crearSucursal() throws Exception {
        // Crear franquicia primero
        FranquiciaRequest franquicia = new FranquiciaRequest();
        franquicia.setNombre("Accenture Cloud Services - SUCURSAL TEST");

        String response = mockMvc.perform(MockMvcRequestBuilders.post("/api/franquicias")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(franquicia)))
                .andReturn().getResponse().getContentAsString();

        Long franquiciaId = objectMapper.readTree(response).get("id").asLong();

        SucursalRequest request = new SucursalRequest();
        request.setNombre("Sucursal - SUCURSAL TEST");
        request.setFranquiciaId(franquiciaId);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/sucursales")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nombre").value("Sucursal - SUCURSAL TEST"));
    }
    
    //Falta integración con modificación de sucursal
}