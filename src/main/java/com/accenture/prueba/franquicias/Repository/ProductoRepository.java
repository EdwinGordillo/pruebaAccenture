package com.accenture.prueba.franquicias.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.accenture.prueba.franquicias.Entity.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
	
    List<Producto> findBySucursalFranquiciaId(Long franquiciaId);
    
}