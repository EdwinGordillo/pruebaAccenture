package com.accenture.prueba.franquicias.Service.Impl;

import org.springframework.stereotype.Service;

import com.accenture.prueba.franquicias.Entity.Franquicia;
import com.accenture.prueba.franquicias.Entity.Sucursal;
import com.accenture.prueba.franquicias.Repository.FranquiciaRepository;
import com.accenture.prueba.franquicias.Repository.SucursalRepository;
import com.accenture.prueba.franquicias.Response.SucursalResponse;
import com.accenture.prueba.franquicias.Service.SucursalService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SucursalServiceImpl implements SucursalService {

    private final SucursalRepository sucursalRepo;
    private final FranquiciaRepository franquiciaRepo;

    @Override
    public SucursalResponse agregarSucursal(String nombre, Long franquiciaId) {
        Franquicia franquicia = franquiciaRepo.findById(franquiciaId)
            .orElseThrow(() -> new RuntimeException("Franquicia no encontrada"));

        Sucursal sucursal = Sucursal.builder().nombre(nombre).franquicia(franquicia).build();
        sucursal = sucursalRepo.save(sucursal);
        return new SucursalResponse(sucursal.getId(), sucursal.getNombre(), franquicia.getId());
    }
    
    public SucursalResponse seleccionarSucursal(Long id) {
        Sucursal sucursal = sucursalRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));

        return new SucursalResponse(
            sucursal.getId(),
            sucursal.getNombre(),
            sucursal.getFranquicia().getId()
        );
    }
    
    @Override
    public SucursalResponse modificarIdSucursal(Long sucursalId, String nombre) {
        Sucursal sucursal = sucursalRepo.findById(sucursalId)
            .orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));

        sucursal.setNombre(nombre);
        sucursal = sucursalRepo.save(sucursal);
        return new SucursalResponse(sucursal.getId(), sucursal.getNombre(), sucursal.getFranquicia().getId());
    }
}
