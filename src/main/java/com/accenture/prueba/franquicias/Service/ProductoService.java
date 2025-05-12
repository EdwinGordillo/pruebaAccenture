package com.accenture.prueba.franquicias.Service;

import java.util.List;

import com.accenture.prueba.franquicias.Response.ProductoResponse;

public interface ProductoService {
    ProductoResponse agregarProducto(String nombre, int stock, Long sucursalId);

    void eliminarProducto(Long productoId);

    ProductoResponse modificarStock(Long productoId, int nuevoStock);
    
    List<ProductoResponse> productoMayorStockPorFranquicia(Long franquiciaId);
}
