package com.reserva.licencias_medicas.services;

import java.util.List;
import java.util.Map;

/**
 * @description Clase para gestionar la información de los horarios
 * @Attributes {int} id
 * @Attributes {String} fecha
 * @Attributes {List<Map<String, List<Object>>>} hora
 * @Attributes {int} idMedico
 */
public class HorariosServices {
    private int id;
    private List<Map<String, Map<String, List<Map<String, String>>>>> fechas;
    private int idMedico;

    /**
     * @description Constructor de la clase
     * @param id
     * @param fechas
     * @param idMedico
     */
    public HorariosServices(int id, List<Map<String, Map<String, List<Map<String, String>>>>> fechas, int idMedico) {
        this.id = id;
        this.fechas = fechas;
        this.idMedico = idMedico;
    }

    /**
     * @description Método para obtener el id del horario
     * @return {int} id
     */
    public int getIdHorario() {
        return id;
    }

    /**
     * @description Método para obtener la hora del horario
     * @return {String} hora
     */
    public List<Map<String, Map<String, List<Map<String, String>>>>> getFechas() {
        return fechas;
    }

    /**
     * @description Método para obtener el id del médico
     * @return {String} idMedico
     */
    public int getIdMedico() {
        return idMedico;
    }
}
