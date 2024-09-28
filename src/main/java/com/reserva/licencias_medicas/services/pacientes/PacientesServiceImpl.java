package com.reserva.licencias_medicas.services.pacientes;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reserva.licencias_medicas.model.PacientesModel;
import com.reserva.licencias_medicas.respository.PacientesRepository;

/**
 * PacientesServiceImpl
 * @see PacientesService
 */
@Service
public class PacientesServiceImpl implements PacientesService {
    
    @Autowired
    private PacientesRepository pacientesRepository;

    @Override
    public List<PacientesModel> getPacientes() {
        return pacientesRepository.findAll();
    }

    /**
     * Buscar paciente por id
     * @param id
     */
    @Override
    public Optional<PacientesModel> getPacienteById(Long id) {
        return pacientesRepository.findById(id);
    }

    /**
     * Crear un paciente
     * @param paciente
     */
    @Override
    public PacientesModel createPaciente(PacientesModel paciente) {
        return pacientesRepository.save(paciente);
    }

    /**
     * Actualizar un paciente
     * @param id
     * @param paciente
     */
    @Override
    public PacientesModel updatePaciente(Long id, PacientesModel paciente) {
            
            Optional<PacientesModel> pacienteOptional = pacientesRepository.findById(id);
    
            if (!pacienteOptional.isPresent()) {
                throw new RuntimeException("Paciente no encontrado");
            }
    
            paciente.setId(id);
            return pacientesRepository.save(paciente);
    }

    /**
     * Eliminar un paciente
     * @param id
     */
    @Override
    public void deletePaciente(Long id) {
        pacientesRepository.deleteById(id);
    }
}
