package com.reserva.licencias_medicas.services.horarios;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.reserva.licencias_medicas.dto.HorariosDTO;
import com.reserva.licencias_medicas.model.HorariosModel;

/**
 * HorariosService
 */
public interface HorariosService {
    
    //** Metodos
    List<HorariosModel> getHorarios();
    Optional<HorariosModel> getHorarioById(Long id);
    HorariosModel createHorario(Map<String, Object> horarioData);
    HorariosModel updateHorario(Long id, HorariosDTO horario);
    void deleteHorario(Long id);
    
}
