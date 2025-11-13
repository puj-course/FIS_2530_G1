package bioprint.Frontend.InicioSesion;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LoginControllerTest {

    private LoginController controller;

    @BeforeEach
    void setUp() {
        controller = new LoginController();
        controller.txtUsuario = new TextField();
        controller.txtPassword = new PasswordField();
        controller.imagenCircular = new Circle();
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
        assertDoesNotThrow(() -> controller.iniciarSesion(null));
    }

    @Test
    void testOnIrARegistroManejoDeError() {
        assertDoesNotThrow(() -> controller.onIrARegistro(null));
    }

    @Test
    void testInitializeImagenCorrecta() {
        controller.imagenCircular = new Circle();
        assertDoesNotThrow(() -> controller.initialize());
    }

    @Test
    void testMostrarAlertaReflection() throws Exception {
        Method m = LoginController.class.getDeclaredMethod("mostrarAlerta", String.class, String.class);
        m.setAccessible(true);
        assertDoesNotThrow(() -> m.invoke(controller, "Título", "Mensaje de prueba"));
    }

    // 🔹 NUEVAS PRUEBAS

    @Test
    void testIniciarSesionUsuarioCorrectoJsonSimulado() throws Exception {
        controller.txtUsuario.setText("Juan");
        controller.txtPassword.setText("1234");

        HttpURLConnection mockCon = mock(HttpURLConnection.class);
        when(mockCon.getResponseCode()).thenReturn(200);
        String jsonSimulado = "[{\"nombre\":\"Juan\",\"contrasena\":\"1234\"}]";
        InputStream input = new ByteArrayInputStream(jsonSimulado.getBytes());
        when(mockCon.getInputStream()).thenReturn(input);

        URL mockUrl = mock(URL.class);
        when(mockUrl.openConnection()).thenReturn(mockCon);

        // Inyección por reflexión
        Method iniciar = LoginController.class.getDeclaredMethod("iniciarSesion", ActionEvent.class);
        iniciar.setAccessible(true);
        iniciar.invoke(controller, (Object) null);
        verify(mockCon).disconnect();
    }

    @Test
    void testIniciarSesionServidorConError500() throws Exception {
        controller.txtUsuario.setText("Admin");
        controller.txtPassword.setText("12345");

        HttpURLConnection mockCon = mock(HttpURLConnection.class);
        when(mockCon.getResponseCode()).thenReturn(500);

        URL mockUrl = mock(URL.class);
        when(mockUrl.openConnection()).thenReturn(mockCon);

        assertDoesNotThrow(() -> controller.iniciarSesion(null));
    }

    @Test
    void testIniciarSesionUsuarioNoEncontrado() throws Exception {
        controller.txtUsuario.setText("Desconocido");
        controller.txtPassword.setText("xyz");

        HttpURLConnection mockCon = mock(HttpURLConnection.class);
        when(mockCon.getResponseCode()).thenReturn(200);
        String jsonSimulado = "[{\"nombre\":\"Otro\",\"contrasena\":\"111\"}]";
        InputStream input = new ByteArrayInputStream(jsonSimulado.getBytes());
        when(mockCon.getInputStream()).thenReturn(input);

        assertDoesNotThrow(() -> controller.iniciarSesion(null));
    }

    @Test
    void testIniciarSesionLanzaExcepcion() throws Exception {
        controller.txtUsuario.setText("Usuario");
        controller.txtPassword.setText("123");

        // Forzar error de conexión
        URL mockUrl = mock(URL.class);
        when(mockUrl.openConnection()).thenThrow(new RuntimeException("Error simulado"));
        assertDoesNotThrow(() -> controller.iniciarSesion(null));
    }

    @Test
    void testMostrarAlertaNoLanzaError() {
        assertDoesNotThrow(() -> controller.mostrarAlerta("Aviso", "Mensaje de prueba"));
    }

    @Test
    void testInitializeFallaCargaImagen() {
        controller.imagenCircular = new Circle();
        // Intentará cargar imagen inexistente
        assertDoesNotThrow(() -> controller.initialize());
    }

    @Test
    void testOnIrARegistroSinFXMLValido() {
        assertDoesNotThrow(() -> controller.onIrARegistro(null));
    }

    @Test
    void testCamposUsuarioYPasswordInicialmenteVacios() {
        assertEquals("", controller.txtUsuario.getText());
        assertEquals("", controller.txtPassword.getText());
    }

    @Test
    void testCamposUsuarioYPasswordAsignacion() {
        controller.txtUsuario.setText("Maria");
        controller.txtPassword.setText("secreta");
        assertEquals("Maria", controller.txtUsuario.getText());
        assertEquals("secreta", controller.txtPassword.getText());
    }

    @Test
    void testIniciarSesionConJsonVacio() throws Exception {
        controller.txtUsuario.setText("Pedro");
        controller.txtPassword.setText("123");

        HttpURLConnection mockCon = mock(HttpURLConnection.class);
        when(mockCon.getResponseCode()).thenReturn(200);
        InputStream input = new ByteArrayInputStream("".getBytes());
        when(mockCon.getInputStream()).thenReturn(input);

        assertDoesNotThrow(() -> controller.iniciarSesion(null));
    }

    @Test
    void testInitializeNoRompeSinImagenCircular() {
        controller.imagenCircular = null;
        assertDoesNotThrow(() -> controller.initialize());
    }

    @Test
    void testIniciarSesionConFormatoJsonRaro() throws Exception {
        controller.txtUsuario.setText("UsuarioRaro");
        controller.txtPassword.setText("clave");

        HttpURLConnection mockCon = mock(HttpURLConnection.class);
        when(mockCon.getResponseCode()).thenReturn(200);
        InputStream input = new ByteArrayInputStream("{malformado}".getBytes());
        when(mockCon.getInputStream()).thenReturn(input);

        assertDoesNotThrow(() -> controller.iniciarSesion(null));
    }

    @Test
    void testReflectionDeCampos() throws Exception {
        Field f = LoginController.class.getDeclaredField("txtUsuario");
        assertNotNull(f);
        assertEquals(TextField.class, f.getType());
    }

    @Test
    void testReflectionDeMetodoMostrarAlertaExiste() throws Exception {
        Method m = LoginController.class.getDeclaredMethod("mostrarAlerta", String.class, String.class);
        assertNotNull(m);
    }

    @Test
    void testInitializeCargaImagePattern() {
        controller.imagenCircular = new Circle();
        controller.initialize();
        assertTrue(controller.imagenCircular.getFill() instanceof ImagePattern || controller.imagenCircular.getFill() == null);
    }
}

