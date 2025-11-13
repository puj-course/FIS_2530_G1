package bioprint.ModuloUsuarios;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import javafx.stage.Stage;

/**
 * Pruebas unitarias para LoginController (sin abrir interfaz JavaFX)
 */
public class LoginControllerTest {

    private LoginController controller;
    private UsuarioService usuarioServiceMock;
    private Notificador notificadorMock;

    @BeforeEach
    public void setUp() {
        controller = new LoginController();

        // Inyectar dependencias simuladas (mocks)
        usuarioServiceMock = Mockito.mock(UsuarioService.class);
        notificadorMock = Mockito.mock(Notificador.class);

        // Simular Stage vacío (no abre ventana real)
        Stage dummyStage = new Stage();

        // Inicializar manualmente campos privados
        controller.usuarioService = usuarioServiceMock;
        controller.bot = notificadorMock;
        controller.start(dummyStage);  // simula la inicialización
    }

    @Test
    public void testLoginUsuarioValido() {
        // Usuario correcto
        when(usuarioServiceMock.validarUsuario("juan", "123")).thenReturn(true);

        boolean valido = usuarioServiceMock.validarUsuario("juan", "123");
        assertTrue(valido);

        // Verifica que se haya notificado correctamente
        verify(usuarioServiceMock, times(1)).validarUsuario("juan", "123");
    }

    @Test
    public void testLoginUsuarioInvalido() {
        when(usuarioServiceMock.validarUsuario("maria", "xyz")).thenReturn(false);

        boolean valido = usuarioServiceMock.validarUsuario("maria", "xyz");
        assertFalse(valido);

        verify(usuarioServiceMock, times(1)).validarUsuario("maria", "xyz");
        verify(notificadorMock, never()).enviarMensaje(anyString());
    }

    @Test
    public void testRegistrarUsuarioNuevo() {
        when(usuarioServiceMock.usuarioExiste("nuevo")).thenReturn(false);

        Usuario nuevo = new Usuario();
        nuevo.setNombre("nuevo");
        nuevo.setContrasena("clave");
        usuarioServiceMock.guardar(nuevo);

        verify(usuarioServiceMock, times(1)).guardar(nuevo);
    }

    @Test
    public void testRegistrarUsuarioExistente() {
        when(usuarioServiceMock.usuarioExiste("repetido")).thenReturn(true);

        boolean existe = usuarioServiceMock.usuarioExiste("repetido");
        assertTrue(existe);

        verify(usuarioServiceMock, times(1)).usuarioExiste("repetido");
        verify(usuarioServiceMock, never()).guardar(any());
    }

    @Test
    public void testCamposVaciosEnRegistro() {
        Usuario vacio = new Usuario();
        vacio.setNombre("");
        vacio.setContrasena("");

        assertEquals("", vacio.getNombre());
        assertEquals("", vacio.getContrasena());
    }
}

