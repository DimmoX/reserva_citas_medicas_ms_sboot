package com.reserva.licencias_medicas.respository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reserva.licencias_medicas.model.HorariosModel;
import com.reserva.licencias_medicas.model.MedicosModel;

/**
 * Interfaz que permite interactuar con la tabla horarios en la base de datos.
 */
public interface HorariosRepository extends JpaRepository<HorariosModel, Long> {
    
    //** Buscar por fecha disponible y medico
    Optional<HorariosModel> findByFechaDisponibleAndIdMedico(LocalDate fechaDisponible, MedicosModel idMedico);
}
