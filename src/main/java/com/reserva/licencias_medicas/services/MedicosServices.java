package com.reserva.licencias_medicas.services;

/**
 * @description Clase para gestionar los datos de los médicos
 * @Attributes {int} id - Identificador del médico
 * @Attributes {String} nombre - Nombre del médico
 * @Attributes {String} apellido - Apellido del médico
 * @Attributes {String} especialidad - Especialidad del médico
 */
public class MedicosServices {

    // Atributos de la clase
    private int id;
    private String nombre;
    private String apellido;
    private String especialidad;

    /**
     * @description Constructor de la clase
     * @param id
     * @param nombre
     * @param apellido
     * @param especialidad
     */
    public MedicosServices(int id, String nombre, String apellido, String especialidad) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.especialidad = especialidad;
    }

    /**
     * @description Método para obtener el id del médico
     * @return {int} id
     */
    public int getIdMedico() {
        return id;
    }  

    /**
     * @description Método para obtener el nombre del médico
     * @return {String} nombre
     */
    public String getNombreMedico() {
        return nombre;
    }

    /**
     * @description Método para obtener el apellido del médico
     * @return {String} apellido
     */
    public String getApellidoMedico() {
        return apellido;
    }

    /**
     * @description Método para obtener la especialidad del médico
     * @return {String} especialidad
     */
    public String getEspecialidad() {
        return especialidad;
    }
}
