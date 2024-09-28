package com.reserva.licencias_medicas.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<List<PacientesModel>> getPacientes(){
        logger.info("GET: /pacientes -> Se obtienen todos los pacientes");
        List<PacientesModel> pacientes = pacientesService.getPacientes();
        if( pacientes.isEmpty() ){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.info("GET: /pacientes -> pacientes encontrados: {}", pacientes.size());
        return new ResponseEntity<>(pacientes, HttpStatus.OK);
    }

    /**
     * Obtiene un paciente por su id
     * @param id
     */
    @GetMapping(path = "/{id}")
    public ResponseEntity<PacientesModel> getPacientebyId(@PathVariable Long id) {
        logger.info("GET: /pacientes/{} -> Obtener paciente", id);
        Optional<PacientesModel> paciente = pacientesService.getPacienteById(id);
        if (!paciente.isPresent()) {
            logger.error("GET: /pacientes/{} -> paciente no encontrado", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.info("GET: /pacientes/{} -> paciente encontrado", id);
        return new ResponseEntity<>(paciente.get(), HttpStatus.OK);
    }

    /**
     * Crear un nuevo paciente
     * @param paciente
     */
    @PostMapping
    public ResponseEntity<PacientesModel> createPaciente(@RequestBody PacientesModel paciente) {
        logger.info("POST: /pacientes -> Crear nuevo paciente");
        PacientesModel pacienteModel = pacientesService.createPaciente(paciente);
        logger.info("POST: /pacientes -> paciente creado", pacienteModel.getId());
        return new ResponseEntity<>(pacienteModel, HttpStatus.CREATED);
    }

    /**
     * Actualizar un paciente
     * @param id
     * @param paciente
     */
    @PutMapping(path = "/{id}")
    public ResponseEntity<PacientesModel> updatePaciente(@PathVariable Long id, @RequestBody PacientesModel paciente) {
        logger.info("PUT: /pacientes/{} -> Actualizar paciente", id);
        Optional<PacientesModel> pacienteModel1 = pacientesService.getPacienteById(id);
        if (!pacienteModel1.isPresent()) {
            logger.error("PUT: /pacientes/{} -> paciente no encontrado", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        paciente.setId(id);
        PacientesModel pacienteModel2 = pacientesService.updatePaciente(id, paciente);
        logger.info("PUT: /pacientes/{} -> paciente actualizado", id);
        return new ResponseEntity<>(pacienteModel2, HttpStatus.OK);
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
            logger.error("DELETE: /pacientes/{} -> paciente no encontrado", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        pacientesService.deletePaciente(id);
        logger.info("DELETE: /pacientes/{} -> paciente eliminado", id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
