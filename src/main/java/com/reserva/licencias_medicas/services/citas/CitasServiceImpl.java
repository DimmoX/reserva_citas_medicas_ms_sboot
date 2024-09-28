package com.reserva.licencias_medicas.services.citas;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reserva.licencias_medicas.dto.CitasDTO;
import com.reserva.licencias_medicas.model.CitasModel;
import com.reserva.licencias_medicas.model.HorariosModel;
import com.reserva.licencias_medicas.model.MedicosModel;
import com.reserva.licencias_medicas.model.PacientesModel;
import com.reserva.licencias_medicas.respository.CitasRepository;
import com.reserva.licencias_medicas.respository.HorariosRepository;
import com.reserva.licencias_medicas.respository.MedicosRepository;
import com.reserva.licencias_medicas.respository.PacientesRepository;

/**
 * Implementación de la interfaz CitasService
 * @see CitasService
 */
@Service
public class CitasServiceImpl implements CitasService {

    @Autowired
    private CitasRepository citasRepository;

    @Autowired
    private HorariosRepository horariosRepository;

    @Autowired
    private MedicosRepository medicosRepository;

    @Autowired
    private PacientesRepository pacientesRepository;

    // Método para obtener todas las citas
    public List<CitasModel> getCitas() {
        return citasRepository.findAll();
    }

    // Método para obtener una cita por su id
    @Override
    public Optional<CitasModel> getCitaById(Long id) {
        return citasRepository.findById(id);
    }

    /**
     * Método para crear una nueva cita
     * @param fechaCita Fecha de la cita
     * @param horaCita Hora de la cita
     * @param idMedico Id del médico
     * @param idPaciente Id del paciente
     * @throws RuntimeException Si no se encuentra el médico, el paciente o un horario disponible para la fecha y el médico especificados, o si la hora no está disponible
     * @see CitasModel
     * @see MedicosModel
     * @see PacientesModel
     * @see HorariosModel
     */
    @Override
    public CitasModel createCita(LocalDate fechaCita, String horaCita, Long idMedico, Long idPaciente) {
        // Buscar el médico y el paciente
        MedicosModel idxMedico = medicosRepository.findById(idMedico)
                .orElseThrow(() -> new RuntimeException("Médico no encontrado"));
        PacientesModel idxPaciente = pacientesRepository.findById(idPaciente)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));

        // Buscar el horario del médico para la fecha especificada
        HorariosModel horario = horariosRepository.findByFechaDisponibleAndIdMedico(fechaCita, idxMedico)
                .orElseThrow(() -> new RuntimeException(
                        "No se encontró horario disponible para la fecha y médico especificados"));

        // Verificar si la hora está disponible
        if (!horario.getHorasDisponibles().contains(horaCita)) {
            throw new RuntimeException("La hora no está disponible");
        }

        // Eliminar la hora de las horas disponibles
        horario.getHorasDisponibles().remove(horaCita);

        // Guardar los cambios en el horario
        horariosRepository.save(horario);

        // Crear una nueva cita
        CitasModel nuevaCita = new CitasModel();
        nuevaCita.setFechaCita(fechaCita);
        nuevaCita.setHoraCita(horaCita);
        nuevaCita.setIdMedico(idxMedico);
        nuevaCita.setIdPaciente(idxPaciente);

        return citasRepository.save(nuevaCita);
    }

    /**
     * Método para actualizar una cita
     * @param id Id de la cita
     * @param citaDTO Datos de la cita a actualizar
     * @throws RuntimeException Si no se encuentra la cita, el médico, el paciente o un horario disponible para la fecha y el médico especificados, o si la nueva hora no está disponible
     * @see CitasModel
     * @see CitasDTO
     * @see MedicosModel
     * @see PacientesModel
     */
    @Override
    public CitasDTO updateCita(Long id, CitasDTO citaDTO) {
        // Buscar la cita existente
        CitasModel citaExistente = citasRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));
        // Buscar el médico y el paciente
        MedicosModel medico = medicosRepository.findById(citaDTO.getIdMedico())
                .orElseThrow(() -> new RuntimeException("Médico no encontrado"));
        PacientesModel paciente = pacientesRepository.findById(citaDTO.getIdPaciente())
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
        // Se busca el horario del médico para la fecha específica
        HorariosModel horario = horariosRepository
                .findByFechaDisponibleAndIdMedico(citaExistente.getFechaCita(), medico)
                .orElseThrow(() -> new RuntimeException("Horario no encontrado para el médico en esa fecha"));

        // Se valida si la nueva hora está disponible en el horario
        if (!horario.getHorasDisponibles().contains(citaDTO.getHoraCita())) {
            throw new RuntimeException("La nueva hora no está disponible en el horario del médico");
        }

        // Se agrega la hora anterior de la cita de vuelta al horario
        horario.getHorasDisponibles().add(citaExistente.getHoraCita());

        // Se elimina la nueva hora de la lista de horas disponibles
        horario.getHorasDisponibles().remove(citaDTO.getHoraCita());

        // Se guarda los cambios en el horario
        horariosRepository.save(horario);

        // Se actualiza los datos de la cita
        citaExistente.setFechaCita(citaDTO.getFechaCita());
        citaExistente.setHoraCita(citaDTO.getHoraCita());
        citaExistente.setIdMedico(medico);
        citaExistente.setIdPaciente(paciente);

        // Guardar la cita actualizada
        citasRepository.save(citaExistente);

        return convertirModelADto(citaExistente);
    }

    /**
     * Método para eliminar una cita
     * @param id Id de la cita
     * @throws RuntimeException Si no se encuentra la cita o el horario del médico en la fecha de la cita
     * @see CitasModel
     * @see HorariosModel
     * @see MedicosModel
     */
    @Override
    public void deleteCita(Long id) {
        // Buscar la cita por su id
        Optional<CitasModel> citaOptional = citasRepository.findById(id);
        if (!citaOptional.isPresent()) {
            throw new RuntimeException("Cita no encontrada");
        }

        CitasModel cita = citaOptional.get();

        // Obtener la fecha y la hora de la cita
        LocalDate fechaCita = cita.getFechaCita();
        String horaCita = cita.getHoraCita();

        // Obtener el id del médico
        MedicosModel medico = cita.getIdMedico();

        // Buscar el horario del médico para la fecha de la cita
        Optional<HorariosModel> horarioOptional = horariosRepository.findByFechaDisponibleAndIdMedico(fechaCita,
                medico);
        if (!horarioOptional.isPresent()) {
            throw new RuntimeException("Horario no encontrado para el médico en la fecha especificada");
        }

        HorariosModel horario = horarioOptional.get();

        // Agregar la hora a la lista de horas disponibles si no está ya presente
        if (!horario.getHorasDisponibles().contains(horaCita)) {
            horario.getHorasDisponibles().add(horaCita);
        }

        // Guardar el horario actualizado
        horariosRepository.save(horario);

        // Eliminar la cita
        citasRepository.deleteById(id);
    }

    /**
     * Método para convertir un objeto CitasModel a un objeto CitasDTO
     * @param cita
     * @return {dta CitasDTO} Objeto CitasDTO
     * @see CitasModel
     */
    private CitasDTO convertirModelADto(CitasModel cita) {
        CitasDTO dto = new CitasDTO();
        dto.setId(cita.getId());
        dto.setFechaCita(cita.getFechaCita());
        dto.setHoraCita(cita.getHoraCita());
        dto.setIdMedico(cita.getIdMedico().getId());
        dto.setIdPaciente(cita.getIdPaciente().getId());
        return dto;
    }
}