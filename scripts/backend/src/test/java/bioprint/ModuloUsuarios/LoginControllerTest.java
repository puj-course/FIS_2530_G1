package bioprint.ModuloUsuarios;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LoginControllerTest {

    @Test
    public void testInicioSesionExitoso() {
        // Simula el comportamiento básico del controlador
        UsuarioService servicio = new UsuarioService();
        LoginController controller = new LoginController(servicio);

        // Crear usuario válido
        Usuario usuario = new Usuario("juan", "1234");
        servicio.guardar(usuario);

        // Ejecutar login
        boolean resultado = controller.iniciarSesion("juan", "1234");

        Assertions.assertTrue(resultado, "El usuario debería poder iniciar sesión correctamente");
    }

    @Test
    public void testInicioSesionFallido() {
        UsuarioService servicio = new UsuarioService();
        LoginController controller = new LoginController(servicio);

        // Usuario inexistente
        boolean resultado = controller.iniciarSesion("invalido", "contraseña");

        Assertions.assertFalse(resultado, "El inicio de sesión debe fallar para usuario inexistente");
    }

    @Test
    public void testInicioSesionConContrasenaIncorrecta() {
        UsuarioService servicio = new UsuarioService();
        LoginController controller = new LoginController(servicio);

        Usuario usuario = new Usuario("ana", "1234");
        servicio.guardar(usuario);

        boolean resultado = controller.iniciarSesion("ana", "0000");

        Assertions.assertFalse(resultado, "El inicio de sesión debe fallar si la contraseña es incorrecta");
    }
}



