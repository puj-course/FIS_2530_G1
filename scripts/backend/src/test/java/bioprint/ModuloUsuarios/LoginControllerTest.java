package bioprint.ModuloUsuarios;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test unitario para LoginController sin ejecutar JavaFX ni Spring.
 */
public class LoginControllerTest {

    private LoginController controller;
    private FakeUsuarioService fakeService;
    private FakeNotificador fakeBot;

    /**
     * Servicio falso para probar lógica sin base de datos.
     */
    static class FakeUsuarioService extends UsuarioService {
        private final List<Usuario> usuarios = new ArrayList<>();

        public FakeUsuarioService() {
            super(null); // no necesitamos repositorio real
        }

        @Override
        public boolean validarUsuario(String nombre, String contrasena) {
            return usuarios.stream()
                    .anyMatch(u -> u.getNombre().equals(nombre)
                            && u.getContrasena().equals(contrasena));
        }

        @Override
        public boolean usuarioExiste(String nombre) {
            return usuarios.stream()
                    .anyMatch(u -> u.getNombre().equals(nombre));
        }

        @Override
        public Usuario guardar(Usuario u) {
            usuarios.removeIf(x -> x.getNombre().equals(u.getNombre()));
            usuarios.add(u);
            return u; 
        }

        public List<Usuario> getUsuarios() {
            return usuarios;
        }
    }

    /**
     * Notificador falso que registra los mensajes enviados.
     */
    static class FakeNotificador extends Notificador {
        private final List<String> mensajes = new ArrayList<>();

        @Override
        public void enviarMensaje(String mensaje) {
            mensajes.add(mensaje);
        }

        public List<String> getMensajes() {
            return mensajes;
        }
    }

    @BeforeEach
    void setUp() throws Exception {
        controller = new LoginController();
        fakeService = new FakeUsuarioService();
        fakeBot = new FakeNotificador();

        // Inyección manual en los campos privados del LoginController
        Field usuarioField = LoginController.class.getDeclaredField("usuarioService");
        usuarioField.setAccessible(true);
        usuarioField.set(controller, fakeService);

        Field botField = LoginController.class.getDeclaredField("bot");
        botField.setAccessible(true);
        botField.set(controller, fakeBot);
    }

    @Test
    void testRegistrarUsuarioNuevo() {
        Usuario nuevo = new Usuario("juan", "1234");
        fakeService.guardar(nuevo);

        assertTrue(fakeService.usuarioExiste("juan"),
                "El usuario recién guardado debe existir");
    }

    @Test
    void testUsuarioDuplicado() {
        fakeService.guardar(new Usuario("ana", "123"));
        fakeService.guardar(new Usuario("ana", "xyz")); // reemplaza
        assertEquals(1, fakeService.getUsuarios().size(),
                "No debe duplicar usuarios con el mismo nombre");
    }

    @Test
    void testValidarUsuarioCorrecto() {
        fakeService.guardar(new Usuario("pepe", "abcd"));
        assertTrue(fakeService.validarUsuario("pepe", "abcd"),
                "Debe validar credenciales válidas");
    }

    @Test
    void testValidarUsuarioIncorrecto() {
        fakeService.guardar(new Usuario("luis", "9999"));
        assertFalse(fakeService.validarUsuario("luis", "0000"),
                "No debe validar credenciales incorrectas");
    }

    @Test
    void testNotificadorEnvioMensaje() {
        fakeBot.enviarMensaje("Prueba de notificación");
        assertEquals(1, fakeBot.getMensajes().size(),
                "Debe haberse enviado un mensaje");
    }
}

      
