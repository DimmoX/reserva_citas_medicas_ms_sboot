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

import com.reserva.licencias_medicas.model.MedicosModel;
import com.reserva.licencias_medicas.services.medicos.MedicosService;

/**
 * MedicosController
 */
@RestController
@RequestMapping("/medicos")
public class MedicosController {
    
    private static final Logger logger = LoggerFactory.getLogger(HorariosController.class);

    @Autowired
    private MedicosService medicosService;

    /**
     * Obtiene todos los medicos
     */
    @GetMapping
    public ResponseEntity<List<MedicosModel>> getMedicos(){
        logger.info("GET: /medicos -> Se obtienen todos los medicos");
        List<MedicosModel> medicos = medicosService.getMedicos();
        if( medicos.isEmpty() ){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.info("GET: /medicos -> medicos encontrados: {}", medicos.size());
        return new ResponseEntity<>(medicos, HttpStatus.OK);
    }

    /**
     * Obtiene un medico por su id
     * @param id
     */
    @GetMapping(path = "/{id}")
    public ResponseEntity<MedicosModel> getMedicobyId(@PathVariable Long id) {
        logger.info("GET: /medicos/{} -> Obtener medico", id);
        Optional<MedicosModel> medico = medicosService.getMedicoById(id);
        if (!medico.isPresent()) {
            logger.error("GET: /medicos/{} -> medico no encontrado", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.info("GET: /medicos/{} -> medico encontrado", id);
        return new ResponseEntity<>(medico.get(), HttpStatus.OK);
    }

    /**
     * Crear un medico
     * @param medico
     */
    @PostMapping
    public ResponseEntity<MedicosModel> createMedico(@RequestBody MedicosModel medico) {
        logger.info("POST: /medicos -> Crear nuevo medico");
        MedicosModel m = medicosService.createMedico(medico);
        logger.info("POST: /medicos -> medico creado", m.getId());
        return new ResponseEntity<>(m, HttpStatus.CREATED);
    }

    /**
     * Actualizar un medico
     * @param id
     * @param medico
     */
    @PutMapping(path = "/{id}")
    public ResponseEntity<MedicosModel> updateMedico(@PathVariable Long id, @RequestBody MedicosModel medico) {
        logger.info("PUT: /medicos/{} -> Actualizar medico", id);
        MedicosModel m = medicosService.updateMedico(id, medico);
        if (m == null) {
            logger.error("PUT: /medicos/{} -> medico no encontrado", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.info("PUT: /medicos/{} -> medico actualizado", id);
        return new ResponseEntity<>(m, HttpStatus.OK);
    }

    /**
     * Eliminar un medico
     * @param id
     */
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteMedico(@PathVariable Long id) {
        logger.info("DELETE: /medicos/{} -> Eliminar medico", id);
        medicosService.deleteMedico(id);
        logger.info("DELETE: /medicos/{} -> medico eliminado", id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
