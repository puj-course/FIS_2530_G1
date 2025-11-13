package bioprint.modulocalculadora;

import org.junit.jupiter.api.Test;

import bioprint.modulocalculadora.ArbolesAdapter;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArbolesAdapterTest {

    @Test
    void testValorNormal() {
        ArbolesAdapter adapter = new ArbolesAdapter();
        assertEquals("Equivale a plantar 5 árboles.", adapter.mostrar(100));
    }

    @Test
    void testValorCero() {
        ArbolesAdapter adapter = new ArbolesAdapter();
        assertEquals("Equivale a plantar 0 árboles.", adapter.mostrar(0));
    }

    @Test
    void testValorNegativo() {
        ArbolesAdapter adapter = new ArbolesAdapter();
        assertEquals("los kilogramos de CO2 no pueden ser menores a 0", adapter.mostrar(-5));
    }
}