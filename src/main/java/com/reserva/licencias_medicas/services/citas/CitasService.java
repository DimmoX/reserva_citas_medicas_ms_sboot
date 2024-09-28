package com.reserva.licencias_medicas.services.citas;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.reserva.licencias_medicas.dto.CitasDTO;
import com.reserva.licencias_medicas.model.CitasModel;

/**
 * CitasService
 */
public interface CitasService {
    //** Métodos
    List<CitasModel> getCitas();
    Optional<CitasModel> getCitaById(Long id);
    CitasModel createCita(LocalDate fechaCita, String horaCita, Long idMedico, Long idPaciente);
    CitasDTO updateCita(Long id, CitasDTO citaDTO);
    void deleteCita(Long id);
}
