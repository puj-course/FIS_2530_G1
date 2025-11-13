package bioprint.ModuloUsuarios;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class PuntajeService {
    private final PuntajeRepository repo;
    private final UsuarioRepository usuarioRepo;

    public PuntajeService(PuntajeRepository repo, UsuarioRepository usuarioRepo) {
        this.repo = repo;
        this.usuarioRepo = usuarioRepo;
    }

    public Puntaje registrarPuntaje(String nombreUsuario, double valor) {
        Usuario usuario = usuarioRepo.findByNombre(nombreUsuario);

        Puntaje p = new Puntaje();
        p.setUsuario(usuario);
        p.setValor(valor);
        p.setFecha(LocalDate.now());
        return repo.save(p);
    }

    public List<Puntaje> obtenerPuntajes(String nombreUsuario) {
        return repo.findByUsuarioNombre(nombreUsuario);
    }
    
    public long contarUsuariosConPuntajeMayor(String nombreUsuario) {
    // 1️⃣ Obtener el puntaje más reciente del usuario dado
    List<Puntaje> puntajesUsuario = repo.findByUsuarioNombre(nombreUsuario);
    if (puntajesUsuario.isEmpty()) {
        return 0; // el usuario no tiene puntajes
    }
    double puntajeRecienteUsuario = puntajesUsuario.stream()
            .max((p1, p2) -> p1.getFecha().compareTo(p2.getFecha()))
            .get()
            .getValor();

    // 2️⃣ Obtener todos los usuarios (o todos los puntajes más recientes por usuario)
    List<Usuario> todosUsuarios = usuarioRepo.findAll();

    // 3️⃣ Contar cuántos tienen un puntaje más reciente mayor
    long count = todosUsuarios.stream()
            .filter(u -> !u.getNombre().equals(nombreUsuario)) // excluir el usuario dado
            .map(u -> repo.findByUsuarioNombre(u.getNombre()))
            .filter(puntajes -> !puntajes.isEmpty())
            .map(puntajes -> puntajes.stream()
                    .max((p1, p2) -> p1.getFecha().compareTo(p2.getFecha()))
                    .get()
            )
            .filter(p -> p.getValor() < puntajeRecienteUsuario)
            .count();

    return count;
}
}
