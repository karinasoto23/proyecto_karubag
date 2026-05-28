package cl.karubag.usuario.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Maneja de forma centralizada todas las excepciones del proyecto.
 * Convierte las excepciones en respuestas JSON con formato estándar (ErrorResponse).
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> manejarRecursoNoEncontrado(
            ResourceNotFoundException ex,
            HttpServletRequest request) {

        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "Recurso no encontrado",
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorResponse> manejarRecursoDuplicado(
            DuplicateResourceException ex,
            HttpServletRequest request) {

        ErrorResponse error = new ErrorResponse(
                HttpStatus.CONFLICT.value(),
                "Recurso duplicado",
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> manejarExcepcionGenerica(
            Exception ex,
            HttpServletRequest request) {

        ErrorResponse error = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Error interno del servidor",
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
