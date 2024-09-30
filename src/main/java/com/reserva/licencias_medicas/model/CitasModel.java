package com.reserva.licencias_medicas.model;

import java.time.LocalDate;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
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
 * CitasModel
 * Clase que representa el modelo de las citas médicas en la base de datos
 */
@Entity
@Table(name = "citas")
public class CitasModel extends RepresentationModel<CitasModel>{
    //** Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "fecha_cita")
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Pattern(regexp = "^(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[0-2])-[0-9]{4}$", message = "La fecha debe tener el formato MM-DD-YYYY")
    private LocalDate fechaCita;

    @Column(name = "hora_cita")
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    @Pattern(regexp = "^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$", message = "La hora debe tener el formato HH:mm")
    private String horaCita;

    //** Relación con MedicosModel (Muchos a Uno)
    @ManyToOne
    @JoinColumn(name = "id_medico") // Llave foránea a la tabla de médicos
    private MedicosModel idMedico;

    //** Relación con PacientesModel (Muchos a Uno)
    @ManyToOne
    @JoinColumn(name = "id_paciente") // Llave foránea a la tabla de pacientes
    private PacientesModel idPaciente;

    //** Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFechaCita() {
        return fechaCita;
    }

    public void setFechaCita(LocalDate fechaCita) {
        this.fechaCita = fechaCita;
    }

    public String getHoraCita() {
        return horaCita;
    }

    public void setHoraCita(String horaCita) {
        this.horaCita = horaCita;
    }

    public PacientesModel getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(PacientesModel idPaciente) {
        this.idPaciente = idPaciente;
    }

    public MedicosModel getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(MedicosModel idMedico) {
        this.idMedico = idMedico;
    }
}
