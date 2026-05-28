package cl.karubag.usuario.mapper;

import cl.karubag.usuario.dto.UsuarioCreateRequest;
import cl.karubag.usuario.dto.UsuarioResponse;
import cl.karubag.usuario.model.Usuario;

import org.springframework.stereotype.Component;

/**
 * Mapper encargado de convertir entre las diferentes representaciones del Usuario:
 * - UsuarioCreateRequest (DTO de entrada)  -> Usuario (entidad JPA)
 * - Usuario (entidad JPA)                  -> UsuarioResponse (DTO de salida)
 *
 * También aplica el hashing de password antes de persistir,
 * para que nunca se guarde la contraseña en texto plano.
 */
@Component
public class UsuarioMapper {

    /**
     * Convierte el DTO de entrada en una entidad Usuario lista para persistir.
     * Importante: aquí se hashea la password antes de asignarla.
     */
    public Usuario aEntidad(UsuarioCreateRequest request) {
        Usuario usuario = new Usuario();
        usuario.setEmail(request.getEmail());
        usuario.setPasswordHash(hashPassword(request.getPassword()));
        usuario.setNombreCompleto(request.getNombreCompleto());
        usuario.setTelefono(request.getTelefono());
        usuario.setRol(request.getRol());
        usuario.setActivo(true);
        return usuario;
    }

    /**
     * Convierte una entidad Usuario en el DTO de salida que ve el cliente.
     * Importante: NUNCA se incluye el passwordHash en la respuesta.
     */
    public UsuarioResponse aResponse(Usuario usuario) {
        UsuarioResponse response = new UsuarioResponse();
        response.setId(usuario.getId());
        response.setEmail(usuario.getEmail());
        response.setNombreCompleto(usuario.getNombreCompleto());
        response.setTelefono(usuario.getTelefono());
        response.setRol(usuario.getRol());
        response.setActivo(usuario.getActivo());
        response.setCreadoEn(usuario.getCreadoEn());
        response.setActualizadoEn(usuario.getActualizadoEn());
        return response;
    }

    /**
     * Genera un "hash" muy simple a partir del password en claro.
     * Formato: "HASH_" + hashCode del password.
     *
     * ADVERTENCIA: Esto NO es un hash criptográfico real.
     * En un proyecto productivo se usaría BCrypt, Argon2, PBKDF2, etc.
     * Para este caso académico es suficiente para demostrar el concepto
     * de "nunca guardar la contraseña en texto plano".
     */
    private String hashPassword(String passwordEnClaro) {
        if (passwordEnClaro == null) {
            return null;
        }
        return "HASH_" + passwordEnClaro.hashCode();
    }
}
