package bioprint.modulocalculadora;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import bioprint.modulocalculadora.FuenteHuella;
import bioprint.modulocalculadora.FuenteHuellaFactory;

public class FuenteHuellaFactoryTest {

    @Test
    void testFuenteEnergia() {
        FuenteHuella f = FuenteHuellaFactory.crearFuente(10, 5, 3);
        double co2 = f.calcularCO2(); // (10*0.5) + (5*2.3) + (3*0.35) = 5 + 11.5 + 1.05 = 17.55
        assertEquals(17.55, co2, 0.001);
    }

    @Test
    void testFuenteDietaOmnivora() {
        FuenteHuella f = FuenteHuellaFactory.crearFuente("omnivora", 5);
        assertEquals(2100, f.calcularCO2());
    }

    @Test
    void testCombinacionDeFuentes() {
        FuenteHuella f1 = FuenteHuellaFactory.crearFuente(10, 0, 0);
        FuenteHuella f2 = FuenteHuellaFactory.crearFuente("vegana", 0);
        FuenteHuella combinada = FuenteHuellaFactory.crearFuente(f1, f2);

        assertEquals(1005, combinada.calcularCO2(), 0.001);
    }
}
