package com.reserva.licencias_medicas.services.pacientes;

import java.util.List;
import java.util.Optional;

import com.reserva.licencias_medicas.model.PacientesModel;

public interface PacientesService {
    
    //** Metodos
    List<PacientesModel> getPacientes();
    Optional<PacientesModel> getPacienteById(Long id);
    PacientesModel createPaciente(PacientesModel paciente);
    PacientesModel updatePaciente(Long id, PacientesModel paciente);
    void deletePaciente(Long id);
}
