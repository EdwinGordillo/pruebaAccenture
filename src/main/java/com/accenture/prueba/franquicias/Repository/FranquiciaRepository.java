package com.accenture.prueba.franquicias.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.accenture.prueba.franquicias.Entity.Franquicia;

public interface FranquiciaRepository extends JpaRepository<Franquicia, Long> {}