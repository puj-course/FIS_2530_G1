package bioprint.ModuloUsuarios;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class LoginControllerTest {

    private LoginController controller;
    private FakeUsuarioRepository fakeRepo;
    private UsuarioService usuarioService;
    private Notificador notificador;

    // --- Simulación simple del repositorio sin base de datos ---
    static class FakeUsuarioRepository implements UsuarioRepository {
        private final List<Usuario> usuarios = new ArrayList<>();

        @Override
        public List<Usuario> findAll() {
            return new ArrayList<>(usuarios);
        }

        @Override
        public Usuario save(Usuario u) {
            usuarios.removeIf(x -> x.getNombre().equals(u.getNombre()));
            usuarios.add(u);
            return u;
        }

        @Override
        public Usuario findByNombre(String nombre) {
            return usuarios.stream()
                    .filter(u -> u.getNombre().equals(nombre))
                    .findFirst()
                    .orElse(null);
        }

        @Override
        public Usuario findByNombreAndContrasena(String nombre, String contrasena) {
            return usuarios.stream()
                    .filter(u -> u.getNombre().equals(nombre)
                            && u.getContrasena().equals(contrasena))
                    .findFirst()
                    .orElse(null);
        }
    }

    @BeforeEach
    void setUp() throws Exception {
        // Crear el repositorio falso y el servicio real
        fakeRepo = new FakeUsuarioRepository();
        usuarioService = new UsuarioService(fakeRepo);
        notificador = new Notificador();

        // Crear el LoginController e inyectar dependencias privadas
        controller = new LoginController();
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




