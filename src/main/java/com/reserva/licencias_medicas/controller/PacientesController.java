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

import com.reserva.licencias_medicas.model.PacientesModel;
import com.reserva.licencias_medicas.services.pacientes.PacientesService;

/**
 * PacientesController
 */
@RestController
@RequestMapping ("/pacientes")
public class PacientesController {
    
    private static final Logger logger = LoggerFactory.getLogger(HorariosController.class);

    @Autowired
    private PacientesService pacientesService;

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<PacientesModel>>> getPacientes() {
        logger.info("GET: /pacientes -> Se obtienen todos los pacientes");
        List<PacientesModel> pacientes = pacientesService.getPacientes();

        if (pacientes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<EntityModel<PacientesModel>> pacientesModel = pacientes.stream()
                .map(paciente -> EntityModel.of(paciente,
                        linkTo(methodOn(PacientesController.class).getPacientebyId(paciente.getId())).withSelfRel(),
                        linkTo(methodOn(PacientesController.class).getPacientes()).withRel("pacientes")))
                .collect(Collectors.toList());

        return new ResponseEntity<>(CollectionModel.of(pacientesModel,
                linkTo(methodOn(PacientesController.class).getPacientes()).withSelfRel()), HttpStatus.OK);
    }

    /**
     * Obtiene un paciente por su id
     * @param id
     */
    @GetMapping(path = "/{id}")
    public ResponseEntity<EntityModel<PacientesModel>> getPacientebyId(@PathVariable Long id) {
        logger.info("GET: /pacientes/{} -> Obtener paciente", id);
        Optional<PacientesModel> paciente = pacientesService.getPacienteById(id);

        if (!paciente.isPresent()) {
            logger.error("GET: /pacientes/{} -> Paciente no encontrado", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        EntityModel<PacientesModel> resource = EntityModel.of(paciente.get(),
                linkTo(methodOn(PacientesController.class).getPacientebyId(id)).withSelfRel(),
                linkTo(methodOn(PacientesController.class).getPacientes()).withRel("pacientes"));

        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    /**
     * Crear un nuevo paciente
     * @param paciente
     */
    @PostMapping
    public ResponseEntity<EntityModel<PacientesModel>> createPaciente(@RequestBody PacientesModel paciente) {
        logger.info("POST: /pacientes -> Crear nuevo paciente");
        PacientesModel pacienteModel = pacientesService.createPaciente(paciente);

        EntityModel<PacientesModel> resource = EntityModel.of(pacienteModel,
                linkTo(methodOn(PacientesController.class).getPacientebyId(pacienteModel.getId())).withSelfRel(),
                linkTo(methodOn(PacientesController.class).getPacientes()).withRel("pacientes"));

        return new ResponseEntity<>(resource, HttpStatus.CREATED);
    }

    /**
     * Actualizar un paciente
     * @param id
     * @param paciente
     */
    @PutMapping(path = "/{id}")
    public ResponseEntity<EntityModel<PacientesModel>> updatePaciente(@PathVariable Long id,
            @RequestBody PacientesModel paciente) {
        logger.info("PUT: /pacientes/{} -> Actualizar paciente", id);
        Optional<PacientesModel> pacienteModel1 = pacientesService.getPacienteById(id);

        if (!pacienteModel1.isPresent()) {
            logger.error("PUT: /pacientes/{} -> Paciente no encontrado", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        paciente.setId(id);
        PacientesModel pacienteModel2 = pacientesService.updatePaciente(id, paciente);

        EntityModel<PacientesModel> resource = EntityModel.of(pacienteModel2,
                linkTo(methodOn(PacientesController.class).getPacientebyId(id)).withSelfRel(),
                linkTo(methodOn(PacientesController.class).getPacientes()).withRel("pacientes"));

        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    /**
     * Eliminar un paciente
     * @param id
     */
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deletePaciente(@PathVariable Long id) {
        logger.info("DELETE: /pacientes/{} -> Eliminar paciente", id);
        Optional<PacientesModel> pacienteModel = pacientesService.getPacienteById(id);

        if (!pacienteModel.isPresent()) {
            logger.error("DELETE: /pacientes/{} -> Paciente no encontrado", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        pacientesService.deletePaciente(id);
        logger.info("DELETE: /pacientes/{} -> Paciente eliminado", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
