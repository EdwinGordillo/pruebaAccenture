package com.accenture.prueba.franquicias.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.accenture.prueba.franquicias.Request.ActualizarFranquiciaRequest;
import com.accenture.prueba.franquicias.Request.FranquiciaRequest;
import com.accenture.prueba.franquicias.Response.FranquiciaResponse;
import com.accenture.prueba.franquicias.Service.FranquiciaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/franquicias")
@RequiredArgsConstructor
public class FranquiciaController {

    private final FranquiciaService franquiciaService;

    @PostMapping
    public ResponseEntity<FranquiciaResponse> crear(@RequestBody FranquiciaRequest request) {
        return ResponseEntity.ok(franquiciaService.crearFranquicia(request.getNombre()));
    }
    
    @PutMapping("/{id}/franquicia")
    public ResponseEntity<FranquiciaResponse> modificarFranquicia(@PathVariable Long id, @RequestBody ActualizarFranquiciaRequest request) {
        return ResponseEntity.ok(franquiciaService.modificarIdFranquicia(id, request.getNombre()));
    }
    
}