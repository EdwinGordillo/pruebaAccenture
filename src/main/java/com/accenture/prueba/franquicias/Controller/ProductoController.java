package com.accenture.prueba.franquicias.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.accenture.prueba.franquicias.Request.ActualizarStockRequest;
import com.accenture.prueba.franquicias.Request.ProductoRequest;
import com.accenture.prueba.franquicias.Response.ProductoResponse;
import com.accenture.prueba.franquicias.Service.ProductoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    @PostMapping
    public ResponseEntity<ProductoResponse> crear(@RequestBody ProductoRequest request) {
        return ResponseEntity.ok(
            productoService.agregarProducto(request.getNombre(), request.getCantidad(), request.getSucursalId())
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        productoService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/stock")
    public ResponseEntity<ProductoResponse> modificarStock(@PathVariable Long id, @RequestBody ActualizarStockRequest request) {
        return ResponseEntity.ok(productoService.modificarStock(id, request.getCantidad()));
    }

    @GetMapping("/mayor-stock/{franquiciaId}")
    public ResponseEntity<List<ProductoResponse>> obtenerMayorStock(@PathVariable Long franquiciaId) {
        return ResponseEntity.ok(productoService.productoMayorStockPorFranquicia(franquiciaId));
    }
}