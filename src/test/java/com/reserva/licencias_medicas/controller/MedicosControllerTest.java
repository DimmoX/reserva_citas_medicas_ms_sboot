package com.reserva.licencias_medicas.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.reserva.licencias_medicas.model.MedicosModel;
import com.reserva.licencias_medicas.services.medicos.MedicosService;

public class MedicosControllerTest {
    @Mock
    private MedicosService medicosServiceMock;

    @InjectMocks
    private MedicosController medicosController;

    private MedicosModel medico1;
    private MedicosModel medico2;

    private MedicosModel medico;
    private MedicosModel medicoActualizado;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Datos de prueba para obtener médicos
        medico1 = new MedicosModel();
        medico1.setId(1L);
        medico1.setNombre("Juan");
        medico1.setApellido("Pérez");
        medico1.setEspecialidad("Cardiología");

        medico2 = new MedicosModel();
        medico2.setId(2L);
        medico2.setNombre("Diego");
        medico2.setApellido("López");
        medico2.setEspecialidad("Neurología");

        // Datos de prueba para actualizar médico
        // Médico original
        medico = new MedicosModel();
        medico.setId(1L);
        medico.setNombre("Juan");
        medico.setApellido("Pérez");
        medico.setEspecialidad("Cardiología");

        // Datos actualizados
        medicoActualizado = new MedicosModel();
        medicoActualizado.setNombre("Diego");
        medicoActualizado.setApellido("Pérez");
        medicoActualizado.setEspecialidad("Neurología");
    }

    @AfterEach
    public void tearDown() {
        medico1 = null;
        medico2 = null;
        medico = null;
        medicoActualizado = null;

        reset(medicosServiceMock);
    }

    @Test
    @DisplayName("Test Obtener Todos los Médicos")
    void testGetMedicos() {
        // Simular el comportamiento del servicio
        List<MedicosModel> listaMedicos = Arrays.asList(medico1, medico2);
        when(medicosServiceMock.getMedicos()).thenReturn(listaMedicos);

        // Ejecutar el método del controlador
        ResponseEntity<CollectionModel<EntityModel<MedicosModel>>> response = medicosController.getMedicos();

        // Verificar resultados
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().getContent().size());

        // Verificar que el servicio fue llamado
        verify(medicosServiceMock, times(1)).getMedicos();
    }

    @Test
    @DisplayName("Test Actualizar Médico")
    void testUpdateMedico() {
        // Simular el comportamiento del servicio
        when(medicosServiceMock.updateMedico(1L, medicoActualizado)).thenReturn(medicoActualizado);

        // Ejecutar el método del controlador
        ResponseEntity<EntityModel<MedicosModel>> response = medicosController.updateMedico(1L, medicoActualizado);

        // Verificar resultados
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        MedicosModel updatedMedico = response.getBody().getContent();
        assertNotNull(updatedMedico);

        // Verificar que al menos un campo sea diferente
        assertNotEquals(medico.getNombre(), updatedMedico.getNombre()); // El nombre debería haber cambiado
        assertEquals(medico.getApellido(), updatedMedico.getApellido()); // El apellido permanece igual
        assertNotEquals(medico.getEspecialidad(), updatedMedico.getEspecialidad()); // La especialidad debe haber cambiado

        // Verificar que los métodos del servicio fueron llamados
        verify(medicosServiceMock, times(1)).updateMedico(1L, medicoActualizado);
    }
}
