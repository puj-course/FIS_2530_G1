import static org.junit.jupiter.api.Assertions.*;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.junit.jupiter.api.*;
import org.testfx.framework.junit5.ApplicationTest;

public class FormularioControllerTest extends ApplicationTest {

    private Stage stage;
    private FormularioController controller;

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        controller = new FormularioController(stage, "testUser");
    }

    @Test
    public void testConstructorAndPublicField() {
        assertNotNull(controller.grupo, "grupo should be initialized");
        assertEquals("testUser", controller.nombreUsuario);
        assertEquals(stage, controller.stage);
    }

    @Test
    public void testMostrarPantallaDatosGenerales_validInput() {
        // Show screen
        interact(() -> controller.mostrarPantallaDatosGenerales());

        TextField input = lookup(".text-field").query();
        Button next = lookup(".button").query();
        Label errorLabel = lookup(labelWithTextStarting("Ingrese")).queryAllAs(Label.class).stream().findFirst().orElse(null);

        // Valid input
        interact(() -> input.setText("3"));
        interact(() -> next.fire());

        // After pressing next with valid input, personas should be 3
        assertEquals(3, controller.personas);

        // Stage should now be showing the next screen (Electricidad)
        assertTrue(stage.getScene().getRoot() instanceof javafx.scene.layout.GridPane);
    }

    @Test
    public void testMostrarPantallaDatosGenerales_invalidInput() {
        interact(() -> controller.mostrarPantallaDatosGenerales());

        TextField input = lookup(".text-field").query();
        Button next = lookup(".button").query();
        Label errorLabel = (Label) lookup(labelWithTextStarting("Ingrese")).tryQuery().orElse(null);

        // Invalid non-numeric input
        interact(() -> input.setText("abc"));
        interact(() -> next.fire());
        assertEquals("Ingrese un número válido", errorLabel.getText());

        // Out of range input
        interact(() -> input.setText("200"));
        interact(() -> next.fire());
        assertEquals("Ingrese un número entre 1 y 100", errorLabel.getText());
    }

    @Test
    public void testFullFormFlow() {
        interact(() -> controller.mostrarPantallaDatosGenerales());

        // Step 1: Datos Generales
        TextField personasInput = lookup(".text-field").query();
        Button nextButton = lookup(".button").query();
        interact(() -> personasInput.setText("3"));
        interact(() -> nextButton.fire());
        assertEquals(3, controller.personas);

        // Step 2: Electricidad
        ComboBox<String> fuenteCombo = lookup(".combo-box").query();
        TextField luzInput = lookupAll(".text-field").stream().filter(tf -> tf.isVisible()).toList().get(0);
        TextField gasInput = lookupAll(".text-field").stream().filter(tf -> tf.isVisible()).toList().get(1);
        TextField aguaInput = lookupAll(".text-field").stream().filter(tf -> tf.isVisible()).toList().get(2);
        Button nextElectricidad = lookup(".button").query();

        interact(() -> {
            luzInput.setText("100");
            gasInput.setText("50");
            aguaInput.setText("30");
            fuenteCombo.getSelectionModel().select("Solar");
        });
        interact(() -> nextElectricidad.fire());

        assertEquals(100, controller.luz, 0.01);
        assertEquals(50, controller.gas, 0.01);
        assertEquals(30, controller.agua, 0.01);
        assertEquals(2, controller.fuenteEnergia); // Solar = index 1 +1

        // Step 3: Agua
        TextField duchasInput = lookupAll(".text-field").stream().filter(tf -> tf.isVisible()).toList().get(0);
        TextField duracionInput = lookupAll(".text-field").stream().filter(tf -> tf.isVisible()).toList().get(1);
        TextField ahorradorInput = lookupAll(".text-field").stream().filter(tf -> tf.isVisible()).toList().get(2);
        Button nextAgua = lookup(".button").query();

        interact(() -> {
            duchasInput.setText("2");
            duracionInput.setText("10");
            ahorradorInput.setText("1");
        });
        interact(() -> nextAgua.fire());

        assertEquals(2, controller.duchas);
        assertEquals(10, controller.duracionDucha);
        assertEquals(1, controller.ahorrador);

        // Step 4: Transporte
        ComboBox<String> transporteCombo = lookupAll(".combo-box").get(0);
        TextField kmInput = lookupAll(".text-field").stream().filter(tf -> tf.isVisible()).toList().get(0);
        TextField diasInput = lookupAll(".text-field").stream().filter(tf -> tf.isVisible()).toList().get(1);
        ComboBox<String> vuelosCombo = lookupAll(".combo-box").get(1);
        Button nextTransporte = lookup(".button").query();

        interact(() -> {
            transporteCombo.getSelectionModel().select("Carro gasolina");
            kmInput.setText("15");
            diasInput.setText("5");
            vuelosCombo.getSelectionModel().select("1-2");
        });
        interact(() -> nextTransporte.fire());

        assertEquals(1, controller.tipoTransporte);
        assertEquals(2, controller.vuelos);

        // Step 5: Alimentación
        ComboBox<String> dietaCombo = lookupAll(".combo-box").get(0);
        ComboBox<String> carneCombo = lookupAll(".combo-box").get(1);
        ComboBox<String> lacteosCombo = lookupAll(".combo-box").get(2);
        ComboBox<String> origenCombo = lookupAll(".combo-box").get(3);
        Button finishButton = lookup(".button").query();

        interact(() -> {
            dietaCombo.getSelectionModel().select("Omnívora");
            carneCombo.getSelectionModel().select("3-5/semana");
            lacteosCombo.getSelectionModel().select("1-2/semana");
            origenCombo.getSelectionModel().select("Locales");
        });
        interact(() -> finishButton.fire());

        assertEquals(3, controller.tipoDieta);
        assertEquals(3, controller.carne);
        assertEquals(2, controller.lacteos);
        assertEquals(1, controller.origen);

        // Step 6: Resultados
        // After finishing, the stage should display results scene
        assertEquals("Resultados - Huella de Carbono", stage.getTitle());
        Label label = lookup(".label").query();
        assertTrue(label.getText().contains("testUser"));
    }

    // Utility method for label lookup by text prefix
    private String labelWithTextStarting(String prefix) {
        return ".label[text^='" + prefix + "']";
    }
}
