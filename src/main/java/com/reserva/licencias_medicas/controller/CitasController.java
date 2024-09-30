package com.reserva.licencias_medicas.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reserva.licencias_medicas.dto.CitasDTO;
import com.reserva.licencias_medicas.model.CitasModel;
import com.reserva.licencias_medicas.model.MedicosModel;
import com.reserva.licencias_medicas.model.PacientesModel;
import com.reserva.licencias_medicas.services.citas.CitasService;

/**
 * Clase que representa el controlador de las citas médicas
 */
@RestController
@RequestMapping("/citas")
public class CitasController {
    private static final Logger logger = LoggerFactory.getLogger(CitasController.class);

    @Autowired
    private CitasService citasService;

    /**
     * Obtiene todas las citas
     */
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<CitasModel>>> getCitas() {
        logger.info("GET: /citas -> Se obtienen todas las citas");
        List<EntityModel<CitasModel>> citas = citasService.getCitas().stream()
            .map(cita -> {
                MedicosModel medico = cita.getIdMedico();
                PacientesModel paciente = cita.getIdPaciente();

                return EntityModel.of(cita,
                        linkTo(methodOn(CitasController.class).getCitabyId(cita.getId())).withRel("cita"),
                        linkTo(methodOn(CitasController.class).getCitas()).withRel("citas"),
                        linkTo(methodOn(MedicosController.class).getMedicobyId(medico.getId())).withRel("medico"),
                        linkTo(methodOn(MedicosController.class).getMedicos()).withRel("medicos"),
                        linkTo(methodOn(PacientesController.class).getPacientebyId(paciente.getId())).withRel("paciente"),
                        linkTo(methodOn(PacientesController.class).getPacientes()).withRel("pacientes")
                );
            })
            .collect(Collectors.toList());

        return new ResponseEntity<>(CollectionModel.of(
                citas,
                linkTo(methodOn(CitasController.class).getCitas()).withSelfRel()), HttpStatus.OK);
    }

    /**
     * Obtiene una cita por su id
     * @param id
     */
    @GetMapping(path = "/{id}")
    public ResponseEntity<EntityModel<CitasModel>> getCitabyId(@PathVariable Long id) {
        logger.info("GET: /citas/{} -> Obtener cita", id);
        Optional<CitasModel> cita = citasService.getCitaById(id);
        if (!cita.isPresent()) {
            logger.error("GET: /citas/{} -> cita no encontrada", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        EntityModel<CitasModel> citas = EntityModel.of(cita.get());
        citas.add(linkTo(methodOn(CitasController.class).getCitabyId(id)).withSelfRel());
        citas.add(linkTo(methodOn(CitasController.class).getCitas()).withRel("citas"));
        citas.add(linkTo(methodOn(MedicosController.class).getMedicobyId(cita.get().getIdMedico().getId())).withRel("medico"));
        citas.add(linkTo(methodOn(MedicosController.class).getMedicos()).withRel("medicos"));
        citas.add(linkTo(methodOn(PacientesController.class).getPacientebyId(cita.get().getIdPaciente().getId())).withRel("paciente"));
        citas.add(linkTo(methodOn(PacientesController.class).getPacientes()).withRel("pacientes"));

        return new ResponseEntity<>(citas, HttpStatus.OK);
    }

    /**
     * Crea una nueva cita
     * @param citaDTO
     * @see CitasDTO
     */
    @PostMapping
    public ResponseEntity<EntityModel<CitasModel>> createCita(@RequestBody CitasDTO citaDTO) {
        logger.info("POST: /citas -> Crear nueva cita");
        CitasModel nuevaCita = citasService.createCita(citaDTO.getFechaCita(), citaDTO.getHoraCita(),
                citaDTO.getIdMedico(), citaDTO.getIdPaciente());

        EntityModel<CitasModel> citas = EntityModel.of(nuevaCita);
        citas.add(linkTo(methodOn(CitasController.class).getCitabyId(nuevaCita.getId())).withSelfRel());
        citas.add(linkTo(methodOn(CitasController.class).getCitas()).withRel("citas"));
        citas.add(linkTo(methodOn(MedicosController.class).getMedicobyId(nuevaCita.getIdMedico().getId())).withRel("medico"));
        citas.add(linkTo(methodOn(MedicosController.class).getMedicos()).withRel("medicos"));
        citas.add(linkTo(methodOn(PacientesController.class).getPacientebyId(nuevaCita.getIdPaciente().getId())).withRel("paciente"));
        citas.add(linkTo(methodOn(PacientesController.class).getPacientes()).withRel("pacientes"));

        return new ResponseEntity<>(citas, HttpStatus.CREATED);
    }

    /**
     * Actualiza una cita
     * @param id
     * @param citaDTO
     */
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<CitasDTO>> updateCita(@PathVariable Long id, @RequestBody CitasDTO citaDTO) {
        logger.info("PUT: /citas/{} -> Actualizando cita", id);
        Optional<CitasModel> citaExistente = citasService.getCitaById(id);
        if (!citaExistente.isPresent()) {
            logger.error("PUT: /citas/{} -> cita no encontrada", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        CitasDTO citaActualizada = citasService.updateCita(id, citaDTO);
        EntityModel<CitasDTO> citas = EntityModel.of(citaActualizada);
        citas.add(linkTo(methodOn(CitasController.class).getCitabyId(id)).withSelfRel());
        citas.add(linkTo(methodOn(CitasController.class).getCitas()).withRel("citas"));
        citas.add(linkTo(methodOn(MedicosController.class).getMedicobyId(citaActualizada.getIdMedico())).withRel("medico"));
        citas.add(linkTo(methodOn(MedicosController.class).getMedicos()).withRel("medicos"));
        citas.add(linkTo(methodOn(PacientesController.class).getPacientebyId(citaActualizada.getIdPaciente())).withRel("paciente"));
        citas.add(linkTo(methodOn(PacientesController.class).getPacientes()).withRel("pacientes"));

        return new ResponseEntity<>(citas, HttpStatus.OK);
    }

    /**
     * Elimina una cita
     * @param id
     * @see CitasModel
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCita(@PathVariable Long id) {
        logger.info("DELETE: /citas/{} -> Eliminando cita", id);
        Optional<CitasModel> cita = citasService.getCitaById(id);
        if (!cita.isPresent()) {
            logger.error("DELETE: /citas/{} -> cita no encontrada", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        citasService.deleteCita(id);
        logger.info("DELETE: /citas/{} -> cita eliminada", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
