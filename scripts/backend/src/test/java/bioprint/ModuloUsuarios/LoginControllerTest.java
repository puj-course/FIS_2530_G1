package bioprint.ModuloUsuarios;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class LoginControllerTest {

    private LoginController controller;
    private FakeUsuarioRepository fakeRepo;
    private UsuarioService usuarioService;
    private Notificador notificador;

    // --- Repositorio falso que implementa la interfaz real ---
    static class FakeUsuarioRepository implements UsuarioRepository {
        private final List<Usuario> usuarios = new ArrayList<>();

        @Override
        public List<Usuario> findAll() {
            return new ArrayList<>(usuarios);
        }

        @Override
        public <S extends Usuario> S save(S u) {
            usuarios.removeIf(x -> x.getNombre().equals(u.getNombre()));
            usuarios.add(u);
            return u;
        }

        public Usuario findByNombre(String nombre) {
            return usuarios.stream()
                    .filter(u -> u.getNombre().equals(nombre))
                    .findFirst()
                    .orElse(null);
        }

        public Usuario findByNombreAndContrasena(String nombre, String contrasena) {
            return usuarios.stream()
                    .filter(u -> u.getNombre().equals(nombre)
                            && u.getContrasena().equals(contrasena))
                    .findFirst()
                    .orElse(null);
        }

        // --- Métodos vacíos obligatorios por JpaRepository ---
        @Override public Optional<Usuario> findById(Long id) { return Optional.empty(); }
        @Override public boolean existsById(Long id) { return false; }
        @Override public long count() { return usuarios.size(); }
        @Override public void deleteById(Long id) {}
        @Override public void delete(Usuario entity) {}
        @Override public void deleteAll(Iterable<? extends Usuario> entities) {}
        @Override public void deleteAll() {}
        @Override public <S extends Usuario> List<S> saveAll(Iterable<S> entities) { return null; }
        @Override public List<Usuario> findAllById(Iterable<Long> ids) { return null; }
        @Override public void flush() {}
        @Override public <S extends Usuario> S saveAndFlush(S entity) { return null; }
        @Override public void deleteAllInBatch() {}
        @Override public void deleteAllInBatch(Iterable<Usuario> entities) {}
        @Override public Usuario getOne(Long aLong) { return null; }
        @Override public Usuario getById(Long aLong) { return null; }
    }

    @BeforeEach
    void setUp() throws Exception {
        fakeRepo = new FakeUsuarioRepository();
        usuarioService = new UsuarioService(fakeRepo);
        notificador = new Notificador();

        controller = new LoginController();

        // Inyección manual de dependencias privadas
        Field usuarioField = LoginController.class.getDeclaredField("usuarioService");
        usuarioField.setAccessible(true);
        usuarioField.set(controller, usuarioService);

        Field botField = LoginController.class.getDeclaredField("bot");
        botField.setAccessible(true);
        botField.set(controller, notificador);
    }

    @Test
    void testRegistrarUsuarioNuevo() {
        Usuario nuevo = new Usuario("juan", "1234");
        usuarioService.guardar(nuevo);

        assertTrue(usuarioService.usuarioExiste("juan"),
                "El usuario recién guardado debe existir");
    }

    @Test
    void testUsuarioDuplicado() {
        Usuario u1 = new Usuario("ana", "123");
        usuarioService.guardar(u1);
        usuarioService.guardar(new Usuario("ana", "xyz")); // regraba

        assertEquals(1, fakeRepo.findAll().size(),
                "No debe duplicar usuarios con el mismo nombre");
    }

    @Test
    void testValidarUsuarioCorrecto() {
        fakeRepo.save(new Usuario("pepe", "abcd"));
        assertTrue(usuarioService.validarUsuario("pepe", "abcd"),
                "Debe validar correctamente credenciales válidas");
    }

    @Test
    void testValidarUsuarioIncorrecto() {
        fakeRepo.save(new Usuario("luis", "9999"));
        assertFalse(usuarioService.validarUsuario("luis", "0000"),
                "No debe validar credenciales incorrectas");
    }
}






