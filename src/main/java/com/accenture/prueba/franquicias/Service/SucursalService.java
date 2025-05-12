package com.accenture.prueba.franquicias.Service;

import com.accenture.prueba.franquicias.Response.SucursalResponse;

public interface SucursalService {
    SucursalResponse agregarSucursal(String nombre, Long franquiciaId);
    
    SucursalResponse seleccionarSucursal(Long id);
    
    SucursalResponse modificarIdSucursal(Long sucursalId, String nombre);
}