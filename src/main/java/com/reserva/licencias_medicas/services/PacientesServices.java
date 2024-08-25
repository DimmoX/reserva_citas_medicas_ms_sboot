package com.reserva.licencias_medicas.services;

/**
 * @description Clase para gestionar los datos de los pacientes
 * @Attributes {int} id - Identificador del paciente
 * @Attributes {String} nombre - Nombre del paciente
 * @Attributes {String} apellido - Apellido del paciente
 * @Attributes {String} rut - Rut del paciente
 * @Attributes {String} telefono - Teléfono del paciente
 */
public class PacientesServices {
    
    // Atributos de la clase
    private int id;
    private String nombre;
    private String apellido;
    private String rut;
    private String telefono;

    /**
     * @description Constructor de la clase
     * @param id
     * @param nombre
     * @param apellido
     * @param rut
     * @param telefono
     */
    public PacientesServices(int id, String nombre, String apellido, String rut, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.rut = rut;
        this.telefono = telefono;
    }

    /**
     * @description Método para obtener el id del paciente
     * @return {int} id
     */
    public int getId() {
        return id;
    }

    /**
     * @description Método para obtener el nombre del paciente
     * @return {String} nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @description Método para obtener el apellido del paciente
     * @return {String} apellido
     */
    public String getApellido() {
        return apellido;
    }

    /**
     * @description Método para obtener el rut del paciente
     * @return {String} rut
     */
    public String getRut() {
        return rut;
    }

    /**
     * @description Método para obtener el teléfono del paciente
     * @return {String} telefono
     */
    public String getTelefono() {
        return telefono;
    }
}
