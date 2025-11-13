package bioprint.ModuloUsuarios;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MenuUsuariosTest {

    private UsuarioService servicioMock;
    private Notificador botMock;
    private boolean[] salir;
    private String[] nombre;
    private InputStream originalIn;

    @BeforeEach
    public void setUp() {
        servicioMock = mock(UsuarioService.class);
        botMock = mock(Notificador.class);
        salir = new boolean[]{false};
        nombre = new String[1];
        originalIn = System.in;
    }

    @AfterEach
    public void restoreSystemIn() {
        System.setIn(originalIn);
    }

    @Test
    public void testSeleccionInvalidaLuegoSalir() {
        // Usuario primero ingresa opción inválida (5), luego elige 3 (salir)
        String input = "5\n3\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        boolean resultado = MenuUsuarios.menu(servicioMock, salir, botMock, nombre);

        assertTrue(resultado);
        assertTrue(salir[0]);
        verifyNoInteractions(servicioMock, botMock);
    }

    @Test
    public void testInicioSesionCanceladoPorSalirEnUsuario() {
        // Opción 1  ingresar "salir" como usuario
        String input = "1\nsalir\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        boolean resultado = MenuUsuarios.menu(servicioMock, salir, botMock, nombre);

        assertFalse(resultado);
        verifyNoInteractions(servicioMock, botMock);
    }

    @Test
    public void testInicioSesionCanceladoPorSalirEnContrasena() {
        // Opción 1  usuario válido, pero "salir" en contraseña
        String input = "1\njuan\nsalir\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        boolean resultado = MenuUsuarios.menu(servicioMock, salir, botMock, nombre);

        assertFalse(resultado);
        verifyNoInteractions(servicioMock, botMock);
    }

    @Test
    public void testInicioSesionExitoso() {
        // Opción 1  usuario y contraseña válidos
        String input = "1\njuan\n1234\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        when(servicioMock.validarUsuario("juan", "1234")).thenReturn(true);

        boolean resultado = MenuUsuarios.menu(servicioMock, salir, botMock, nombre);

        assertTrue(resultado);
        verify(botMock).enviarMensaje("Usuario juan acaba de iniciar sesion");
    }

    @Test
    public void testInicioSesionFallidoYReintento() {
        // Opción 1  credenciales inválidas, luego válidas
        String input = "1\njuan\nmalpass\njuan\n1234\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        when(servicioMock.validarUsuario("juan", "malpass")).thenReturn(false);
        when(servicioMock.validarUsuario("juan", "1234")).thenReturn(true);

        boolean resultado = MenuUsuarios.menu(servicioMock, salir, botMock, nombre);

        assertTrue(resultado);
        verify(botMock).enviarMensaje("Usuario juan acaba de iniciar sesion");
    }

    @Test
    public void testRegistroExitoso() {
        // Opción 2  crear nuevo usuario correctamente
        String input = "2\nnuevoUser\nclaveSegura\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        boolean resultado = MenuUsuarios.menu(servicioMock, salir, botMock, nombre);

        assertTrue(resultado);
        verify(servicioMock).guardar(any(Usuario.class));
        verify(botMock).enviarMensaje("Nuevo usuario agregado a la base de datos: nuevoUser");
    }

    @Test
    public void testRegistroCanceladoPorSalirEnUsuario() {
        String input = "2\nsalir\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        boolean resultado = MenuUsuarios.menu(servicioMock, salir, botMock, nombre);

        assertFalse(resultado);
        verifyNoInteractions(servicioMock, botMock);
    }

    @Test
    public void testRegistroCanceladoPorSalirEnContrasena() {
        String input = "2\njuan\nsalir\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        boolean resultado = MenuUsuarios.menu(servicioMock, salir, botMock, nombre);

        assertFalse(resultado);
        verifyNoInteractions(botMock);
    }
}
