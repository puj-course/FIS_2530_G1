package bioprint.modulocalculadora;

import org.junit.jupiter.api.Test;

import bioprint.modulocalculadora.ConsolaObservador;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class ConsolaObservadorTest {

    @Test
    void testActualizarImprimeMensaje() {
        ConsolaObservador obs = new ConsolaObservador();

        // Redirigir salida de consola
        ByteArrayOutputStream salida = new ByteArrayOutputStream();
        System.setOut(new PrintStream(salida));

        obs.actualizar(123.45);

        String output = salida.toString().trim();
        assertTrue(output.contains("Nueva huella calculada"));
        assertTrue(output.contains("123.45"));
    }
}
