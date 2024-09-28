package com.reserva.licencias_medicas.services.horarios;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reserva.licencias_medicas.dto.HorariosDTO;
import com.reserva.licencias_medicas.model.HorariosModel;
import com.reserva.licencias_medicas.model.MedicosModel;
import com.reserva.licencias_medicas.respository.HorariosRepository;
import com.reserva.licencias_medicas.respository.MedicosRepository;

/**
 * HorariosServiceImpl
 * @see HorariosService
 */
@Service
public class HorariosServiceImpl implements HorariosService {
    
    @Autowired
    private HorariosRepository horariosRepository;

    @Autowired
    private MedicosRepository medicosRepository;

    @Override
    public List<HorariosModel> getHorarios() {
        return horariosRepository.findAll();
    }

    /**
     * Buscar horario por id
     * @param id
     */
    @Override
    public Optional<HorariosModel> getHorarioById(Long id) {
        return horariosRepository.findById(id);
    }
    
    /**
     * Crear un horario
     * @param horarioData
     * @see MedicosModel
     */
    @Override
    public HorariosModel createHorario(Map<String, Object> horarioData) {
        // Formatear la fecha
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String fechaStr = (String) horarioData.get("fecha");
        LocalDate fecha = LocalDate.parse(fechaStr, formatter);

        // Obtener la lista de horas disponibles
        List<String> horasDisponibles = (List<String>) horarioData.get("horasDisponibles");

        // Obtener el id del médico
        Long idMedico = Long.valueOf(horarioData.get("idMedico").toString());

        // Buscar el médico en la base de datos
        MedicosModel medico = medicosRepository.findById(idMedico)
            .orElseThrow(() -> new RuntimeException("Médico no encontrado"));

        // Crear el objeto HorariosModel
        HorariosModel horario = new HorariosModel();
        horario.setFechaDisponible(fecha);
        horario.setHorasDisponibles(horasDisponibles);
        horario.setIdMedico(medico);  // Asignar el objeto MedicosModel

        // Guardar en la base de datos
        return horariosRepository.save(horario);
    }

    /**
     * Actualizar un horario
     * @param id
     * @param horario
     */
    @Override
    public HorariosModel updateHorario(Long id, HorariosDTO horarioDTO) {
        // Buscar el horario existente
        Optional<HorariosModel> horarioExistente = horariosRepository.findById(id);
        if (!horarioExistente.isPresent()) {
            throw new RuntimeException("Horario no encontrado");
        }

        // Obtener el horario actual
        HorariosModel horario = horarioExistente.get();

        // Actualizar la fecha y horas disponibles
        horario.setFechaDisponible(LocalDate.parse(horarioDTO.getFecha(), DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        horario.setHorasDisponibles(horarioDTO.getHorasDisponibles());

        // Buscar y establecer el médico
        MedicosModel medico = medicosRepository.findById(horarioDTO.getIdMedico())
                .orElseThrow(() -> new RuntimeException("Médico no encontrado"));
        horario.setIdMedico(medico);

        // Guardar los cambios
        return horariosRepository.save(horario);
    }

    /**
     * Eliminar un horario
     * @param id
     */
    @Override
    public void deleteHorario(Long id) {
        horariosRepository.deleteById(id);
    }
}
