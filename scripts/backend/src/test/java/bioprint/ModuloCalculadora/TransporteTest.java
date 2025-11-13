package bioprint.modulocalculadora;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import bioprint.modulocalculadora.EstrategiaBus;
import bioprint.modulocalculadora.EstrategiaCarro;
import bioprint.modulocalculadora.Transporte;

public class TransporteTest {

    @Test
    void EstrategiaCarroTest() {
        Transporte transporte = new Transporte(new EstrategiaCarro(), 10);
        assertEquals(2.1, transporte.calcularCO2(), 0.001);
    }
    @Test
    void EstrategiaCarroMenor0Test(){
        Transporte transporte = new Transporte(new EstrategiaCarro(), -20);
        assertEquals(0.0, transporte.calcularCO2(), 0.001);
    }
    @Test
    void testEstrategiaBusCeroKm() {
        EstrategiaBus bus = new EstrategiaBus();
        assertEquals(0.0, bus.calcularCO2(0), 0.001);
    }

}
