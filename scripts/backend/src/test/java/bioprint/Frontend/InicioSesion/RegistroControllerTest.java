package bioprint.Frontend.InicioSesion;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

public class RegistroControllerTest {

    private RegistroController controller;

    @BeforeEach
    void setUp() {
        controller = new RegistroController();
        controller.txtNombre = new TextField();
        controller.txtEmail = new TextField();
        controller.txtContrasena = new PasswordField();
        controller.txtConfirmarContrasena = new PasswordField();
    }

    @Test
    void testEmailValido() throws Exception {
        Method m = RegistroController.class.getDeclaredMethod("esEmailValido", String.class);
        m.setAccessible(true);

        assertTrue((boolean) m.invoke(controller, "usuario@test.com"));
        assertFalse((boolean) m.invoke(controller, "usuario@@test"));
        assertFalse((boolean) m.invoke(controller, "usuario.com"));
    }

    @Test
    void testContrasenaSegura() throws Exception {
        Method m = RegistroController.class.getDeclaredMethod("esContrasenaSegura", String.class);
        m.setAccessible(true);

        assertTrue((boolean) m.invoke(controller, "Contra1$A"));
        assertFalse((boolean) m.invoke(controller, "simple"));
        assertFalse((boolean) m.invoke(controller, "NoNumber!"));
        assertFalse((boolean) m.invoke(controller, "12345678"));
    }

    @Test
    void testGuardarUsuarioFalse() throws Exception {
        Method m = RegistroController.class.getDeclaredMethod("guardarUsuario", String.class, String.class);
        m.setAccessible(true);
        boolean result = (boolean) m.invoke(controller, "test", "1234");
        assertFalse(result);
    }

    @Test
    void testRegistrarUsuarioCamposVacios() {
        controller.txtNombre.setText("");
        controller.txtEmail.setText("");
        controller.txtContrasena.setText("");
        controller.txtConfirmarContrasena.setText("");
        assertDoesNotThrow(() -> controller.registrarUsuario(null));
    }

    @Test
    void testRegistrarUsuarioDatosInvalidos() {
        controller.txtNombre.setText("Juan");
        controller.txtEmail.setText("correoInvalido");
        controller.txtContrasena.setText("123456");
        controller.txtConfirmarContrasena.setText("123456");
        assertDoesNotThrow(() -> controller.registrarUsuario(null));
    }

    @Test
    void testRegistrarUsuarioCorrecto() {
        controller.txtNombre.setText("Pedro");
        controller.txtEmail.setText("pedro@test.com");
        controller.txtContrasena.setText("Contra1$");
        controller.txtConfirmarContrasena.setText("Contra1$");
        assertDoesNotThrow(() -> controller.registrarUsuario(null));
    }

    @Test
    void testInitializeMascota() {
        assertDoesNotThrow(() -> controller.initialize());
    }
}
