package bioprint.metricas;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias y de integración para AnalizadorCalidad.
 * Cubre los métodos principales, flujos normales y de error
 * 
 */
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

    // ---------------------------------------------------------
    // Pruebas de calcularComplejidadCiclomatica()
    // ---------------------------------------------------------

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
    void testCalcularComplejidadCiclomatica_ErrorDeParseo() throws IOException {
        String code = "public class X { void m( { }"; // código inválido
        Files.writeString(tempFile.toPath(), code);

        IOException ex = assertThrows(IOException.class, () ->
                AnalizadorCalidad.calcularComplejidadCiclomatica(tempFile));
        assertTrue(ex.getMessage().contains("No se pudo analizar"),
                "Debe lanzar IOException al no poder analizar el archivo");
    }

    // ---------------------------------------------------------
    // Pruebas de calcularMantenibilidad()
    // ---------------------------------------------------------

    @Test
    void testCalcularMantenibilidad() throws IOException {
        String code = "// comentario\n/* otro */\npublic class C { void m(){} }";
        Files.writeString(tempFile.toPath(), code);

        int cc = 1;
        double mantenibilidad = AnalizadorCalidad.calcularMantenibilidad(tempFile, cc);
        assertTrue(mantenibilidad > 0, "El índice debe ser positivo");
    }

    @Test
    void testCalcularMantenibilidad_ArchivoVacio() throws IOException {
        Files.writeString(tempFile.toPath(), "");
        double result = AnalizadorCalidad.calcularMantenibilidad(tempFile, 1);
        assertTrue(Double.isFinite(result),
                "Debe devolver un valor numérico aunque el archivo esté vacío");
    }

    // ---------------------------------------------------------
    // Pruebas de interpretación
    // ---------------------------------------------------------

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

    // ---------------------------------------------------------
    // Pruebas de método main()
    // ---------------------------------------------------------

    @Test
    void testMainCarpetaInexistente() {
        java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(out));

        assertDoesNotThrow(() -> AnalizadorCalidad.main(new String[]{}));
        String output = out.toString();
        assertTrue(output.contains("ANÁLISIS DE CALIDAD") || output.contains("No se encontró"),
                "Debe imprimir un mensaje indicando el resultado del análisis o la falta del directorio");
    }

    @Test
    void testMainConArchivoJava() throws IOException {
        // Crear carpeta temporal con archivo .java
        File dir = Files.createTempDirectory("bioprint_test").toFile();
        File javaFile = new File(dir, "Ejemplo.java");
        Files.writeString(javaFile.toPath(), "public class Ejemplo { void m(){ if(true){} } }");

        // Crear estructura esperada para el main
        File src = new File("src/main/java/bioprint");
        src.mkdirs();
        Files.writeString(new File(src, "Dummy.java").toPath(), "class D {}");

        java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(out));

        assertDoesNotThrow(() -> AnalizadorCalidad.main(new String[]{}));

        String output = out.toString();
        assertTrue(output.contains("Complejidad ciclomática") ||
                   output.contains("Índice de mantenibilidad"),
                   "Debe imprimir resultados del análisis");
    }
}
