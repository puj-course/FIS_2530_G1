package bioprint.modulousuarios;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UsuarioControllerTest {

    private UsuarioService usuarioService;
    private UsuarioController controller;

    @BeforeEach
    void setUp() {
        usuarioService = mock(UsuarioService.class);
        controller = new UsuarioController(usuarioService);
    }

    @Test
    void testListarUsuarios() {
        // Datos simulados
        Usuario u1 = new Usuario();
        u1.setNombre("juan");
        u1.setContrasena("1234");

        Usuario u2 = new Usuario();
        u2.setNombre("ana");
        u2.setContrasena("abcd");

        when(usuarioService.listar()).thenReturn(Arrays.asList(u1, u2));

        // Ejecución
        List<Usuario> resultado = controller.listar();

        // Verificación
        assertEquals(2, resultado.size());
        assertEquals("juan", resultado.get(0).getNombre());
        verify(usuarioService, times(1)).listar();
    }

    @Test
    void testCrearUsuario() {
        Usuario nuevo = new Usuario();
        nuevo.setNombre("carlos");
        nuevo.setContrasena("pass");

        when(usuarioService.guardar(nuevo)).thenReturn(nuevo);

        Usuario resultado = controller.crear(nuevo);

        assertNotNull(resultado);
        assertEquals("carlos", resultado.getNombre());
        verify(usuarioService, times(1)).guardar(nuevo);
    }

    @Test
    void testCrearUsuario_NullUsuario() {
        // Si se pasa null, simulamos que el servicio también devuelve null
        when(usuarioService.guardar(null)).thenReturn(null);

        Usuario resultado = controller.crear(null);

        assertNull(resultado, "Debe devolver null si el usuario es null");
        verify(usuarioService, times(1)).guardar(null);
    }

    @Test
    void testListarUsuarios_Vacio() {
        when(usuarioService.listar()).thenReturn(List.of());

        List<Usuario> resultado = controller.listar();

        assertTrue(resultado.isEmpty());
        verify(usuarioService, times(1)).listar();
    }
}
