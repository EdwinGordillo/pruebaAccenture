package com.accenture.prueba.franquicias.Service;

import com.accenture.prueba.franquicias.Response.FranquiciaResponse;

public interface FranquiciaService {
	
    FranquiciaResponse crearFranquicia(String nombre);
    
    FranquiciaResponse modificarIdFranquicia(Long franquiciaId, String nombre);
    
}
