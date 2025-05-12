package com.accenture.prueba.franquicias.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.accenture.prueba.franquicias.Request.ActualizarSucursalRequest;
import com.accenture.prueba.franquicias.Request.SucursalRequest;
import com.accenture.prueba.franquicias.Response.SucursalResponse;
import com.accenture.prueba.franquicias.Service.SucursalService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/sucursales")
@RequiredArgsConstructor
public class SucursalController {

    private final SucursalService sucursalService;

    @PostMapping
    public ResponseEntity<SucursalResponse> crear(@RequestBody SucursalRequest request) {
        return ResponseEntity.ok(
            sucursalService.agregarSucursal(request.getNombre(), request.getFranquiciaId())
        );
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<SucursalResponse> obtenerMayorStock(@PathVariable Long id) {
        return ResponseEntity.ok(sucursalService.seleccionarSucursal(id));
    }
    
    @PutMapping("/{id}/sucursal")
    public ResponseEntity<SucursalResponse> modificarSucursal(@PathVariable Long id, @RequestBody ActualizarSucursalRequest request) {
        return ResponseEntity.ok(sucursalService.modificarIdSucursal(id, request.getNombre()));
    }
}