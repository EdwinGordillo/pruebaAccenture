package com.accenture.prueba.franquicias.Service.Impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.accenture.prueba.franquicias.Entity.Producto;
import com.accenture.prueba.franquicias.Entity.Sucursal;
import com.accenture.prueba.franquicias.Repository.ProductoRepository;
import com.accenture.prueba.franquicias.Repository.SucursalRepository;
import com.accenture.prueba.franquicias.Response.ProductoResponse;
import com.accenture.prueba.franquicias.Service.ProductoService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepo;
    private final SucursalRepository sucursalRepo;

    @Override
    public ProductoResponse agregarProducto(String nombre, int cantidad, Long sucursalId) {
        Sucursal sucursal = sucursalRepo.findById(sucursalId)
            .orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));

        Producto producto = Producto.builder().nombre(nombre).cantidad(cantidad).sucursal(sucursal).build();
        producto = productoRepo.save(producto);
        return new ProductoResponse(producto.getId(), producto.getNombre(), producto.getCantidad(), sucursal.getId());
    }

    @Override
    public void eliminarProducto(Long productoId) {
        productoRepo.deleteById(productoId);
    }

    @Override
    public ProductoResponse modificarStock(Long productoId, int nuevaCantidad) {
        Producto producto = productoRepo.findById(productoId)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        producto.setCantidad(nuevaCantidad);
        producto = productoRepo.save(producto);
        return new ProductoResponse(producto.getId(), producto.getNombre(), producto.getCantidad(), producto.getSucursal().getId());
    }

    @Override
    public List<ProductoResponse> productoMayorStockPorFranquicia(Long franquiciaId) {
        List<Sucursal> sucursales = sucursalRepo.findByFranquiciaId(franquiciaId);

        List<ProductoResponse> productoResponses = new ArrayList<>();

        for (Sucursal sucursal : sucursales) {
            Producto productoMayorStock = sucursal.getProductos().stream()
                    .max(Comparator.comparingInt(Producto::getCantidad))
                    .orElse(null);

            if (productoMayorStock != null) {
                ProductoResponse response = new ProductoResponse(
                        productoMayorStock.getId(),
                        productoMayorStock.getNombre(),
                        productoMayorStock.getCantidad(),
                        sucursal.getId()
                );
                productoResponses.add(response);
            }
        }

        return productoResponses;
    }
}