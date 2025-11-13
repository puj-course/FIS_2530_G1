package bioprint.modulousuarios;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PuntajeRepository extends JpaRepository<Puntaje, Long> {
    List<Puntaje> findByUsuarioNombre(String nombre);
}
