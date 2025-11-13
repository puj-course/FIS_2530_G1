package bioprint.modulocalculadora;

import org.junit.jupiter.api.Test;

import bioprint.modulocalculadora.EstrategiaBici;
import bioprint.modulocalculadora.EstrategiaBus;
import bioprint.modulocalculadora.EstrategiaCarro;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EstrategiaTransporteTest {

    @Test
    void testCarro() {
        EstrategiaCarro carro = new EstrategiaCarro();
        assertEquals(2.1, carro.calcularCO2(10), 0.001);
    }

    @Test
    void testBus() {
        EstrategiaBus bus = new EstrategiaBus();
        assertEquals(1.0, bus.calcularCO2(10), 0.001);
    }

    @Test
    void testBici() {
        EstrategiaBici bici = new EstrategiaBici();
        assertEquals(0.0, bici.calcularCO2(10), 0.001);
    }
}