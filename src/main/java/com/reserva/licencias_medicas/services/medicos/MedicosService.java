package com.reserva.licencias_medicas.services.medicos;

import java.util.List;
import java.util.Optional;

import com.reserva.licencias_medicas.model.MedicosModel;

public interface MedicosService {

    //** Metodos
    List<MedicosModel> getMedicos();
    Optional<MedicosModel> getMedicoById(Long id);
    MedicosModel createMedico(MedicosModel medico);
    MedicosModel updateMedico(Long id, MedicosModel medico);
    void deleteMedico(Long id);
}
