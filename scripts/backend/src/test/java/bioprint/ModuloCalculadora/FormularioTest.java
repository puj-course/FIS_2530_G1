package bioprint.ModuloCalculadora;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.PrintStream;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;

public class FormularioTest {

    // --- TESTS PARA int ---
    @Test
    void testEnteroDentroDelRango() {
        assertTrue(Formulario.validar(5, 10, 1, false));
    }

    @Test
    void testEnteroFueraDelRangoSinOpciones() {
        assertFalse(Formulario.validar(15, 10, 1, false));
    }

    @Test
    void testEnteroFueraDelRangoConOpciones() {
        assertFalse(Formulario.validar(0, 10, 1, true));
    }

    @Test
    void testEnteroIgualAlMinimo() {
        assertTrue(Formulario.validar(1, 10, 1, false));
    }

    @Test
    void testEnteroIgualAlMaximo() {
        assertTrue(Formulario.validar(10, 10, 1, false));
    }

    // --- TESTS PARA double ---
    @Test
    void testDoubleDentroDelRango() {
        assertTrue(Formulario.validar(5.5, 10.0, 1.0, false));
    }

    @Test
    void testDoubleFueraDelRangoSinOpciones() {
        assertFalse(Formulario.validar(11.0, 10.0, 1.0, false));
    }

    @Test
    void testDoubleFueraDelRangoConOpciones() {
        assertFalse(Formulario.validar(-2.0, 10.0, 1.0, true));
    }

    @Test
    void testDoubleIgualAlMinimo() {
        assertTrue(Formulario.validar(1.0, 10.0, 1.0, false));
    }

    @Test
    void testDoubleIgualAlMaximo() {
        assertTrue(Formulario.validar(10.0, 10.0, 1.0, false));
    }

    // --- TESTS NUEVOS PARA AUMENTAR COBERTURA ---

    @Test
    void testEnteroFueraDelRangoImprimeMensajeSinOpciones() {
        ByteArrayOutputStream salida = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(salida));

        boolean resultado = Formulario.validar(20, 10, 1, false);

        System.setOut(originalOut); // restaurar salida estándar
        assertFalse(resultado);
        assertTrue(salida.toString().contains("Por favor ingresar un número entre"));
    }

    @Test
    void testEnteroFueraDelRangoImprimeMensajeConOpciones() {
        ByteArrayOutputStream salida = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(salida));

        boolean resultado = Formulario.validar(0, 10, 1, true);

        System.setOut(originalOut);
        assertFalse(resultado);
        assertTrue(salida.toString().contains("Por favor elegir entre las opciones dadas"));
    }

    @Test
    void testDoubleFueraDelRangoImprimeMensajeSinOpciones() {
        ByteArrayOutputStream salida = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(salida));

        boolean resultado = Formulario.validar(12.5, 10.0, 1.0, false);

        System.setOut(originalOut);
        assertFalse(resultado);
        assertTrue(salida.toString().contains("Por favor ingresar un número entre"));
    }

    @Test
    void testDoubleFueraDelRangoImprimeMensajeConOpciones() {
        ByteArrayOutputStream salida = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(salida));

        boolean resultado = Formulario.validar(-5.0, 10.0, 1.0, true);

        System.setOut(originalOut);
        assertFalse(resultado);
        assertTrue(salida.toString().contains("Por favor elegir entre las opciones dadas"));
    }

    @Test
    void testFormularioConInputInvalido() {
        // Simula una entrada no numérica que cause InputMismatchException
        String entradaSimulada = "texto\n";
        ByteArrayInputStream entrada = new ByteArrayInputStream(entradaSimulada.getBytes());
        System.setIn(entrada);

        ByteArrayOutputStream salida = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(salida));

        double resultado = Formulario.formulario();

        System.setIn(System.in);
        System.setOut(originalOut);

        assertEquals(-1, resultado);
        assertTrue(salida.toString().contains("Entrada inválida"));
    }

    @Test
    void testValidarLimiteInferiorNegativo() {
        // Cubre rama con valores negativos
        assertFalse(Formulario.validar(-5, 10, 0, false));
    }

    @Test
    void testValidarDoubleMuyGrande() {
        // Cubre validación con double fuera del rango superior
        assertFalse(Formulario.validar(9999.9, 500.0, 0.0, false));
    }
}






