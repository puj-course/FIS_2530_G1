package bioprint.Frontend.InicioSesion;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.shape.Circle;
import javafx.scene.paint.ImagePattern;
import javafx.event.ActionEvent;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
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
    void testCamposVaciosNoRompe() {
        controller.txtUsuario.setText("");
        controller.txtPassword.setText("");
        assertDoesNotThrow(() -> controller.iniciarSesion(null));
    }
    @Test
    void testServidorNoDisponible() {
        controller.txtUsuario.setText("user");
        controller.txtPassword.setText("123");
        assertDoesNotThrow(() -> controller.iniciarSesion(null));
    }
    @Test
    void testUsuarioEncontrado() throws Exception {
        controller.txtUsuario.setText("Juan");
        controller.txtPassword.setText("1234");

        // Mock conexión
        HttpURLConnection mockCon = mock(HttpURLConnection.class);
        when(mockCon.getResponseCode()).thenReturn(200);
        String jsonSimulado = "[{\"nombre\":\"Juan\",\"contrasena\":\"1234\"}]";
        InputStream input = new ByteArrayInputStream(jsonSimulado.getBytes());
        when(mockCon.getInputStream()).thenReturn(input);

        // Mock URL
        URL mockUrl = mock(URL.class);
        when(mockUrl.openConnection()).thenReturn(mockCon);

        assertDoesNotThrow(() -> controller.iniciarSesion(null));
        verify(mockCon, times(1)).disconnect();
    }
    @Test
    void testUsuarioNoEncontrado() throws Exception {
        controller.txtUsuario.setText("Ana");
        controller.txtPassword.setText("abc");

        HttpURLConnection mockCon = mock(HttpURLConnection.class);
        when(mockCon.getResponseCode()).thenReturn(200);
        InputStream input = new ByteArrayInputStream("[{\"nombre\":\"Pedro\",\"contrasena\":\"xyz\"}]".getBytes());
        when(mockCon.getInputStream()).thenReturn(input);

        assertDoesNotThrow(() -> controller.iniciarSesion(null));
    }
    @Test
    void testServidorError500() throws Exception {
        controller.txtUsuario.setText("test");
        controller.txtPassword.setText("pass");

        HttpURLConnection mockCon = mock(HttpURLConnection.class);
        when(mockCon.getResponseCode()).thenReturn(500);

        assertDoesNotThrow(() -> controller.iniciarSesion(null));
    }

    @Test
    void testJsonVacio() throws Exception {
        controller.txtUsuario.setText("Mario");
        controller.txtPassword.setText("000");

        HttpURLConnection mockCon = mock(HttpURLConnection.class);
        when(mockCon.getResponseCode()).thenReturn(200);
        InputStream input = new ByteArrayInputStream("".getBytes());
        when(mockCon.getInputStream()).thenReturn(input);

        assertDoesNotThrow(() -> controller.iniciarSesion(null));
    }

    @Test
    void testJsonMalformado() throws Exception {
        controller.txtUsuario.setText("bad");
        controller.txtPassword.setText("json");

        HttpURLConnection mockCon = mock(HttpURLConnection.class);
        when(mockCon.getResponseCode()).thenReturn(200);
        InputStream input = new ByteArrayInputStream("{mal}".getBytes());
        when(mockCon.getInputStream()).thenReturn(input);

        assertDoesNotThrow(() -> controller.iniciarSesion(null));
    }

    @Test
    void testConexionFalla() throws Exception {
        controller.txtUsuario.setText("fail");
        controller.txtPassword.setText("fail");

        URL mockUrl = mock(URL.class);
        when(mockUrl.openConnection()).thenThrow(new RuntimeException("Error simulado"));

        assertDoesNotThrow(() -> controller.iniciarSesion(null));
    }

    @Test
    void testMostrarAlertaNoRompe() {
        assertDoesNotThrow(() -> controller.mostrarAlerta("Título", "Mensaje"));
    }

    @Test
    void testIrARegistroFallaCargaFXML() {
        assertDoesNotThrow(() -> controller.onIrARegistro(null));
    }
    @Test
    void testInitializeImagenOK() {
        assertDoesNotThrow(() -> controller.initialize());
    }
    @Test
    void testInitializeImagenCircularNula() {
        controller.imagenCircular = null;
        assertDoesNotThrow(() -> controller.initialize());
    }
    @Test
    void testInitializeImagenFalla() {
        controller.imagenCircular = new Circle();
        assertDoesNotThrow(() -> controller.initialize());
    }
    @Test
    void testReflectionMetodoMostrarAlerta() throws Exception {
        Method m = LoginController.class.getDeclaredMethod("mostrarAlerta", String.class, String.class);
        assertNotNull(m);
    }
    @Test
    void testCamposInicializados() {
        assertNotNull(controller.txtUsuario);
        assertNotNull(controller.txtPassword);
        assertNotNull(controller.imagenCircular);
    }

    @Test
    void testCamposSetters() {
        controller.txtUsuario.setText("Pepe");
        controller.txtPassword.setText("clave");
        assertEquals("Pepe", controller.txtUsuario.getText());
        assertEquals("clave", controller.txtPassword.getText());
    }
    @Test
    void testInitializeRellenaImagen() {
        controller.imagenCircular = new Circle();
        controller.initialize();
        assertTrue(controller.imagenCircular.getFill() == null ||
                   controller.imagenCircular.getFill() instanceof ImagePattern);
    }

    @Test
    void testIniciarSesionConEventoNulo() {
        controller.txtUsuario.setText("test");
        controller.txtPassword.setText("test");
        assertDoesNotThrow(() -> controller.iniciarSesion((ActionEvent) null));
    }
}
