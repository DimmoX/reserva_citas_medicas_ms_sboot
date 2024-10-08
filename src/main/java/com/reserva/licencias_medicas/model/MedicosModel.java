package com.reserva.licencias_medicas.model;

import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

/**
 * Clase que representa la tabla de médicos
 */
@Entity
@Table(name = "medicos")
public class MedicosModel extends RepresentationModel<MedicosModel>{

    //** Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre")
    @NotNull
    private String nombre;

    @Column(name = "apellido")
    @NotNull
    private String apellido;

    @Column(name = "especialidad")
    @NotNull
    private String especialidad;

    @OneToMany(mappedBy = "idMedico", cascade = CascadeType.REMOVE)
    private List<CitasModel> citas;

    @OneToMany(mappedBy = "idMedico", cascade = CascadeType.REMOVE)
    private List<HorariosModel> horarios;

    //** Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }
}
