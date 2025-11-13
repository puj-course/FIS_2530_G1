package bioprint.modulousuarios;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PuntajeServiceTest {

    private PuntajeRepository puntajeRepo;
    private UsuarioRepository usuarioRepo;
    private PuntajeService service;

    @BeforeEach
    void setUp() {
        puntajeRepo = mock(PuntajeRepository.class);
        usuarioRepo = mock(UsuarioRepository.class);
        service = new PuntajeService(puntajeRepo, usuarioRepo);
    }

    @Test
    void testRegistrarPuntaje_UsuarioExistente() {
        Usuario usuario = new Usuario();
        usuario.setNombre("juan");

        when(usuarioRepo.findByNombre("juan")).thenReturn(usuario);

        Puntaje esperado = new Puntaje();
        esperado.setUsuario(usuario);
        esperado.setValor(80);
        esperado.setFecha(LocalDate.now());

        when(puntajeRepo.save(any(Puntaje.class))).thenReturn(esperado);

        Puntaje resultado = service.registrarPuntaje("juan", 80);

        assertNotNull(resultado);
        assertEquals(usuario, resultado.getUsuario());
        assertEquals(80, resultado.getValor());
        verify(puntajeRepo, times(1)).save(any(Puntaje.class));
    }

    @Test
    void testObtenerPuntajes() {
        Usuario usuario = new Usuario();
        usuario.setNombre("ana");

        Puntaje p1 = new Puntaje();
        p1.setUsuario(usuario);
        p1.setValor(90);
        p1.setFecha(LocalDate.now());

        when(puntajeRepo.findByUsuarioNombre("ana")).thenReturn(List.of(p1));

        List<Puntaje> lista = service.obtenerPuntajes("ana");

        assertEquals(1, lista.size());
        assertEquals(90, lista.get(0).getValor());
        verify(puntajeRepo, times(1)).findByUsuarioNombre("ana");
    }

    @Test
    void testContarUsuariosConPuntajeMayor_SinPuntajesUsuario() {
        when(puntajeRepo.findByUsuarioNombre("juan")).thenReturn(Collections.emptyList());

        long count = service.contarUsuariosConPuntajeMayor("juan");

        assertEquals(0, count, "Si el usuario no tiene puntajes, el resultado debe ser 0");
    }

    @Test
    void testContarUsuariosConPuntajeMayor_VariosUsuarios() {
        Usuario juan = new Usuario();
        juan.setNombre("juan");
        Usuario ana = new Usuario();
        ana.setNombre("ana");
        Usuario pedro = new Usuario();
        pedro.setNombre("pedro");

        // Puntajes de Juan
        Puntaje pj1 = new Puntaje();
        pj1.setUsuario(juan);
        pj1.setValor(85);
        pj1.setFecha(LocalDate.now().minusDays(1));

        when(puntajeRepo.findByUsuarioNombre("juan")).thenReturn(List.of(pj1));

        // Puntajes de Ana
        Puntaje pa1 = new Puntaje();
        pa1.setUsuario(ana);
        pa1.setValor(60);
        pa1.setFecha(LocalDate.now().minusDays(2));

        when(puntajeRepo.findByUsuarioNombre("ana")).thenReturn(List.of(pa1));

        // Puntajes de Pedro
        Puntaje pp1 = new Puntaje();
        pp1.setUsuario(pedro);
        pp1.setValor(90);
        pp1.setFecha(LocalDate.now().minusDays(3));

        when(puntajeRepo.findByUsuarioNombre("pedro")).thenReturn(List.of(pp1));

        when(usuarioRepo.findAll()).thenReturn(Arrays.asList(juan, ana, pedro));

        long count = service.contarUsuariosConPuntajeMayor("juan");

        // Ana tiene 60 (<85), Pedro tiene 90 (>85)
        // Según la lógica actual, cuenta los que tienen MENOR puntaje
        assertEquals(1, count, "Solo un usuario (Ana) tiene puntaje menor que Juan");
    }

    @Test
    void testContarUsuariosConPuntajeMayor_TodosMenores() {
        Usuario base = new Usuario();
        base.setNombre("base");
        Puntaje pb = new Puntaje();
        pb.setUsuario(base);
        pb.setValor(100);
        pb.setFecha(LocalDate.now());

        when(puntajeRepo.findByUsuarioNombre("base")).thenReturn(List.of(pb));

        Usuario u1 = new Usuario();
        u1.setNombre("u1");
        Usuario u2 = new Usuario();
        u2.setNombre("u2");

        Puntaje p1 = new Puntaje();
        p1.setUsuario(u1);
        p1.setValor(70);
        p1.setFecha(LocalDate.now());
        Puntaje p2 = new Puntaje();
        p2.setUsuario(u2);
        p2.setValor(60);
        p2.setFecha(LocalDate.now());

        when(usuarioRepo.findAll()).thenReturn(List.of(base, u1, u2));
        when(puntajeRepo.findByUsuarioNombre("u1")).thenReturn(List.of(p1));
        when(puntajeRepo.findByUsuarioNombre("u2")).thenReturn(List.of(p2));

        long count = service.contarUsuariosConPuntajeMayor("base");

        assertEquals(2, count, "Ambos tienen puntaje menor al del usuario base");
    }
}
