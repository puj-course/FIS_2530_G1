package bioprint.Frontend.InicioSesion;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.lang.reflect.Field;

public class RegistroControllerTest {

    private RegistroController controller;

    @BeforeEach
    public void setUp() throws Exception {
        controller = new RegistroController();

        controller.txtNombre = new TextField();
        controller.txtEmail = new TextField();
        controller.txtContrasena = new PasswordField();
        controller.txtConfirmarContrasena = new PasswordField();
    }

    @Test
    public void testEmailValido() {
        assertTrue(controller.esEmailValido("usuario@dominio.com"));
        assertFalse(controller.esEmailValido("correo_invalido"));
    }

    @Test
    public void testContrasenaSegura() {
        assertTrue(controller.esContrasenaSegura("Abcdef1@"));
        assertFalse(controller.esContrasenaSegura("abcdefg"));
        assertFalse(controller.esContrasenaSegura("ABC12345"));
    }

    @Test
    public void testRegistrarUsuario_CamposVacios() {
        controller.txtNombre.setText("");
        controller.txtEmail.setText("");
        controller.txtContrasena.setText("");
        controller.txtConfirmarContrasena.setText("");
        controller.registrarUsuario(null);
        // No lanza excepción entonces cobertura básica
    }

    @Test
    public void testRegistrarUsuario_EmailInvalido() {
        controller.txtNombre.setText("Juan");
        controller.txtEmail.setText("correo_invalido");
        controller.txtContrasena.setText("Abcdef1@");
        controller.txtConfirmarContrasena.setText("Abcdef1@");
        controller.registrarUsuario(null);
    }

    @Test
    public void testRegistrarUsuario_ContrasenaInsegura() {
        controller.txtNombre.setText("Juan");
        controller.txtEmail.setText("juan@mail.com");
        controller.txtContrasena.setText("1234");
        controller.txtConfirmarContrasena.setText("1234");
        controller.registrarUsuario(null);
    }

    @Test
    public void testRegistrarUsuario_ConfirmacionDistinta() {
        controller.txtNombre.setText("Juan");
        controller.txtEmail.setText("juan@mail.com");
        controller.txtContrasena.setText("Abcdef1@");
        controller.txtConfirmarContrasena.setText("Abcdef2@");
        controller.registrarUsuario(null);
    }

    @Test
    public void testGuardarUsuario_ConexionFallida() {
        boolean resultado = controller.guardarUsuario("Juan", "Abcdef1@");
        // En la mayoría de entornos locales no hay servidor entocnes debe devolver false
        assertFalse(resultado);
    }

    @Test
    public void testLimpiarCampos() {
        controller.txtNombre.setText("Juan");
        controller.txtEmail.setText("juan@mail.com");
        controller.txtContrasena.setText("Abcdef1@");
        controller.txtConfirmarContrasena.setText("Abcdef1@");

        controller.limpiarCampos();

        assertTrue(controller.txtNombre.getText().isEmpty());
        assertTrue(controller.txtEmail.getText().isEmpty());
        assertTrue(controller.txtContrasena.getText().isEmpty());
        assertTrue(controller.txtConfirmarContrasena.getText().isEmpty());
    }
}
