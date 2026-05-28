package cl.karubag.usuario.exception;

/**
 * Excepción que se lanza cuando se busca un recurso que no existe.
 * Se traduce a HTTP 404 Not Found.
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String mensaje) {
        super(mensaje);
    }
}
