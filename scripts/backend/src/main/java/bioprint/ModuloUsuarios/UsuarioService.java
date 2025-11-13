package bioprint.modulousuarios;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    private final UsuarioRepository repo;

    public UsuarioService(UsuarioRepository repo) {
        this.repo = repo;
    }

    public List<Usuario> listar() {
        return repo.findAll();
    }

    public Usuario guardar(Usuario u) {
        return repo.save(u);
    }

    public boolean usuarioExiste(String nombre){
        Usuario usuario= repo.findByNombre(nombre);
        if(usuario==null)
            return false;
        return true;
    }
    public boolean validarUsuario(String nombre, String contrasena) {
        Usuario usuario = repo.findByNombreAndContrasena(nombre, contrasena);
        if (usuario != null) {
            return true;
        } else {
            return false;
        }
    }
}
