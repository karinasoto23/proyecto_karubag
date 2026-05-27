package cl.karubag.usuario.dto;

import cl.karubag.usuario.model.Rol;

/**
 * DTO para crear un nuevo usuario.
 * 
 * Es lo que el cliente envía en el body de un POST /api/v1/usuarios.
 * No incluye id ni timestamps porque la BD los genera automáticamente.
 */
public class UsuarioCreateRequest {

    private String email;
    private String password;
    private String nombreCompleto;
    private String telefono;
    private Rol rol;

    // ===== Constructor vacío (requerido por Jackson para deserializar JSON) =====
    public UsuarioCreateRequest() {
    }

    // ===== Getters y Setters =====

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
}
