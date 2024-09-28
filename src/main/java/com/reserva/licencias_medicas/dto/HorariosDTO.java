package com.reserva.licencias_medicas.dto;

import java.util.List;

public class HorariosDTO {
    private String fecha;
    private List<String> horasDisponibles;
    private Long idMedico;

    //** Getters and Setters
    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public List<String> getHorasDisponibles() {
        return horasDisponibles;
    }

    public void setHorasDisponibles(List<String> horasDisponibles) {
        this.horasDisponibles = horasDisponibles;
    }

    public Long getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(Long idMedico) {
        this.idMedico = idMedico;
    }
}
