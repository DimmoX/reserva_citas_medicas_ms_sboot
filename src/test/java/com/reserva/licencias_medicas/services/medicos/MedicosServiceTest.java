package com.reserva.licencias_medicas.services.medicos;

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

import com.reserva.licencias_medicas.model.MedicosModel;
import com.reserva.licencias_medicas.respository.MedicosRepository;

@ExtendWith(MockitoExtension.class)
public class MedicosServiceTest {

    @Mock
    private MedicosRepository medicosRepositoryMock;

    @InjectMocks
    private MedicosServiceImpl medicosService;

    private MedicosModel medico;
    private MedicosModel medicoUpdate;

    @BeforeEach
    public void configuracionInicial() {

        // ** Se inicialilza objeto model en cada test.
        MockitoAnnotations.openMocks(this);
        
        // Médico original antes de la actualización
        medico = new MedicosModel();
        medico.setId(1L);
        medico.setNombre("Juan");
        medico.setApellido("Pérez");
        medico.setEspecialidad("Cardiología");
        
        // Datos actualizados (especialidad cambia a Neurología)
        medicoUpdate = new MedicosModel();
        medicoUpdate.setNombre("Juan");
        medicoUpdate.setApellido("Pérez");
        medicoUpdate.setEspecialidad("Neurología");
    }

    @AfterEach
    public void limpiarRecursos() {

        //** Se realiza limpieza de objeto model en cada test.
        medico = null;
        medicoUpdate = null;

        //** se realiza reset de mock en cada test.
        Mockito.reset(medicosRepositoryMock);
    }

    @Test
    @DisplayName("Test Crear Médico")
    void testCreateMedico() {
        // Simular el comportamiento del repository
        when(medicosRepositoryMock.save(medico)).thenReturn(medico);

        // Ejecutar el método de servicio
        MedicosModel createdMedico = medicosService.createMedico(medico);

        assertNotNull(createdMedico);

        // Verificar que el método save fue llamado
        verify(medicosRepositoryMock, times(1)).save(createdMedico);
    }

    @Test
    @DisplayName("Test Actualizar Médico")
    void testUpdateMedico() {
         // Simular que el médico existe en la base de datos
        when(medicosRepositoryMock.findById(1L)).thenReturn(Optional.of(medico));
        when(medicosRepositoryMock.save(any(MedicosModel.class))).thenReturn(medicoUpdate);

        // Ejecutar el método de servicio
        MedicosModel result = medicosService.updateMedico(1L, medicoUpdate);

        // Verificar los resultados
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Juan", result.getNombre());
        assertEquals("Pérez", result.getApellido());
        assertEquals("Neurología", result.getEspecialidad());  // Cambió la especialidad

        // Verificar que los métodos findById y save fueron llamados
        verify(medicosRepositoryMock, times(1)).findById(1L);
        verify(medicosRepositoryMock, times(1)).save(medicoUpdate);
    }
}
