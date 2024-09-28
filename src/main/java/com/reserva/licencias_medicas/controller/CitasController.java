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

import com.reserva.licencias_medicas.dto.CitasDTO;
import com.reserva.licencias_medicas.model.CitasModel;
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
    public ResponseEntity<List<CitasModel>> getCitas(){
        logger.info("GET: /citas -> Se obtienen todas las citas");
        List<CitasModel> citas = citasService.getCitas();
        if( citas.isEmpty() ){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.info("GET: /citas -> citas encontrados: {}", citas.size());
        return new ResponseEntity<>(citas, HttpStatus.OK);
    }

    /**
     * Obtiene una cita por su id
     * @param id
     */
    @GetMapping(path = "/{id}")
    public ResponseEntity<CitasModel> getCitabyId(@PathVariable Long id) {
        logger.info("GET: /citas/{} -> Obtener cita", id);
        Optional<CitasModel> cita = citasService.getCitaById(id);
        if (!cita.isPresent()) {
            logger.error("GET: /citas/{} -> cita no encontrada", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.info("GET: /citas/{} -> cita encontrado", id);
        return new ResponseEntity<>(cita.get(), HttpStatus.OK);
    }

    /**
     * Crea una nueva cita
     * @param citaDTO
     * @see CitasDTO
     */
    @PostMapping
    public ResponseEntity<CitasModel> createCita(@RequestBody CitasDTO citaDTO) {
        logger.info("POST: /citas -> Crear nueva cita");
        CitasModel nuevaCita = citasService.createCita(citaDTO.getFechaCita(), citaDTO.getHoraCita(), citaDTO.getIdMedico(), citaDTO.getIdPaciente());

        return new ResponseEntity<>(nuevaCita, HttpStatus.CREATED);
    }

    /**
     * Actualiza una cita
     * @param id
     * @param citaDTO
     */
    @PutMapping("/{id}")
    public ResponseEntity<CitasDTO> updateCita(@PathVariable Long id, @RequestBody CitasDTO citaDTO) {
        logger.info("PUT: /citas/{} -> Actualizando cita", id);
        Optional<CitasModel> citaExistente = citasService.getCitaById(id);
        if (!citaExistente.isPresent()) {
            logger.error("PUT: /citas/{} -> cita no encontrada", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.info("PUT: /citas/{} -> cita encontrada", id);
        CitasDTO citaActualizada = citasService.updateCita(id, citaDTO);
        return new ResponseEntity<>(citaActualizada, HttpStatus.OK);
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
        logger.info("DELETE: /citas/{} -> cita encontrada", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
