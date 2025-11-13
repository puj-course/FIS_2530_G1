package bioprint.Frontend.InicioSesion;

import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
    void testLimpiarCampos() {
        controller.txtNombre.setText("Ana");
        controller.txtEmail.setText("ana@test.com");
        controller.txtContrasena.setText("Contra1$");
        controller.txtConfirmarContrasena.setText("Contra1$");

        controller.limpiarCampos();

        assertEquals("", controller.txtNombre.getText());
        assertEquals("", controller.txtEmail.getText());
        assertEquals("", controller.txtContrasena.getText());
        assertEquals("", controller.txtConfirmarContrasena.getText());
    }

    @Test
    void testMostrarAlertaNoLanzaExcepcion() {
        assertDoesNotThrow(() -> controller.mostrarAlerta("Título", "Mensaje de prueba"));
    }

    @Test
    void testInitializeCargaImagen() {
        controller.imagenMascota = new Circle();
        assertDoesNotThrow(controller::initialize);
    }

    @Test
    void testGuardarUsuarioConExcepcion() throws Exception {
        Method m = RegistroController.class.getDeclaredMethod("guardarUsuario", String.class, String.class);
        m.setAccessible(true);
        boolean result = (boolean) m.invoke(controller, "nombre", "pass");
        assertFalse(result);
    }

    @Test
    void testEsEmailValidoCasosLimite() throws Exception {
        Method m = RegistroController.class.getDeclaredMethod("esEmailValido", String.class);
        m.setAccessible(true);

        assertTrue((boolean) m.invoke(controller, "a@b.co"));
        assertFalse((boolean) m.invoke(controller, "@sinusuario.com"));
        assertFalse((boolean) m.invoke(controller, "usuario@.com"));
        assertFalse((boolean) m.invoke(controller, "usuario@test."));
    }

    @Test
    void testEsContrasenaSeguraCaracteresEspeciales() throws Exception {
        Method m = RegistroController.class.getDeclaredMethod("esContrasenaSegura", String.class);
        m.setAccessible(true);

        assertTrue((boolean) m.invoke(controller, "Contra#2025"));
        assertFalse((boolean) m.invoke(controller, "contrasena"));
        assertFalse((boolean) m.invoke(controller, "CONTRASENA1"));
    }

    @Test
    void testRegistrarUsuarioContrasenasDistintas() {
        controller.txtNombre.setText("Sofia");
        controller.txtEmail.setText("sofia@test.com");
        controller.txtContrasena.setText("Contra1$");
        controller.txtConfirmarContrasena.setText("Diferente1$");

        assertDoesNotThrow(() -> controller.registrarUsuario(null));
    }

    @Test
    void testOnVolverSinExcepcion() throws IOException {
        // Simulamos un nodo con Mockito
        Node node = Mockito.mock(Node.class);
        Scene escena = new Scene(new Group());
        when(node.getScene()).thenReturn(escena);

        ActionEvent event = new ActionEvent(node, null);
        assertDoesNotThrow(() -> controller.onVolver(event));
    }

    @Test
    void testRegistrarUsuarioServidorCaido() {
        controller.txtNombre.setText("Lina");
        controller.txtEmail.setText("lina@test.com");
        controller.txtContrasena.setText("Contra1$A");
        controller.txtConfirmarContrasena.setText("Contra1$A");

        assertDoesNotThrow(() -> controller.registrarUsuario(null));
    }

    @Test
    void testInitializeMascota() {
        controller.imagenMascota = new Circle();
        assertDoesNotThrow(() -> controller.initialize());
    }
}



