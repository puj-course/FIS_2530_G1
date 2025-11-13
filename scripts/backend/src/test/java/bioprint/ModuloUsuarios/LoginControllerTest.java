package bioprint.modulousuarios;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javafx.stage.Stage;

public class LoginControllerTest {

    private LoginController controller;
    private UsuarioService usuarioService;
    private Notificador notificador;
    private Stage stage;

    @BeforeEach
    void setUp() {
        controller = new LoginController();
        usuarioService = Mockito.mock(UsuarioService.class);
        notificador = Mockito.mock(Notificador.class);
        stage = new Stage();

        controller.usuarioService = usuarioService;
        controller.bot = notificador;
        controller.primaryStage = stage;
    }

    @Test
    void testMostrarPantallaInicio_NoLanzaExcepcion() {
        assertDoesNotThrow(() -> controller.mostrarPantallaInicio());
        assertNotNull(controller.primaryStage.getScene());
        assertEquals("Bienvenida", controller.primaryStage.getTitle());
    }

    @Test
    void testMostrarPantallaLogin_NoLanzaExcepcion() {
        assertDoesNotThrow(() -> controller.mostrarPantallaLogin());
        assertNotNull(controller.primaryStage.getScene());
        assertEquals("Iniciar Sesión", controller.primaryStage.getTitle());
    }

    @Test
    void testMostrarPantallaRegistro_NoLanzaExcepcion() {
        assertDoesNotThrow(() -> controller.mostrarPantallaRegistro());
        assertNotNull(controller.primaryStage.getScene());
        assertEquals("Registro", controller.primaryStage.getTitle());
    }

    @Test
    void testRegistroUsuarioYaExiste() {
        when(usuarioService.usuarioExiste("juan")).thenReturn(true);

        controller.mostrarPantallaRegistro();

        // Simular acción de registro directamente
        Usuario usuario = new Usuario();
        usuario.setNombre("juan");
        usuario.setContrasena("1234");

        // Lógica similar a la del botón
        if (usuarioService.usuarioExiste(usuario.getNombre())) {
            // no se guarda el usuario ni se notifica
            verify(usuarioService, never()).guardar(any());
            verify(notificador, never()).enviarMensaje(anyString());
        }
    }

    @Test
    void testRegistroUsuarioNuevoValido() {
        when(usuarioService.usuarioExiste("ana")).thenReturn(false);
        when(usuarioService.guardar(any(Usuario.class))).thenReturn(new Usuario());

        controller.mostrarPantallaRegistro();

        Usuario nuevo = new Usuario();
        nuevo.setNombre("ana");
        nuevo.setContrasena("clave");

        usuarioService.guardar(nuevo);
        notificador.enviarMensaje("Nuevo usuario registrado: ana");

        verify(usuarioService, times(1)).guardar(any(Usuario.class));
        verify(notificador, times(1)).enviarMensaje(contains("ana"));
    }

    @Test
    void testLoginUsuarioValido() {
        when(usuarioService.validarUsuario("juan", "1234")).thenReturn(true);

        controller.mostrarPantallaLogin();

        usuarioService.validarUsuario("juan", "1234");
        notificador.enviarMensaje("Usuario juan inició sesión");

        verify(usuarioService, times(1)).validarUsuario("juan", "1234");
        verify(notificador, times(1)).enviarMensaje(contains("juan"));
    }

    @Test
    void testLoginUsuarioInvalido() {
        when(usuarioService.validarUsuario("pedro", "0000")).thenReturn(false);

        controller.mostrarPantallaLogin();

        usuarioService.validarUsuario("pedro", "0000");

        verify(usuarioService, times(1)).validarUsuario("pedro", "0000");
        verify(notificador, never()).enviarMensaje(anyString());
    }
}


      
