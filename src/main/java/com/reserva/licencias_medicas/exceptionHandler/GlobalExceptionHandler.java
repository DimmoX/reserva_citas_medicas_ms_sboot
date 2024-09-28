package com.reserva.licencias_medicas.exceptionHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

/**
 * @description Clase que maneja las excepciones globales
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    // Se crea un logger para manejar logs con excepciones
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Se manejan las excepciones de tipo Exception
   @ExceptionHandler(Exception.class)
    /**
     * @description Metodo para el manejo de Excepciones Globales.
     * @param ex
     * @param request
     * @return
     */
    public ResponseEntity<Object> handleGlobalException(Exception ex, WebRequest request) {
      logger.error("Se ha producido un error inesperado. {}", ex.getMessage());
      logger.debug("Detalle error: {}", ex);
      Map<String, Object> body = new HashMap<>();
      body.put("details", request.getDescription(false));
      body.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

      // Manejo específico para ConstraintViolationException (Cuando una validación falla),
      // se obtiene solo el mensaje de la validación.
      if (ex instanceof ConstraintViolationException) {
         ConstraintViolationException violationException = (ConstraintViolationException) ex;
         StringBuilder messageBuilder = new StringBuilder();

         for (ConstraintViolation<?> violation : violationException.getConstraintViolations()) {
            // Solo extrae el contenido de messageTemplate (mensaje error de validación)
            messageBuilder.append(violation.getMessageTemplate());
            break; // Solo necesitas el primer mensaje, si hay más de uno
         }
         
         body.put("message", messageBuilder.toString());
         return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
      }

      body.put("message", ex.getMessage());

      return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
   }

   // Se manejan las excepciones de tipo ResourceNotFoundException
   @ExceptionHandler(ResourceNotFoundException.class)
    /**
     * @description Metodo que maneja las excepciones de tipo ResourceNotFoundException
     * @param ex
     * @param request
     */
   public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
      logger.error("Resource not found. {}", ex.getMessage());
      logger.debug("Error details: {}", ex);
      Map<String, Object> body = new HashMap<>();
      body.put("message", ex.getMessage());
      body.put("details", request.getDescription(false));

      return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
   }

    // Se manejan las excepciones de tipo IllegalArgumentException
   @ExceptionHandler(IllegalArgumentException.class)
    /**
     * @description Metodo que maneja las excepciones de tipo IllegalArgumentException
     * @param ex
     * @param request
     */
   public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
      logger.error("Bad request. {}", ex.getMessage());
      logger.debug("Error details: {}", ex);
      Map<String, Object> body = new HashMap<>();
      body.put("message", ex.getMessage());
      body.put("details", request.getDescription(false));

      return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
   }

    // Se manejan las excepciones de tipo DatabaseTransactionException
   @ExceptionHandler(DatabaseTransactionException.class)
    /**
     * @description Metodo que maneja las excepciones de tipo DatabaseTransactionException
     * @param ex
     * @param request
     * @return
     */
   public ResponseEntity<Object> handleDatabaseTransactionException(DatabaseTransactionException ex, WebRequest request) {
      logger.error("Database transaction error. {}", ex.getMessage());
      logger.debug("Error details: {}", ex);
      Map<String, Object> body = new HashMap<>();
      body.put("message", ex.getMessage());
      body.put("details", request.getDescription(false));
      body.put("SQL error", ex.getCause().getMessage());

      return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
   }
}
