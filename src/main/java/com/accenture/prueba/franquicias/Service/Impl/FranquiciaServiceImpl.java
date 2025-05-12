package com.accenture.prueba.franquicias.Service.Impl;

import org.springframework.stereotype.Service;

import com.accenture.prueba.franquicias.Entity.Franquicia;
import com.accenture.prueba.franquicias.Repository.FranquiciaRepository;
import com.accenture.prueba.franquicias.Response.FranquiciaResponse;
import com.accenture.prueba.franquicias.Service.FranquiciaService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FranquiciaServiceImpl implements FranquiciaService {

    private final FranquiciaRepository franquiciaRepo;

    @Override
    public FranquiciaResponse crearFranquicia(String nombre) {
        Franquicia franquicia = franquiciaRepo.save(Franquicia.builder().nombre(nombre).build());
        return new FranquiciaResponse(franquicia.getId(), franquicia.getNombre());
    }
    
    @Override
    public FranquiciaResponse modificarIdFranquicia(Long franquiciaId, String nombre) {
        Franquicia franquicia = franquiciaRepo.findById(franquiciaId)
            .orElseThrow(() -> new RuntimeException("Franquicia no encontrado"));

        franquicia.setNombre(nombre);
        franquicia = franquiciaRepo.save(franquicia);
        return new FranquiciaResponse(franquicia.getId(), franquicia.getNombre());
    }

}
