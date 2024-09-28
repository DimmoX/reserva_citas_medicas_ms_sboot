package com.reserva.licencias_medicas.exceptionHandler;

/**
 * @description Clase que maneja las excepciones de transacciones de la base de datos
 */
public class DatabaseTransactionException extends RuntimeException {
       public DatabaseTransactionException(String message, Throwable cause) {
       super(message);
       super.initCause(cause);
    }
}
