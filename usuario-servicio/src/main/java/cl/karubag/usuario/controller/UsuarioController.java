package cl.karubag.usuario.controller;

import cl.karubag.usuario.dto.ExistsResponse;
import cl.karubag.usuario.dto.UsuarioCreateRequest;
import cl.karubag.usuario.dto.UsuarioResponse;
import cl.karubag.usuario.service.UsuarioService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    /**
     * Crea un nuevo usuario.
     * POST /api/v1/usuarios
     *
     * @return 201 Created + el usuario creado
     *         409 Conflict si el email ya existe
     */
    @PostMapping
    public ResponseEntity<UsuarioResponse> crear(@RequestBody UsuarioCreateRequest request) {
        UsuarioResponse creado = usuarioService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    /**
     * Lista todos los usuarios.
     * GET /api/v1/usuarios
     *
     * @return 200 OK + lista (puede ser vacía)
     */
    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> listarTodos() {
        List<UsuarioResponse> usuarios = usuarioService.listarTodos();
        return ResponseEntity.ok(usuarios);
    }

    /**
     * Obtiene un usuario por su id.
     * GET /api/v1/usuarios/{id}
     *
     * @return 200 OK + usuario
     *         404 Not Found si no existe
     */
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> obtenerPorId(@PathVariable Long id) {
        UsuarioResponse usuario = usuarioService.obtenerPorId(id);
        return ResponseEntity.ok(usuario);
    }

    /**
     * Indica si existe un usuario con el id dado.
     * GET /api/v1/usuarios/{id}/exists
     *
     * Endpoint pensado para que OTROS microservicios validen
     * la existencia de un usuario antes de crear datos relacionados
     * (por ejemplo, cliente-servicio antes de crear un Cliente).
     *
     * @return 200 OK + { "exists": true/false }
     */
    @GetMapping("/{id}/exists")
    public ResponseEntity<ExistsResponse> existePorId(@PathVariable Long id) {
        boolean existe = usuarioService.existePorId(id);
        return ResponseEntity.ok(new ExistsResponse(existe));
    }

    /**
     * Actualiza los datos modificables de un usuario.
     * PUT /api/v1/usuarios/{id}
     *
     * @return 200 OK + usuario actualizado
     *         404 Not Found si no existe
     */
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponse> actualizar(
            @PathVariable Long id,
            @RequestBody UsuarioCreateRequest request) {

        UsuarioResponse actualizado = usuarioService.actualizar(id, request);
        return ResponseEntity.ok(actualizado);
    }

    /**
     * Elimina un usuario por su id.
     * DELETE /api/v1/usuarios/{id}
     *
     * @return 204 No Content (eliminación exitosa, sin body de respuesta)
     *         404 Not Found si no existe
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        usuarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
