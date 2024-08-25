package com.reserva.licencias_medicas.controller;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.reserva.licencias_medicas.services.CitasServices;
import com.reserva.licencias_medicas.services.HorariosServices;
import com.reserva.licencias_medicas.services.MedicosServices;
import com.reserva.licencias_medicas.services.PacientesServices;

/**
 * @description Clase para gestionar las licencias médicas
 */
@RestController
public class LicenciasMedicasController {

    // Atributos de la clase
    private List<PacientesServices> paciente = new ArrayList<>();
    private List<MedicosServices> medicos = new ArrayList<>();
    private List <HorariosServices> horarios = new ArrayList<>();
    private List<Map<String, Map<String, List<Map<String, String>>>>> horas1 = new ArrayList<>();
    private List<Map<String, Map<String, List<Map<String, String>>>>> horas2 = new ArrayList<>();
    private List<Map<String, Map<String, List<Map<String, String>>>>> horas3 = new ArrayList<>();
    private List<Map<String, Map<String, List<Map<String, String>>>>> horas4 = new ArrayList<>();
    private List<CitasServices> citas = new ArrayList<>();

    /**
     * Constructor de la clase
     */
    public LicenciasMedicasController() {

        // Lista de Citas
        citas.addAll(Arrays.asList(
            new CitasServices(1, "10-09-2024", "11:00", 1, 4),
            new CitasServices(4, "12-09-2024", "15:00", 2, 3),
            new CitasServices(3, "01-09-2024", "16:00", 3, 1),
            new CitasServices(2, "29-09-2024", "16:00", 4, 2)
        ));

        // Lista de Pacientes
        paciente.addAll(Arrays.asList(
            new PacientesServices(1, "Diego", "Morales", "11111111-1", "950768456"),
            new PacientesServices(2, "Camila", "Rojas", "22222222-2", "967427893"),
            new PacientesServices(3, "Esteban", "Vargas", "33333333-3", "975678395"),
            new PacientesServices(4, "Isabel", "Castillo", "44444444-4", "967531749")
        ));

        // Lista de Médicos
        medicos.addAll(Arrays.asList(
            new MedicosServices(1, "Santiago", "Pérez", "Medicina General"),
            new MedicosServices(2, "Lucía", "Ramírez", "Pediatría"),
            new MedicosServices(3, "Javier", "Fernández", "Traumatología"),
            new MedicosServices(4, "Carolina", "Muñoz", "Cardiología")
        ));

        /**
         * Listas de Horarios
         */
        horas1.add(
            Map.of(
                "10-09-2024", Map.of(
                    "horas_disponibles", Arrays.asList(
                        Map.of("hora", "09:00"),
                        Map.of("hora", "10:00")
                    )
                ),
                "12-09-2024", Map.of(
                    "horas_disponibles", Arrays.asList(
                        Map.of("hora", "09:00"),
                        Map.of("hora", "12:30")
                    )
                )
            )
        );

        horas2.add(
            Map.of(
                "12-09-2024", Map.of(
                    "horas_disponibles", Arrays.asList(
                        Map.of("hora", "09:00"),
                        Map.of("hora", "12:00"),
                        Map.of("hora", "18:30")
                    )
                ),
                "13-09-2024", Map.of(
                    "horas_disponibles", Arrays.asList(
                        Map.of("hora", "09:30"),
                        Map.of("hora", "12:30"),
                        Map.of("hora", "16:00")
                    )
                )
            )
        );

        horas3.add(
            Map.of(
                "01-09-2024", Map.of(
                    "horas_disponibles", Arrays.asList(
                        Map.of("hora", "10:00"),
                        Map.of("hora", "12:00"),
                        Map.of("hora", "17:00") 
                    )
                ),
                "03-09-2024", Map.of(
                    "horas_disponibles", Arrays.asList(
                        Map.of("hora", "13:300"),
                        Map.of("hora", "17:300")
                    )
                )
            )
        );

        horas4.add(
            Map.of(
                "29-09-2024", Map.of(
                    "horas_disponibles", Arrays.asList(
                        Map.of("hora", "12:00"),
                        Map.of("hora", "18:00")
                    )
                ),
                "30-09-2024", Map.of(
                    "horas_disponibles", Arrays.asList(
                        Map.of("hora", "09:00")
                    )
                )
            )
        );

        // Lista de Horarios
        horarios.addAll(Arrays.asList(
            new HorariosServices(1, horas1, 3),
            new HorariosServices(2, horas2, 1),
            new HorariosServices(3, horas3, 4),
            new HorariosServices(4, horas4, 2)
        ));
    }

    // Endpoints

    /**
     * Método para obtener la información de un médico
     * @param id
     * @return {Map<String, Object>} respuesta de error o médico
     */
    @GetMapping(path = "/medicos/{id}")
    public Map<String, Object> getMedico(@PathVariable int id) {

        for(HorariosServices horario : horarios) {
            for(MedicosServices medicos : medicos) {
                if(horario.getIdMedico() == id && horario.getIdMedico() == medicos.getIdMedico()) {

                    return Map.of(
                        "idMedico", horario.getIdMedico(),
                        "nombre", medicos.getNombreMedico() + " " + medicos.getApellidoMedico(),
                        "especialidad", medicos.getEspecialidad(),
                        "fechas", horario.getFechas(),
                        "IdHorario", horario.getIdHorario()
                    );
                }
            }
        }
        return Map.of("Error", "no se encontró horario para el médico solicitado");
    }

    /**
     * Método para obtener los horarios
     * @return {List<HorariosServices>} horarios
     */
    @GetMapping(path = "/horarios")
    public List<HorariosServices> getHorarios() {
        return horarios;
    }

    /**
     * Método para obtener la lista de médicos
     * @return {List<MedicosServices>} medicos
     */
    @GetMapping(path = "/medicos")
    public List<MedicosServices> getMedicos() {
        return medicos;
    }

    /**
     * Método para obtener la información de una cita
     * @param id
     * @return {Map<String, Object>} respuesta de error o cita
     */
    @GetMapping(path = "/citas/{id}")
    public Map<String, Object> getReserva(@PathVariable int id) {
        
        for (CitasServices citas : citas) {
            for(MedicosServices medicos : medicos){
                for(PacientesServices paciente : paciente){
                    if (citas.getIdReserva() == id && citas.getIdMedico() == medicos.getIdMedico() && citas.getIdPaciente() == paciente.getId()) {
                        return Map.of(
                            "idCitas", citas.getIdReserva(),
                            "fecha", citas.getFechaReserva(),
                            "hora", citas.getHoraReserva(),
                            "idMedico", citas.getIdMedico(),
                            "idPaciente", citas.getIdPaciente(),
                            "nombreMedico", medicos.getNombreMedico() + " " + medicos.getApellidoMedico(),
                            "especialidad", medicos.getEspecialidad(),
                            "nombrePaciente", paciente.getNombre() + " " + paciente.getApellido()
                        );
                    }
                }
            }
        }
        return Map.of("error", "Reserva no encontrada");   
    }

    /**
     * Método para obtener la lista de citas
     * @return {List<Map<String, Object>>} citasList
     */
    @GetMapping(path = "/citas")
    public List<Map<String, Object>> getCitas() {
        List<Map<String, Object>> citasList = new ArrayList<>();
        for(CitasServices cita : citas){
            for(MedicosServices medico : medicos){
                for(PacientesServices paciente : paciente){
                    if(cita.getIdMedico() == medico.getIdMedico() && cita.getIdPaciente() == paciente.getId()){
                        citasList.add(Map.of(
                            "idMedico", cita.getIdMedico(),
                            "nombreMedico", medico.getNombreMedico() + " " + medico.getApellidoMedico(),
                            "especialidad", medico.getEspecialidad(),
                            "idPaciente", cita.getIdPaciente(),
                            "nombrePaciente", paciente.getNombre() + " " + paciente.getApellido(),
                            "fecha", cita.getFechaReserva(),
                            "hora", cita.getHoraReserva()
                        ));
                    }

                }
            }
        }
        return citasList == null ? List.of(Map.of("message", "No hay citas disponibles")) : citasList;
    }
}
