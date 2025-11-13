package bioprint.modulocalculadora;

import org.junit.jupiter.api.Test;

import bioprint.modulocalculadora.EstrategiaBici;
import bioprint.modulocalculadora.EstrategiaBus;
import bioprint.modulocalculadora.EstrategiaCarro;

import static org.junit.jupiter.api.Assertions.*;

class EstrategiasTest {

    @Test
    void testCarro() {
        EstrategiaCarro e = new EstrategiaCarro();
        assertEquals(21.0, e.calcularCO2(100), 0.01);
        assertEquals(0, e.calcularCO2(-5));
    }

    @Test
    void testBus() {
        EstrategiaBus e = new EstrategiaBus();
        assertEquals(10.0, e.calcularCO2(100), 0.01);
        assertEquals(0, e.calcularCO2(-3));
    }

    @Test
    void testBici() {
        EstrategiaBici e = new EstrategiaBici();
        assertEquals(0, e.calcularCO2(50));
    }
}
