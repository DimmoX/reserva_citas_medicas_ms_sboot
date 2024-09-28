package com.reserva.licencias_medicas.controller;

import java.util.List;
import java.util.Map;
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

import com.reserva.licencias_medicas.dto.HorariosDTO;
import com.reserva.licencias_medicas.model.HorariosModel;
import com.reserva.licencias_medicas.services.horarios.HorariosService;

@RestController
@RequestMapping("/horarios")
public class HorariosController {
    private static final Logger logger = LoggerFactory.getLogger(HorariosController.class);

    @Autowired
    private HorariosService horariosService;

    @GetMapping
    public ResponseEntity<List<HorariosModel>> getEnvios(){
        logger.info("GET: /horarios -> Se obtienen todos los envios");
        List<HorariosModel> horarios = horariosService.getHorarios();
        if( horarios.isEmpty() ){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.info("GET: /horarios -> horarios encontrados: {}", horarios.size());
        return new ResponseEntity<>(horarios, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<HorariosModel> getEnvioById(@PathVariable Long id) {
        logger.info("GET: /horarios/{} -> Obtener horarios", id);
        Optional<HorariosModel> horario = horariosService.getHorarioById(id);
        if (!horario.isPresent()) {
            logger.error("GET: /horarios/{} -> Horario no encontrado", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.info("GET: /horarios/{} -> Envio encontrado", id);
        return new ResponseEntity<>(horario.get(), HttpStatus.OK);
    }

     @PostMapping
    public ResponseEntity<HorariosModel> createHorario(@RequestBody Map<String, Object> horarioData) {
        HorariosModel newHorario = horariosService.createHorario(horarioData);
        return new ResponseEntity<>(newHorario, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HorariosModel> updateHorario(@PathVariable Long id, @RequestBody HorariosDTO horarioUpdateDTO) {
        logger.info("PUT: /horarios/{} -> Actualizando horario", id);
        Optional<HorariosModel> h = horariosService.getHorarioById(id);

        if (!h.isPresent()) {
            logger.error("PUT: /horarios/{} -> horario no encontrado", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        logger.info("PUT: /horarios/{} -> horario encontrado", id);

        // Llamar al servicio para actualizar el horario, pasando el DTO
        HorariosModel updatedHorario = horariosService.updateHorario(id, horarioUpdateDTO);

        return new ResponseEntity<>(updatedHorario, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnvio(@PathVariable Long id) {
        logger.info("DELETE: /horarios/{} -> Eliminando horario", id);
        Optional<HorariosModel> horario = horariosService.getHorarioById(id);
        if (!horario.isPresent()) {
            logger.error("DELETE: /horarios/{} -> horario no encontrado", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        horariosService.deleteHorario(id);
        logger.info("DELETE: /horarios/{} -> horario encontrado", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
