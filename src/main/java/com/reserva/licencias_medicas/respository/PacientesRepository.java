package com.reserva.licencias_medicas.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reserva.licencias_medicas.model.PacientesModel;

/**
 * Interfaz que permite interactuar con la tabla pacientes en la base de datos.
 */
public interface PacientesRepository extends JpaRepository<PacientesModel, Long> {
    
}
