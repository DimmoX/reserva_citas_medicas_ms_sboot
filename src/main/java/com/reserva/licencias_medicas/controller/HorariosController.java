package com.reserva.licencias_medicas.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.Map;
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

import com.reserva.licencias_medicas.dto.HorariosDTO;
import com.reserva.licencias_medicas.model.HorariosModel;
import com.reserva.licencias_medicas.model.MedicosModel;
import com.reserva.licencias_medicas.services.horarios.HorariosService;

@RestController
@RequestMapping("/horarios")
public class HorariosController {
    private static final Logger logger = LoggerFactory.getLogger(HorariosController.class);

    @Autowired
    private HorariosService horariosService;

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<HorariosModel>>> getHorarios() {
        logger.info("GET: /horarios -> Se obtienen todos los horarios");

        List<EntityModel<HorariosModel>> horarios = horariosService.getHorarios().stream()
                .map(horario -> {
                    MedicosModel medico = horario.getIdMedico();
                    return EntityModel.of(
                            horario,
                            linkTo(methodOn(HorariosController.class).getHorarioById(horario.getId()))
                                    .withRel("horario"),
                            linkTo(methodOn(HorariosController.class).getHorarios()).withRel("horarios"),
                            linkTo(methodOn(MedicosController.class).getMedicobyId(medico.getId())).withRel("medico"),
                            linkTo(methodOn(MedicosController.class).getMedicos()).withRel("medicos"));
                })
                .collect(Collectors.toList());

        return new ResponseEntity<>(CollectionModel.of(
                horarios,
                linkTo(methodOn(HorariosController.class).getHorarios()).withSelfRel()), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<EntityModel<HorariosModel>> getHorarioById(@PathVariable Long id) {
        logger.info("GET: /horarios/{} -> Obtener horario", id);
        Optional<HorariosModel> horario = horariosService.getHorarioById(id);
        if (!horario.isPresent()) {
            logger.error("GET: /horarios/{} -> Horario no encontrado", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.info("GET: /horarios/{} -> Horario encontrado", id);

        EntityModel<HorariosModel> horarios = EntityModel.of(horario.get());
        horarios.add(linkTo(methodOn(HorariosController.class).getHorarioById(id)).withRel("horario"));
        horarios.add(linkTo(methodOn(HorariosController.class).getHorarios()).withRel("horarios"));
        horarios.add(linkTo(methodOn(MedicosController.class).getMedicobyId(horario.get().getIdMedico().getId())).withRel("medico"));
        horarios.add(linkTo(methodOn(MedicosController.class).getMedicos()).withRel("medicos"));

        return new ResponseEntity<>(horarios, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<EntityModel<HorariosModel>> createHorario(@RequestBody Map<String, Object> horarioData) {
        logger.info("POST: /horarios -> Crear nuevo horario");
        HorariosModel horarioModel = horariosService.createHorario(horarioData);

        EntityModel<HorariosModel> horario = EntityModel.of(horarioModel);
        horario.add(linkTo(methodOn(HorariosController.class).getHorarioById(horarioModel.getId())).withRel("horario"));
        horario.add(linkTo(methodOn(HorariosController.class).getHorarios()).withRel("horarios"));
        horario.add(linkTo(methodOn(MedicosController.class).getMedicobyId(horarioModel.getIdMedico().getId())).withRel("medico"));
        horario.add(linkTo(methodOn(MedicosController.class).getMedicos()).withRel("medicos"));

        return new ResponseEntity<>(horario, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<HorariosModel>> updateHorario(@PathVariable Long id,
            @RequestBody HorariosDTO horarioUpdateDTO) {
        logger.info("PUT: /horarios/{} -> Actualizando horario", id);
        Optional<HorariosModel> horarioExistente = horariosService.getHorarioById(id);
        if (!horarioExistente.isPresent()) {
            logger.error("PUT: /horarios/{} -> Horario no encontrado", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.info("PUT: /horarios/{} -> Horario encontrado", id);

        HorariosModel updatedHorario = horariosService.updateHorario(id, horarioUpdateDTO);

        EntityModel<HorariosModel> horario = EntityModel.of(updatedHorario);
        horario.add(linkTo(methodOn(HorariosController.class).getHorarioById(id)).withRel("horario"));
        horario.add(linkTo(methodOn(HorariosController.class).getHorarios()).withRel("horarios"));
        horario.add(linkTo(methodOn(MedicosController.class).getMedicobyId(updatedHorario.getIdMedico().getId())).withRel("medico"));
        horario.add(linkTo(methodOn(MedicosController.class).getMedicos()).withRel("medicos"));

        return new ResponseEntity<>(horario, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHorario(@PathVariable Long id) {
        logger.info("DELETE: /horarios/{} -> Eliminando horario", id);
        Optional<HorariosModel> horario = horariosService.getHorarioById(id);
        if (!horario.isPresent()) {
            logger.error("DELETE: /horarios/{} -> Horario no encontrado", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        horariosService.deleteHorario(id);
        logger.info("DELETE: /horarios/{} -> Horario eliminado", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
