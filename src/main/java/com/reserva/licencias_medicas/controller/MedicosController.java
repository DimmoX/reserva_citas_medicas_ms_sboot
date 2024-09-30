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
    public ResponseEntity<CollectionModel<EntityModel<MedicosModel>>> getMedicos() {
        logger.info("GET: /medicos -> Se obtienen todos los médicos");
        List<MedicosModel> medicos = medicosService.getMedicos();

        if (medicos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<EntityModel<MedicosModel>> medicosModel = medicos.stream()
                .map(medico -> EntityModel.of(medico,
                        linkTo(methodOn(MedicosController.class).getMedicobyId(medico.getId())).withSelfRel(),
                        linkTo(methodOn(MedicosController.class).getMedicos()).withRel("medicos")))
                .collect(Collectors.toList());

        return new ResponseEntity<>(CollectionModel.of(medicosModel,
                linkTo(methodOn(MedicosController.class).getMedicos()).withSelfRel()), HttpStatus.OK);
    }

    /**
     * Obtiene un medico por su id
     * @param id
     */
    @GetMapping(path = "/{id}")
    public ResponseEntity<EntityModel<MedicosModel>> getMedicobyId(@PathVariable Long id) {
        logger.info("GET: /medicos/{} -> Obtener médico", id);
        Optional<MedicosModel> medico = medicosService.getMedicoById(id);

        if (!medico.isPresent()) {
            logger.error("GET: /medicos/{} -> Médico no encontrado", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        EntityModel<MedicosModel> resource = EntityModel.of(medico.get(),
                linkTo(methodOn(MedicosController.class).getMedicobyId(id)).withSelfRel(),
                linkTo(methodOn(MedicosController.class).getMedicos()).withRel("medicos"));

        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    /**
     * Crear un medico
     * @param medico
     */
    @PostMapping
    public ResponseEntity<EntityModel<MedicosModel>> createMedico(@RequestBody MedicosModel medico) {
        logger.info("POST: /medicos -> Crear nuevo médico");
        MedicosModel m = medicosService.createMedico(medico);

        EntityModel<MedicosModel> resource = EntityModel.of(m,
                linkTo(methodOn(MedicosController.class).getMedicobyId(m.getId())).withSelfRel(),
                linkTo(methodOn(MedicosController.class).getMedicos()).withRel("medicos"));

        return new ResponseEntity<>(resource, HttpStatus.CREATED);
    }

    /**
     * Actualizar un medico
     * @param id
     * @param medico
     */
    @PutMapping(path = "/{id}")
    public ResponseEntity<EntityModel<MedicosModel>> updateMedico(@PathVariable Long id,
            @RequestBody MedicosModel medico) {
        logger.info("PUT: /medicos/{} -> Actualizar médico", id);
        MedicosModel updatedMedico = medicosService.updateMedico(id, medico);

        if (updatedMedico == null) {
            logger.error("PUT: /medicos/{} -> Médico no encontrado", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        EntityModel<MedicosModel> resource = EntityModel.of(updatedMedico,
                linkTo(methodOn(MedicosController.class).getMedicobyId(id)).withSelfRel(),
                linkTo(methodOn(MedicosController.class).getMedicos()).withRel("medicos"));

        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    /**
     * Eliminar un medico
     * @param id
     */
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteMedico(@PathVariable Long id) {
        logger.info("DELETE: /medicos/{} -> Eliminar médico", id);
        medicosService.deleteMedico(id);
        logger.info("DELETE: /medicos/{} -> Médico eliminado", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
