package bioprint.ModuloUsuarios;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    @Mock
    private UsuarioRepository repo;

    @InjectMocks
    private UsuarioService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListarDevuelveTodosLosUsuarios() {
        List<Usuario> listaMock = Arrays.asList(
                new Usuario("juan", "1234"),
                new Usuario("ana", "abcd")
        );

        when(repo.findAll()).thenReturn(listaMock);

        List<Usuario> resultado = service.listar();

        assertEquals(2, resultado.size());
        verify(repo, times(1)).findAll();
    }

    @Test
    void testGuardarGuardaYDevuelveUsuario() {
        Usuario u = new Usuario("carlos", "pass");
        when(repo.save(u)).thenReturn(u);

        Usuario resultado = service.guardar(u);

        assertEquals(u, resultado);
        verify(repo, times(1)).save(u);
    }

    @Test
    void testValidarUsuarioValido() {
        String nombre = "pepe";
        String contrasena = "123";
        Usuario mockUsuario = new Usuario(nombre, contrasena);

        when(repo.findByNombreAndContrasena(nombre, contrasena))
                .thenReturn(mockUsuario);

        boolean valido = service.validarUsuario(nombre, contrasena);

        assertTrue(valido);
        verify(repo).findByNombreAndContrasena(nombre, contrasena);
    }

    @Test
    void testValidarUsuarioInvalido() {
        String nombre = "pepe";
        String contrasena = "123";

        when(repo.findByNombreAndContrasena(nombre, contrasena))
                .thenReturn(null);

        boolean valido = service.validarUsuario(nombre, contrasena);

        assertFalse(valido);
        verify(repo).findByNombreAndContrasena(nombre, contrasena);
    }
    @Test
void testListarDevuelveListaVacia() {
    when(repo.findAll()).thenReturn(List.of());

    List<Usuario> resultado = service.listar();

    assertTrue(resultado.isEmpty());
    verify(repo, times(1)).findAll();
}
    @Test
void testGuardarLanzaExcepcionSiFallaRepositorio() {
    Usuario u = new Usuario("error", "1234");
    when(repo.save(u)).thenThrow(new RuntimeException("Error de BD"));

    RuntimeException ex = assertThrows(RuntimeException.class, () -> service.guardar(u));
    assertEquals("Error de BD", ex.getMessage());
    verify(repo).save(u);
}
@Test
void testValidarUsuarioConCamposNulos() {
    boolean resultado = service.validarUsuario(null, null);
    verify(repo).findByNombreAndContrasena(null, null);
    assertFalse(resultado);
}
@Test
void testValidarUsuarioConEspaciosOMayusculas() {
    String nombre = " Pepe ";
    String contrasena = "123";
    when(repo.findByNombreAndContrasena(nombre.trim(), contrasena)).thenReturn(null);

    // Este test simula que no se hace trim ni normalización
    boolean valido = service.validarUsuario(nombre, contrasena);

    assertFalse(valido);
    verify(repo).findByNombreAndContrasena(nombre, contrasena);
}
@Test
void testListarNoModificaListaOriginal() {
    List<Usuario> original = Arrays.asList(new Usuario("a", "1"), new Usuario("b", "2"));
    when(repo.findAll()).thenReturn(original);

    List<Usuario> resultado = service.listar();

    assertEquals(original, resultado);
    assertNotSame(original, resultado); // si devuelves copia
    verify(repo).findAll();
}
}


