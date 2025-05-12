package com.accenture.prueba.franquicias;

import com.accenture.prueba.franquicias.Request.FranquiciaRequest;
import com.accenture.prueba.franquicias.Request.ProductoRequest;
import com.accenture.prueba.franquicias.Request.SucursalRequest;
import com.accenture.prueba.franquicias.Request.ActualizarStockRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Long crearFranquiciaYSucursal() throws Exception {
        // Crear franquicia
        FranquiciaRequest franquicia = new FranquiciaRequest();
        franquicia.setNombre("Franquicia PRODUCTO TEST");

        String franquiciaJson = mockMvc.perform(post("/api/franquicias")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(franquicia)))
                .andReturn().getResponse().getContentAsString();

        Long franquiciaId = objectMapper.readTree(franquiciaJson).get("id").asLong();

        // Crear sucursal
        SucursalRequest sucursal = new SucursalRequest();
        sucursal.setNombre("Sucursal PRODUCTO TEST");
        sucursal.setFranquiciaId(franquiciaId);

        String sucursalJson = mockMvc.perform(post("/api/sucursales")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sucursal)))
                .andReturn().getResponse().getContentAsString();

        return objectMapper.readTree(sucursalJson).get("id").asLong();
    }
    
    @Test
    void testCrearProducto() throws Exception {
        Long sucursalId = crearFranquiciaYSucursal();

        ProductoRequest productoRequest = new ProductoRequest();
        productoRequest.setNombre("Producto SIN MODIFICAR");
        productoRequest.setCantidad(30);
        productoRequest.setSucursalId(sucursalId);

        mockMvc.perform(post("/api/productos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productoRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nombre").value("Producto SIN MODIFICAR"))
                .andExpect(jsonPath("$.stock").value(30))
                .andExpect(jsonPath("$.sucursalId").value(sucursalId));
    }

    @Test
    void testModificarStock() throws Exception {
        Long sucursalId = crearFranquiciaYSucursal();

        // Crear producto
        ProductoRequest productoRequest = new ProductoRequest();
        productoRequest.setNombre("Producto MODIFICADO");
        productoRequest.setCantidad(20);
        productoRequest.setSucursalId(sucursalId);

        String response = mockMvc.perform(post("/api/productos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productoRequest)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Long productoId = objectMapper.readTree(response).get("id").asLong();

        // Modificar stock
        ActualizarStockRequest actualizarStock = new ActualizarStockRequest();
        actualizarStock.setCantidad(45);

        mockMvc.perform(put("/api/productos/" + productoId + "/stock")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(actualizarStock)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.stock").value(45));
    }

    @Test
    void testEliminarProducto() throws Exception {
        Long sucursalId = crearFranquiciaYSucursal();

        ProductoRequest productoRequest = new ProductoRequest();
        productoRequest.setNombre("Producto ELIMINACIÓN");
        productoRequest.setCantidad(15);
        productoRequest.setSucursalId(sucursalId);

        String response = mockMvc.perform(post("/api/productos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productoRequest)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Long productoId = objectMapper.readTree(response).get("id").asLong();

        mockMvc.perform(delete("/api/productos/" + productoId))
                .andExpect(status().isNoContent());
    }

    @Test
    void testProductoMayorStockPorFranquicia() throws Exception {
        Long sucursalId = crearFranquiciaYSucursal();

        // Producto 1
        ProductoRequest p1 = new ProductoRequest();
        p1.setNombre("Producto Bajo");
        p1.setCantidad(10);
        p1.setSucursalId(sucursalId);

        mockMvc.perform(post("/api/productos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(p1)))
                .andExpect(status().isOk());

        // Producto 2
        ProductoRequest p2 = new ProductoRequest();
        p2.setNombre("Producto Alto");
        p2.setCantidad(99);
        p2.setSucursalId(sucursalId);

        mockMvc.perform(post("/api/productos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(p2)))
                .andExpect(status().isOk());

        // Obtener franquiciaId desde la sucursal
        String sucursalJson = mockMvc.perform(get("/api/sucursales/" + sucursalId))
                .andReturn().getResponse().getContentAsString();
        Long franquiciaId = objectMapper.readTree(sucursalJson).get("franquiciaId").asLong();

        // Verificar que el producto con más stock sea "Producto Alto"
        mockMvc.perform(get("/api/productos/mayor-stock/" + franquiciaId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Producto Alto"))
                .andExpect(jsonPath("$[0].stock").value(99));
    }
}