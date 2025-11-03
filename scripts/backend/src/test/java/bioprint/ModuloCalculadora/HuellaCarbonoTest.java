package bioprint.ModuloCalculadora;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class HuellaCarbonoTest {

    // 1️ Prueba: calcularCO2() Energía (caso normal)
    @Test
    public void testCalcularCO2Energia() {
        double luz = 100.0;
        double gas = 50.0;
        double agua = 10.0;

        FuenteHuella energia = FuenteHuellaFactory.crearFuente(luz, gas, agua);
        double resultado = energia.calcularCO2();
        double esperado = luz * 0.5 + gas * 2.3 + agua * 0.35;

        System.out.println(" [testCalcularCO2Energia]");
        System.out.println("   Esperado: " + esperado + " | Obtenido: " + resultado);

        assertEquals(esperado, resultado, 0.01, "El cálculo de CO₂ de energía debe ser correcto.");
    }

    // 2️ Prueba: EstrategiaCarro (Strategy)
    @Test
    public void testEstrategiaCarroCalculo() {
        EstrategiaTransporte estrategia = new EstrategiaCarro();
        Transporte transporte = new Transporte(estrategia, 100.0);

        double co2 = transporte.calcularCO2();
        double esperado = 21.0;

        System.out.println(" [testEstrategiaCarroCalculo]");
        System.out.println("   Esperado: " + esperado + " | Obtenido: " + co2);

        assertEquals(esperado, co2, 0.01, "El cálculo debe aplicar 0.21 kgCO₂/km.");
    }

    // 3️ Prueba: Singleton — CalculadoraHuella.getInstance()
    @Test
    public void testSingletonCalculadoraHuella() {
        CalculadoraHuella c1 = CalculadoraHuella.getInstance();
        CalculadoraHuella c2 = CalculadoraHuella.getInstance();

        System.out.println(" [testSingletonCalculadoraHuella]");
        System.out.println("   Instancia 1 hash: " + c1.hashCode());
        System.out.println("   Instancia 2 hash: " + c2.hashCode());

        assertSame(c1, c2, "CalculadoraHuella debe seguir el patrón Singleton.");
    }

    // 4️ Prueba: calcularTotal() — Composite + Singleton
    @Test
    public void testCalcularTotalGrupoFuentes() {
        GrupoFuentes grupo = new GrupoFuentes();
        FuenteHuella energia = FuenteHuellaFactory.crearFuente(100.0, 0.0, 0.0);
        FuenteHuella alimentacion = FuenteHuellaFactory.crearFuente("vegana", 0);

        grupo.addFuente(energia);
        grupo.addFuente(alimentacion);

        CalculadoraHuella calculadora = CalculadoraHuella.getInstance();

        double totalPorGrupo = grupo.calcularCO2();
        double totalPorCalculadora = calculadora.calcularTotal(grupo);

        System.out.println(" [testCalcularTotalGrupoFuentes]");
        System.out.println("   Total por grupo: " + totalPorGrupo);
        System.out.println("   Total por calculadora: " + totalPorCalculadora);

        assertEquals(totalPorGrupo, totalPorCalculadora, 0.01,
                "El cálculo total debe coincidir entre el GrupoFuentes y la CalculadoraHuella.");
        assertTrue(totalPorGrupo > 0, "El total calculado debe ser mayor que cero.");
    }

    // 5️ Prueba: ArbolesAdapter — conversión a equivalencia
    @Test
    public void testArbolesAdapterMostrar() {
        ArbolesAdapter adapter = new ArbolesAdapter();
        double co2 = 1000.0;

        String resultado = adapter.mostrar(co2);

        System.out.println(" [testArbolesAdapterMostrar]");
        System.out.println("   Entrada CO₂: " + co2 + " | Resultado: " + resultado);

        assertNotNull(resultado, "El mensaje no debe ser nulo.");
        assertTrue(resultado.contains("árboles") || resultado.contains("arboles"),
                "El texto debe mencionar árboles.");
        assertTrue(resultado.contains("50"),
                "Para 1000 kgCO₂, debería equivaler a unos 50 árboles (1000/20).");
    }

    // 6️ Caso borde: km = 0 → CO₂ = 0
    @Test
    public void testEstrategiaCarroBordeKmCero() {
        EstrategiaTransporte estrategia = new EstrategiaCarro();
        Transporte transporte = new Transporte(estrategia, 0.0);

        double co2 = transporte.calcularCO2();

        System.out.println(" [testEstrategiaCarroBordeKmCero]");
        System.out.println("   Entrada: 0 km | Resultado: " + co2);

        assertEquals(0.0, co2, 0.0001, "Si el recorrido es 0 km, el CO₂ debe ser 0.");
    }

    // 7️ Caso negativo: energía con valores negativos → no debe producir CO₂ negativo
    @Test
    public void testEnergiaEntradasNegativas() {
        FuenteHuella energia = FuenteHuellaFactory.crearFuente(-50.0, -10.0, -5.0);
        double co2 = energia.calcularCO2();

        System.out.println(" [testEnergiaEntradasNegativas]");
        System.out.println("   Entradas negativas (-50, -10, -5) | CO₂ obtenido: " + co2);

        assertTrue(co2 >= 0.0, "El cálculo de CO₂ no debe ser negativo, incluso si las entradas lo son.");
    }
}
