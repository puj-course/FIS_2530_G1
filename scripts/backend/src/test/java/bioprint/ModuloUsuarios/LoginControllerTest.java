package bioprint.ModuloUsuarios;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UsuarioControllerTest {

    private UsuarioService mockService;
    private UsuarioController controller;

    @BeforeEach
    void setUp() {
        mockService = mock(UsuarioService.class);
        controller = new UsuarioController(mockService);
    }

    @Test
    void testListarUsuariosOk() {
        Usuario u1 = new Usuario(1L, "Juan", "1234");
        Usuario u2 = new Usuario(2L, "Ana", "abcd");

        when(mockService.listar()).thenReturn(Arrays.asList(u1, u2));

        List<Usuario> resultado = controller.listar();

        assertEquals(2, resultado.size());
        assertEquals("Juan", resultado.get(0).getNombre());
        verify(mockService, times(1)).listar();
    }

    @Test
    void testListarUsuariosVacia() {
        when(mockService.listar()).thenReturn(List.of());

        List<Usuario> resultado = controller.listar();

        assertTrue(resultado.isEmpty());
        verify(mockService, times(1)).listar();
    }

    @Test
    void testCrearUsuarioOk() {
        Usuario nuevo = new Usuario(null, "Pedro", "clave");
        Usuario guardado = new Usuario(3L, "Pedro", "clave");

        when(mockService.guardar(nuevo)).thenReturn(guardado);

        Usuario resultado = controller.crear(nuevo);

        assertNotNull(resultado);
        assertEquals(3L, resultado.getId());
        assertEquals("Pedro", resultado.getNombre());
        verify(mockService, times(1)).guardar(nuevo);
    }
    @Test
    void testCrearUsuarioNulo() {
        Usuario entrada = new Usuario(null, "Carlos", "pass");
        when(mockService.guardar(entrada)).thenReturn(null);

        Usuario resultado = controller.crear(entrada);

        assertNull(resultado);
        verify(mockService, times(1)).guardar(entrada);
    }

    @Test
    void testListarUsuariosLanzaExcepcion() {
        when(mockService.listar()).thenThrow(new RuntimeException("Error en BD"));
        assertThrows(RuntimeException.class, () -> controller.listar());
        verify(mockService).listar();
    }

    @Test
    void testGuardarUsuarioLanzaExcepcion() {
        Usuario u = new Usuario(null, "Mario", "000");
        when(mockService.guardar(u)).thenThrow(new RuntimeException("Error al guardar"));
        assertThrows(RuntimeException.class, () -> controller.crear(u));
        verify(mockService).guardar(u);
    }

    @Test
    void testConstructorYServiceNoNulo() {
        assertNotNull(controller);
        assertNotNull(mockService);
    }

    @Test
    void testMetodosDeclarados() {
        assertDoesNotThrow(() -> UsuarioController.class.getDeclaredMethod("listar"));
        assertDoesNotThrow(() -> UsuarioController.class.getDeclaredMethod("crear", Usuario.class));
    }
}
