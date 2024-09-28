package com.reserva.licencias_medicas.model;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

/**
 * Clase que representa la tabla de horarios
 */
@Entity
@Table(name = "horarios")
public class HorariosModel {

    //** Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "fechas_disponibles")
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Pattern(regexp = "^(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[0-2])-[0-9]{4}$", message = "La fecha debe tener el formato MM-DD-YYYY")
    private LocalDate fechaDisponible;

    @ElementCollection
    @CollectionTable(name = "horas_disponibles", joinColumns = @JoinColumn(name = "horario_id"))
    @Column(name = "hora")
    private List<String> horasDisponibles;  // Lista de horas disponibles

    // Relación con MedicosModel (Muchos a Uno)
    @ManyToOne
    @JoinColumn(name = "id_medico") // Llave foránea a la tabla de médicos
    private MedicosModel idMedico;

    //** Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFechaDisponible() {
        return fechaDisponible;
    }

    public void setFechaDisponible(LocalDate fechaDisponible) {
        this.fechaDisponible = fechaDisponible;
    }

    public List<String> getHorasDisponibles() {
        return horasDisponibles;
    }

    public void setHorasDisponibles(List<String> horasDisponibles) {
        this.horasDisponibles = horasDisponibles;
    }

    public MedicosModel getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(MedicosModel idMedico) {
        this.idMedico = idMedico;
    }
    
}
