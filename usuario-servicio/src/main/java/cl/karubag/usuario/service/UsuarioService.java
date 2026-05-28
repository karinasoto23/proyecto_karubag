package cl.karubag.usuario.service;

import cl.karubag.usuario.dto.UsuarioCreateRequest;
import cl.karubag.usuario.dto.UsuarioResponse;
import cl.karubag.usuario.exception.DuplicateResourceException;
import cl.karubag.usuario.exception.ResourceNotFoundException;
import cl.karubag.usuario.mapper.UsuarioMapper;
import cl.karubag.usuario.model.Usuario;
import cl.karubag.usuario.repository.UsuarioRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Servicio que contiene la lógica de negocio para Usuario.
 *
 * Responsabilidades:
 * - Coordinar Repository y Mapper.
 * - Validar reglas de negocio (ej: email único).
 * - Lanzar excepciones de dominio cuando algo no cumple las reglas.
 * - Gestionar transacciones con @Transactional.
 *
 * Inyección por constructor: no se usa @Autowired en campos
 * (decisión moderna que favorece la inmutabilidad y facilita los tests).
 */
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    public UsuarioService(UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
    }

    /**
     * Crea un nuevo usuario.
     * Valida que el email no esté registrado antes de persistir.
     *
     * @throws DuplicateResourceException si el email ya existe (HTTP 409)
     */
    @Transactional
    public UsuarioResponse crear(UsuarioCreateRequest request) {
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException(
                    "Ya existe un usuario con el email: " + request.getEmail()
            );
        }

        Usuario nuevoUsuario = usuarioMapper.aEntidad(request);
        Usuario guardado = usuarioRepository.save(nuevoUsuario);
        return usuarioMapper.aResponse(guardado);
    }

    /**
     * Obtiene un usuario por su id.
     *
     * @throws ResourceNotFoundException si no existe (HTTP 404)
     */
    @Transactional(readOnly = true)
    public UsuarioResponse obtenerPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Usuario con id " + id + " no existe"
                ));
        return usuarioMapper.aResponse(usuario);
    }

    /**
     * Lista todos los usuarios registrados.
     */
    @Transactional(readOnly = true)
    public List<UsuarioResponse> listarTodos() {
        return usuarioRepository.findAll()
                .stream()
                .map(usuarioMapper::aResponse)
                .toList();
    }

    /**
     * Indica si existe un usuario con el id dado.
     * Lo usan otros microservicios (vía WebClient) para validaciones cruzadas.
     */
    @Transactional(readOnly = true)
    public boolean existePorId(Long id) {
        return usuarioRepository.existsById(id);
    }

    /**
     * Actualiza los datos modificables de un usuario existente.
     * No se actualiza el email (es identificador) ni la password en este endpoint.
     *
     * @throws ResourceNotFoundException si el usuario no existe (HTTP 404)
     */
    @Transactional
    public UsuarioResponse actualizar(Long id, UsuarioCreateRequest request) {
        Usuario existente = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Usuario con id " + id + " no existe"
                ));

        existente.setNombreCompleto(request.getNombreCompleto());
        existente.setTelefono(request.getTelefono());
        existente.setRol(request.getRol());

        Usuario actualizado = usuarioRepository.save(existente);
        return usuarioMapper.aResponse(actualizado);
    }

    /**
     * Elimina un usuario por su id.
     *
     * @throws ResourceNotFoundException si el usuario no existe (HTTP 404)
     */
    @Transactional
    public void eliminar(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                    "Usuario con id " + id + " no existe"
            );
        }
        usuarioRepository.deleteById(id);
    }
}
