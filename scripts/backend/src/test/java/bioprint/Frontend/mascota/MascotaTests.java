package bioprint.Frontend.mascota;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import javafx.application.Platform;

import static org.junit.jupiter.api.Assertions.*;

class MascotaTests {

    @BeforeAll
    static void initJavaFX() {
        // Inicializa JavaFX para los tests (necesario para Label/ImageView)
        try {
            Platform.startup(() -> {});
        } catch (IllegalStateException e) {
            // JavaFX ya inicializado
        }
    }

    // -------- AnalizadorRespuestas --------

    @Test
    void testEvaluarPuntajeBajo() {
        assertEquals("bajo", AnalizadorRespuestas.evaluarPuntaje(10));
    }

    @Test
    void testEvaluarPuntajeMedio() {
        assertEquals("medio", AnalizadorRespuestas.evaluarPuntaje(50));
    }

    @Test
    void testEvaluarPuntajeAlto() {
        assertEquals("alto", AnalizadorRespuestas.evaluarPuntaje(90));
    }

    // -------- Recomendacion --------

    @Test
    void testObtenerRecomendacionesNivelBajo() {
        String[] recs = Recomendacion.obtenerRecomendaciones("bajo");
        assertTrue(recs.length >= 3);
        assertTrue(recs[0].contains("luces"));
    }

    @Test
    void testObtenerRecomendacionesNivelDesconocido() {
        String[] recs = Recomendacion.obtenerRecomendaciones("xyz");
        assertArrayEquals(new String[]{"No hay recomendaciones disponibles"}, recs);
    }

    // -------- MascotaController --------

    @Test
    void testMostrarMascotaYRecomendaciones() {
        MascotaController controller = new MascotaController();

        // Inyectamos componentes JavaFX falsos
        Label label = new Label();
        ImageView imageView = new ImageView();
        controller.mensajeLabel = label;
        controller.mascotaImg = imageView;

        // Inicializa el controlador (establece imagen inicial)
        assertDoesNotThrow(controller::initialize);
        assertNotNull(controller.mascotaImg.getImage());

        // Simula mostrar recomendaciones varias veces
        controller.mostrarRecomendaciones();
        controller.mostrarRecomendaciones();
        assertTrue(controller.mensajeLabel.getText().length() > 0);
    }

    @Test
    void testRecomendacionesAgotadasNoFalla() {
        MascotaController controller = new MascotaController();
        controller.mensajeLabel = new Label();
        controller.mascotaImg = new ImageView();

        controller.initialize();
        for (int i = 0; i < 5; i++) {
            controller.mostrarRecomendaciones();
        }

        assertNotNull(controller.mensajeLabel.getText());
    }
}
