package com.reserva.licencias_medicas.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

import com.reserva.licencias_medicas.model.PacientesModel;
import com.reserva.licencias_medicas.services.pacientes.PacientesService;

public class PacientesControllerTest {
    @Mock
    private PacientesService pacientesServiceMock;

    @InjectMocks
    private PacientesController pacientesController;

    private PacientesModel paciente1;
    private PacientesModel paciente2;

    private PacientesModel paciente;
    private PacientesModel pacienteActualizado;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Datos de prueba para obtener pacientes
        paciente1 = new PacientesModel();
        paciente1.setId(1L);
        paciente1.setNombre("María");
        paciente1.setApellido("Gómez");

        paciente2 = new PacientesModel();
        paciente2.setId(2L);
        paciente2.setNombre("Carlos");
        paciente2.setApellido("Rodríguez");

        // Datos de prueba para actualizar paciente
        // Paciente original
        paciente = new PacientesModel();
        paciente.setId(1L);
        paciente.setNombre("María");
        paciente.setApellido("Gómez");

        // Datos actualizados
        pacienteActualizado = new PacientesModel();
        pacienteActualizado.setNombre("Ana");
        pacienteActualizado.setApellido("Gómez");
    }

    @AfterEach
    public void tearDown() {
        paciente1 = null;
        paciente2 = null;
        paciente = null;
        pacienteActualizado = null;

        reset(pacientesServiceMock);
    }

    @Test
    @DisplayName("Test Obtener Todos los Pacientes")
    void testGetPacientes() {
        // Simular el comportamiento del servicio
        List<PacientesModel> listaPacientes = Arrays.asList(paciente1, paciente2);
        when(pacientesServiceMock.getPacientes()).thenReturn(listaPacientes);

        // Ejecutar el método del controlador
        ResponseEntity<CollectionModel<EntityModel<PacientesModel>>> response = pacientesController.getPacientes();

        // Verificar resultados
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().getContent().size());

        // Verificar que el servicio fue llamado
        verify(pacientesServiceMock, times(1)).getPacientes();
    }

    @Test
    @DisplayName("Test Actualizar Paciente")
    void testUpdatePaciente() {
        // Se simula el comportamiento del servicio
        when(pacientesServiceMock.getPacienteById(1L)).thenReturn(Optional.of(paciente));
        when(pacientesServiceMock.updatePaciente(1L, pacienteActualizado)).thenReturn(pacienteActualizado);

        ResponseEntity<EntityModel<PacientesModel>> response = pacientesController.updatePaciente(1L,
                pacienteActualizado);

        // Se verifican los resultados
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        PacientesModel updatedPaciente = response.getBody().getContent();
        assertNotNull(updatedPaciente);

        // Se verifica que los valores e un paciente sean diferentes al actualizarlo
        assertNotEquals(paciente.getNombre(), updatedPaciente.getNombre()); // El nombre debería haber cambiado
        assertEquals(paciente.getApellido(), updatedPaciente.getApellido()); // El apellido permanece igual

        // Se verica que los métodos del servicio fueron llamados
        verify(pacientesServiceMock, times(1)).getPacienteById(1L);
        verify(pacientesServiceMock, times(1)).updatePaciente(1L, pacienteActualizado);
    }

}
