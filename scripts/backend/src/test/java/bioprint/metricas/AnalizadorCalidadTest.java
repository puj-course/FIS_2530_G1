package bioprint.metricas;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AnalizadorCalidadTest {

    private File tempFile;

    @BeforeEach
    void setUp() throws IOException {
        tempFile = File.createTempFile("test", ".java");
    }

    @AfterEach
    void tearDown() {
        if (tempFile.exists()) tempFile.delete();
    }

    @Test
    void testCalcularComplejidadCiclomatica_Simple() throws IOException {
        String code = "public class A { void m(){ int x=0; if(x>0){x++;} } }";
        Files.writeString(tempFile.toPath(), code);

        int result = AnalizadorCalidad.calcularComplejidadCiclomatica(tempFile);
        assertEquals(2, result, "Debe detectar una complejidad de 2 (una rama if + base)");
    }

    @Test
    void testCalcularComplejidadCiclomatica_Compleja() throws IOException {
        String code = "public class B { void m(){ for(int i=0;i<3;i++){if(i>0){while(i<5){i++;}}} } }";
        Files.writeString(tempFile.toPath(), code);

        int result = AnalizadorCalidad.calcularComplejidadCiclomatica(tempFile);
        assertTrue(result >= 4, "Debe detectar múltiples estructuras de control");
    }

    @Test
    void testCalcularMantenibilidad() throws IOException {
        String code = "// comentario\n/* otro */\npublic class C { void m(){} }";
        Files.writeString(tempFile.toPath(), code);

        int cc = 1;
        double mantenibilidad = AnalizadorCalidad.calcularMantenibilidad(tempFile, cc);

        assertTrue(mantenibilidad > 0, "El índice debe ser positivo");
    }

    @Test
    void testInterpretarComplejidad() {
        assertTrue(AnalizadorCalidad.interpretarComplejidad(3).contains("Baja"));
        assertTrue(AnalizadorCalidad.interpretarComplejidad(8).contains("Moderada"));
        assertTrue(AnalizadorCalidad.interpretarComplejidad(15).contains("Alta"));
        assertTrue(AnalizadorCalidad.interpretarComplejidad(25).contains("Muy alta"));
    }

    @Test
    void testInterpretarMantenibilidad() {
        assertTrue(AnalizadorCalidad.interpretarMantenibilidad(220).contains("Excelente"));
        assertTrue(AnalizadorCalidad.interpretarMantenibilidad(160).contains("Buena"));
        assertTrue(AnalizadorCalidad.interpretarMantenibilidad(120).contains("Aceptable"));
        assertTrue(AnalizadorCalidad.interpretarMantenibilidad(80).contains("Deficiente"));
    }
}
