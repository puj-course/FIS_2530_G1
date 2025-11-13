package bioprint.modulocalculadora;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import bioprint.modulocalculadora.Observador;
import bioprint.modulocalculadora.Sujeto;

class SujetoObservadorTest {

    static class ObservadorPrueba implements Observador {
        double valorRecibido = -1;
        @Override
        public void actualizar(double total) {
            valorRecibido = total;
        }
    }

    @Test
    void testNotificacionAUnObservador() {
        Sujeto sujeto = new Sujeto();
        ObservadorPrueba obs = new ObservadorPrueba();

        sujeto.addObservador(obs);
        sujeto.notificar(55.5);

        assertEquals(55.5, obs.valorRecibido);
    }

    @Test
    void testMultiplesObservadores() {
        Sujeto sujeto = new Sujeto();
        ObservadorPrueba obs1 = new ObservadorPrueba();
        ObservadorPrueba obs2 = new ObservadorPrueba();

        sujeto.addObservador(obs1);
        sujeto.addObservador(obs2);

        sujeto.notificar(99.9);

        assertEquals(99.9, obs1.valorRecibido);
        assertEquals(99.9, obs2.valorRecibido);
    }
}