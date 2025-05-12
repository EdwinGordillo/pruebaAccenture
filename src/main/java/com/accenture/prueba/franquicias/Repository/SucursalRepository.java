package com.accenture.prueba.franquicias.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.accenture.prueba.franquicias.Entity.Sucursal;

public interface SucursalRepository extends JpaRepository<Sucursal, Long> {
	
    List<Sucursal> findByFranquiciaId(Long franquiciaId);
	
}

