package com.reserva.licencias_medicas.services.medicos;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reserva.licencias_medicas.model.MedicosModel;
import com.reserva.licencias_medicas.respository.MedicosRepository;

/**
 * MedicosServiceImpl
 * @see MedicosService
 */
@Service
public class MedicosServiceImpl implements MedicosService {

    @Autowired
    private MedicosRepository medicosRepository;

    @Override
    public List<MedicosModel> getMedicos() {
        return medicosRepository.findAll();
    }

    /**
     * Buscar médico por id
     * @param id
     */
    @Override
    public Optional<MedicosModel> getMedicoById(Long id) {
        return medicosRepository.findById(id);
    }

    /**
     * Crear un médico
     * @param medico
     */
    @Override
    public MedicosModel createMedico(MedicosModel medico) {
        return medicosRepository.save(medico);
    }

    /**
     * Actualizar un médico
     * @param id
     * @param medico
     */
    @Override
    public MedicosModel updateMedico(Long id, MedicosModel medico) {

        Optional<MedicosModel> medicoOptional = medicosRepository.findById(id);

        if (!medicoOptional.isPresent()) {
            throw new RuntimeException("Médico no encontrado");
        }

        medico.setId(id);
        return medicosRepository.save(medico);
    }

    /**
     * Eliminar un médico
     * @param id
     */
    @Override
    public void deleteMedico(Long id) {
        medicosRepository.deleteById(id);
    }
    
}
