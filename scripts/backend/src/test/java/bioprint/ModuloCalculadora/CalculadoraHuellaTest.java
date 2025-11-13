package bioprint.modulocalculadora;

import org.junit.jupiter.api.Test;

import bioprint.modulocalculadora.ArbolesAdapter;
import bioprint.modulocalculadora.EstrategiaCarro;
import bioprint.modulocalculadora.EstrategiaTransporte;
import bioprint.modulocalculadora.FuenteHuella;
import bioprint.modulocalculadora.FuenteHuellaFactory;
import bioprint.modulocalculadora.Transporte;

import static org.junit.jupiter.api.Assertions.*;

public class CalculadoraHuellaTest {

    @Test
    void testCalculoTransporteCarro() {
        EstrategiaTransporte carro = new EstrategiaCarro();
        Transporte transporte = new Transporte(carro, 100);
        assertEquals(21.0, transporte.calcularCO2(), 0.001);
    }

    @Test
    void testArbolesAdapter() {
        ArbolesAdapter adapter = new ArbolesAdapter();
        assertEquals("Equivale a plantar 5 árboles.", adapter.mostrar(100));
        assertEquals("los kilogramos de CO2 no pueden ser menores a 0", adapter.mostrar(-10));
    }

    @Test
    void testFactoryEnergia() {
        FuenteHuella energia = FuenteHuellaFactory.crearFuente(10, 5, 3);
        double total = energia.calcularCO2();
        assertTrue(total > 0);
    }

    @Test
    void testComposicionFuentes() {
        FuenteHuella f1 = FuenteHuellaFactory.crearFuente(10, 0, 0);
        FuenteHuella f2 = FuenteHuellaFactory.crearFuente("vegana", 0);
        FuenteHuella total = FuenteHuellaFactory.crearFuente(f1, f2);
        assertTrue(total.calcularCO2() > 0);
    }
}
