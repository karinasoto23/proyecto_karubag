package cl.karubag.usuario.repository;

import cl.karubag.usuario.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository para acceder a la tabla usuario en la BD.
 * 
 * Hereda de JpaRepository, que proporciona automáticamente:
 *  - save(entity), findById(id), findAll(), deleteById(id), existsById(id), etc.
 * 
 * Spring Data JPA implementa esta interfaz en tiempo de ejecución.
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /**
     * Busca un usuario por su email.
     * Spring Data JPA genera la consulta SQL automáticamente desde el nombre.
     */
    Optional<Usuario> findByEmail(String email);

    /**
     * Verifica si existe un usuario con el email dado.
     * Útil para validar unicidad antes de crear.
     */
    boolean existsByEmail(String email);
}
