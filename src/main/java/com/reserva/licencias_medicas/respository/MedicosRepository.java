package com.reserva.licencias_medicas.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reserva.licencias_medicas.model.MedicosModel;

/**
 * Interfaz que permite interactuar con la tabla medicos en la base de datos.
 */
public interface MedicosRepository extends JpaRepository<MedicosModel, Long> {
    
}
