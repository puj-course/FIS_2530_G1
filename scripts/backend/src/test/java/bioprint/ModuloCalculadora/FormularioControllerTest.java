package bioprint.modulocalculadora;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import org.junit.jupiter.api.*;
import org.testfx.framework.junit5.*;

import bioprint.modulocalculadora.FormularioController;

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
        // Step 0: Show the first screen
        interact(() -> controller.mostrarPantallaDatosGenerales());

        // ---------------- Step 1: Datos Generales ----------------
        TextField personasInput = lookup(".text-field").queryAs(TextField.class);
        Button nextButton = lookup(".button").queryAs(Button.class);

        interact(() -> personasInput.setText("3"));
        interact(nextButton::fire);

        assertEquals(3, controller.personas);

        // ---------------- Step 2: Electricidad ----------------
        List<TextField> textFieldsStep2 = lookup(".text-field").queryAllAs(TextField.class).stream()
                .filter(TextField::isVisible)
                .toList();
        TextField luzInput = textFieldsStep2.get(0);
        TextField gasInput = textFieldsStep2.get(1);
        TextField aguaInput = textFieldsStep2.get(2);

        ComboBox<String> fuenteCombo = lookup(".combo-box").queryAs(ComboBox.class);
        Button nextElectricidad = lookup(".button").queryAs(Button.class);

        interact(() -> {
            luzInput.setText("100");
            gasInput.setText("50");
            aguaInput.setText("30");
            fuenteCombo.getSelectionModel().select("Solar");
        });
        interact(nextElectricidad::fire);

        assertEquals(100, controller.luz, 0.01);
        assertEquals(50, controller.gas, 0.01);
        assertEquals(30, controller.agua, 0.01);
        assertEquals(2, controller.fuenteEnergia); // Assuming Solar = index 1 +1

        // ---------------- Step 3: Agua ----------------
        List<TextField> textFieldsStep3 = lookup(".text-field").queryAllAs(TextField.class).stream()
                .filter(TextField::isVisible)
                .toList();
        TextField duchasInput = textFieldsStep3.get(0);
        TextField duracionInput = textFieldsStep3.get(1);
        TextField ahorradorInput = textFieldsStep3.get(2);

        Button nextAgua = lookup(".button").queryAs(Button.class);

        interact(() -> {
            duchasInput.setText("2");
            duracionInput.setText("10");
            ahorradorInput.setText("1");
        });
        interact(nextAgua::fire);

        assertEquals(2, controller.duchas);
        assertEquals(10, controller.duracionDucha);
        assertEquals(1, controller.ahorrador);

        // ---------------- Step 4: Transporte ----------------
        List<ComboBox> comboBoxesStep4 = lookup(".combo-box").queryAllAs(ComboBox.class).stream()
                .filter(c -> c.isVisible())
                .toList();
        ComboBox<String> transporteCombo = comboBoxesStep4.get(0);
        ComboBox<String> vuelosCombo = comboBoxesStep4.get(1);

        List<TextField> textFieldsStep4 = lookup(".text-field").queryAllAs(TextField.class).stream()
                .filter(TextField::isVisible)
                .toList();
        TextField kmInput = textFieldsStep4.get(0);
        TextField diasInput = textFieldsStep4.get(1);

        Button nextTransporte = lookup(".button").queryAs(Button.class);

        interact(() -> {
            transporteCombo.getSelectionModel().select("Carro gasolina");
            kmInput.setText("15");
            diasInput.setText("5");
            vuelosCombo.getSelectionModel().select("1-2");
        });
        interact(nextTransporte::fire);

        assertEquals(1, controller.tipoTransporte);
        assertEquals(2, controller.vuelos);

        // ---------------- Step 5: Alimentación ----------------
        List<ComboBox> comboBoxesStep5 = lookup(".combo-box").queryAllAs(ComboBox.class).stream()
                .filter(c -> c.isVisible())
                .toList();
        ComboBox<String> dietaCombo = comboBoxesStep5.get(0);
        ComboBox<String> carneCombo = comboBoxesStep5.get(1);
        ComboBox<String> lacteosCombo = comboBoxesStep5.get(2);
        ComboBox<String> origenCombo = comboBoxesStep5.get(3);

        Button finishButton = lookup(".button").queryAs(Button.class);

        interact(() -> {
            dietaCombo.getSelectionModel().select("Omnívora");
            carneCombo.getSelectionModel().select("3-5/semana");
            lacteosCombo.getSelectionModel().select("1-2/semana");
            origenCombo.getSelectionModel().select("Locales");
        });
        interact(finishButton::fire);

        assertEquals(3, controller.tipoDieta);
        assertEquals(3, controller.carne);
        assertEquals(2, controller.lacteos);
        assertEquals(1, controller.origen);

        // ---------------- Step 6: Resultados ----------------
        assertEquals("Resultados - Huella de Carbono", stage.getTitle());
        Label label = lookup(".label").queryAs(Label.class);
        assertTrue(label.getText().contains("testUser"));
    }

    // Utility method for label lookup by text prefix
    private String labelWithTextStarting(String prefix) {
        return ".label[text^='" + prefix + "']";
    }
}
