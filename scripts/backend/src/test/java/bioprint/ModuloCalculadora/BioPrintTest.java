package bioprint.ModuloCalculadora.BioPrint;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BioPrintTest {

    @Test
    void testMainEjecutaSinErrores() {
        // Se ejecuta con el modo "test" para evitar que entre al menú interactivo
        assertDoesNotThrow(() -> BioPrint.main(new String[]{"test"}));
    }

    @Test
    void testMainSinArgumentos() {
        // También se verifica que al pasar sin argumentos no lance excepción
        assertDoesNotThrow(() -> BioPrint.main(new String[]{}));
    }
}
