package bioprint.Frontend.InicioSesion;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

public class LoginControllerTest {

    private LoginController controller;

    @BeforeEach
    void setUp() {
        controller = new LoginController();
        controller.txtUsuario = new TextField();
        controller.txtPassword = new PasswordField();
    }

    @Test
    void testIniciarSesionCamposVacios() {
        controller.txtUsuario.setText("");
        controller.txtPassword.setText("");
        assertDoesNotThrow(() -> controller.iniciarSesion(null));
    }

    @Test
    void testIniciarSesionUsuarioSinServidor() {
        controller.txtUsuario.setText("testUser");
        controller.txtPassword.setText("1234");
        // No debe lanzar excepción incluso si no hay servidor
        assertDoesNotThrow(() -> controller.iniciarSesion(null));
    }

    @Test
    void testOnIrARegistroManejoDeError() {
        // Sin JavaFX cargado, debería lanzar alerta pero no romper
        assertDoesNotThrow(() -> controller.onIrARegistro(null));
    }

    @Test
    void testInitializeImagen() {
        // Ejecuta el método y asegura que no falle
        assertDoesNotThrow(() -> controller.initialize());
    }

    @Test
    void testMostrarAlertaReflection() throws Exception {
        Method m = LoginController.class.getDeclaredMethod("mostrarAlerta", String.class, String.class);
        m.setAccessible(true);
        assertDoesNotThrow(() -> m.invoke(controller, "Título", "Mensaje de prueba"));
    }
}
