package com.reserva.licencias_medicas.services.pacientes;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.reserva.licencias_medicas.model.PacientesModel;
import com.reserva.licencias_medicas.respository.PacientesRepository;

@ExtendWith(MockitoExtension.class)
public class PacientesServiceTest {

    @Mock
    private PacientesRepository pacientesRepositoryMock;

    @InjectMocks
    private PacientesServiceImpl pacientesService;

    private PacientesModel paciente;
    private PacientesModel pacienteUpdate;

    @BeforeEach
    public void configuracionInicial() {

        // ** Se inicialilza objeto model en cada test.
        MockitoAnnotations.openMocks(this);

        // Médico original antes de la actualización
        paciente = new PacientesModel();
        paciente.setId(1L);
        paciente.setNombre("Cristian");
        paciente.setApellido("Saez");

        // Datos actualizados (especialidad cambia a Neurología)
        pacienteUpdate = new PacientesModel();
        pacienteUpdate.setId(1L);
        pacienteUpdate.setNombre("Javier");
        pacienteUpdate.setApellido("Saez");
    }

    @AfterEach
    public void limpiarRecursos() {

        // ** Se realiza limpieza de objeto model en cada test.
        paciente = null;
        pacienteUpdate = null;

        // ** se realiza reset de mock en cada test.
        Mockito.reset(pacientesRepositoryMock);
    }

    @Test
    @DisplayName("Test Crear Paciente")
    void testCreatePaciente() {
        // Simular el comportamiento del repository
        when(pacientesRepositoryMock.save(paciente)).thenReturn(paciente);

        // Ejecutar el método de servicio
        PacientesModel createdPaciente = pacientesService.createPaciente(paciente);

        assertNotNull(createdPaciente);

        // Verificar que el método save fue llamado
        verify(pacientesRepositoryMock, times(1)).save(createdPaciente);
    }

    @Test
    @DisplayName("Test Actualizar Paciente")
    void testUpdatePaciente() {
        // Simular que el médico existe en la base de datos
        when(pacientesRepositoryMock.findById(1L)).thenReturn(Optional.of(paciente));
        when(pacientesRepositoryMock.save(any(PacientesModel.class))).thenReturn(pacienteUpdate);

        // Ejecutar el método de servicio
        PacientesModel result = pacientesService.updatePaciente(1L, pacienteUpdate);

        // Verificar los resultados
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertNotEquals(paciente.getNombre(), result.getNombre());

        // Verificar que los métodos findById y save fueron llamados
        verify(pacientesRepositoryMock, times(1)).findById(1L);
        verify(pacientesRepositoryMock, times(1)).save(pacienteUpdate);
    }
}