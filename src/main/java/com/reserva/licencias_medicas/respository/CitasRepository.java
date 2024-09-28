package com.reserva.licencias_medicas.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reserva.licencias_medicas.model.CitasModel;

/**
 * Interfaz que permite interactuar con la tabla citas en la base de datos.
 */
public interface CitasRepository extends JpaRepository<CitasModel, Long> {

}
