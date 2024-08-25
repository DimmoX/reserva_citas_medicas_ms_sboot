package com.reserva.licencias_medicas.services;

/**
 * @description Clase para gestionar los datos de las citas
 * @Attributes {int} idCitas - Identificador de la reserva
 * @Attributes {String} fecha - Fecha de la reserva
 * @Attributes {String} hora - Hora de la reserva
 * @Attributes {int} idMedico - Identificador del médico
 * @Attributes {int} idPaciente - Identificador del paciente
 */
public class CitasServices {
        
        // Atributos de la clase
        private int idCitas;
        private String fecha;
        private String hora;
        private int idMedico;
        private int idPaciente;
    
        /**
        * @description Constructor de la clase
        * @param idCitas
        * @param fecha
        * @param hora
        * @param idMedico
        * @param idPaciente
        */
        public CitasServices(int idCitas, String fecha, String hora, int idMedico, int idPaciente) {
            this.idCitas = idCitas;
            this.fecha = fecha;
            this.hora = hora;
            this.idMedico = idMedico;
            this.idPaciente = idPaciente;
        }
    
        /**
        * @description Método para obtener el id de la reserva
        * @return {int} id
        */
        public int getIdReserva() {
            return idCitas;
        }
    
        /**
        * @description Método para obtener la fecha de la reserva
        * @return {String} fecha
        */
        public String getFechaReserva() {
            return fecha;
        }
    
        /**
        * @description Método para obtener la hora de la reserva
        * @return {String} hora
        */
        public String getHoraReserva() {
            return hora;
        }
    
        /**
        * @description Método para obtener el id del médico
        * @return {int} idMedico
        */
        public int getIdMedico() {
            return idMedico;
        }
    
        /**
        * @description Método para obtener el id del paciente
        * @return {int} idPaciente
        */
        public int getIdPaciente() {
            return idPaciente;
        }
}
