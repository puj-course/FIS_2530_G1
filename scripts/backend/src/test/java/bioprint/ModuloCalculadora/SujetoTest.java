package bioprint.modulocalculadora;

import org.junit.jupiter.api.Test;

import bioprint.modulocalculadora.Sujeto;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SujetoTest {

    @Test
    void testNotificarObservador() {
        Sujeto sujeto = new Sujeto();
        final boolean[] notificado = {false};

        sujeto.addObservador(total -> notificado[0] = true);
        sujeto.notificar(50);

        assertTrue(notificado[0], "El observador debería ser notificado");
    }
}
