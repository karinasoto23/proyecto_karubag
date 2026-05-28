package cl.karubag.usuario.exception;

/**
 * Excepción que se lanza cuando se intenta crear un recurso que ya existe
 * (por ejemplo, un usuario con un email ya registrado).
 * Se traduce a HTTP 409 Conflict.
 */
public class DuplicateResourceException extends RuntimeException {

    public DuplicateResourceException(String mensaje) {
        super(mensaje);
    }
}
