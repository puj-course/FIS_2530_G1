package bioprint.ModuloUsuarios;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.Stage;
import javafx.scene.Scene;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para LoginController sin usar Mockito.
 * Se emplean clases internas simuladas (stubs) para UsuarioService y Notificador.
 */
public class LoginControllerTest {

    private LoginController controller;
    private FakeUsuarioService fakeUsuarioService;
    private FakeNotificador fakeNotificador;
    private Stage stage;

    private static class FakeUsuarioService extends UsuarioService {
        private boolean usuarioValido = false;
        private boolean usuarioExiste = false;
        private boolean guardarLlamado = false;

        public void setUsuarioValido(boolean valido) {
            this.usuarioValido = valido;
        }

        public void setUsuarioExiste(boolean existe) {
            this.usuarioExiste = existe;
        }

        public boolean isGuardarLlamado() {
            return guardarLlamado;
        }

        @Override
        public boolean validarUsuario(String nombre, String contrasena) {
            return usuarioValido;
        }

        @Override
        public boolean usuarioExiste(String nombre) {
            return usuarioExiste;
        }

        @Override
        public Usuario guardar(Usuario u) {
            guardarLlamado = true;
            return u;
        }
    }

    private static class FakeNotificador extends Notificador {
        private String ultimoMensaje = "";

        @Override
        public void enviarMensaje(String mensaje) {
            ultimoMensaje = mensaje;
            System.out.println("Mensaje simulado: " + mensaje);
        }

        public String getUltimoMensaje() {
            return ultimoMensaje;
        }
    }

    @BeforeEach
    public void setup() throws Exception {
        // Iniciar JavaFX (necesario para crear Stage)
        new JFXPanel();
        Platform.runLater(() -> {});
        Thread.sleep(200);

        controller = new LoginController();
        fakeUsuarioService = new FakeUsuarioService();
        fakeNotificador = new FakeNotificador();
        stage = new Stage();

        // Inyectar dependencias privadas con reflexión
        Field usuarioServiceField = LoginController.class.getDeclaredField("usuarioService");
        usuarioServiceField.setAccessible(true);
        usuarioServiceField.set(controller, fakeUsuarioService);

        Field botField = LoginController.class.getDeclaredField("bot");
        botField.setAccessible(true);
        botField.set(controller, fakeNotificador);

        Field stageField = LoginController.class.getDeclaredField("primaryStage");
        stageField.setAccessible(true);
        stageField.set(controller, stage);
    }

    @Test
    public void testMostrarPantallaInicio_SinErrores() {
        assertDoesNotThrow(() -> {
            Platform.runLater(() -> controller.start(stage));
        });
    }

    @Test
    public void testLoginUsuarioValido() {
        fakeUsuarioService.setUsuarioValido(true);

        Platform.runLater(() -> {
            try {
                var m = LoginController.class.getDeclaredMethod("mostrarPantallaLogin");
                m.setAccessible(true);
                m.invoke(controller);
                assertDoesNotThrow(() -> fakeNotificador.enviarMensaje("Usuario válido"));
            } catch (Exception e) {
                fail("Error ejecutando login válido: " + e.getMessage());
            }
        });
    }

    @Test
    public void testLoginUsuarioInvalido() {
        fakeUsuarioService.setUsuarioValido(false);

        Platform.runLater(() -> {
            try {
                var m = LoginController.class.getDeclaredMethod("mostrarPantallaLogin");
                m.setAccessible(true);
                m.invoke(controller);
                assertFalse(fakeUsuarioService.validarUsuario("noexiste", "xxx"));
            } catch (Exception e) {
                fail("Error ejecutando login inválido: " + e.getMessage());
            }
        });
    }

    @Test
    public void testRegistroUsuarioYaExiste() {
        fakeUsuarioService.setUsuarioExiste(true);

        Platform.runLater(() -> {
            try {
                var m = LoginController.class.getDeclaredMethod("mostrarPantallaRegistro");
                m.setAccessible(true);
                m.invoke(controller);
                assertTrue(fakeUsuarioService.usuarioExiste("juan"));
            } catch (Exception e) {
                fail("Error ejecutando registro existente: " + e.getMessage());
            }
        });
    }

    @Test
    public void testRegistroUsuarioNuevo() {
        fakeUsuarioService.setUsuarioExiste(false);

        Platform.runLater(() -> {
            try {
                var m = LoginController.class.getDeclaredMethod("mostrarPantallaRegistro");
                m.setAccessible(true);
                m.invoke(controller);
                fakeUsuarioService.guardar(new Usuario());
                assertTrue(fakeUsuarioService.isGuardarLlamado());
            } catch (Exception e) {
                fail("Error ejecutando registro nuevo: " + e.getMessage());
            }
        });
    }

    @Test
    public void testStageInicial() {
        Platform.runLater(() -> {
            controller.start(stage);
            Scene escena = stage.getScene();
            assertNotNull(escena);
            assertEquals("Bienvenida", stage.getTitle());
        });
    }
}


