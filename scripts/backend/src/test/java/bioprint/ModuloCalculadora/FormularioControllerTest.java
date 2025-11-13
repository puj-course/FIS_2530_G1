package bioprint.ModuloCalculadora;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FormularioControllerTest {

    @BeforeAll
    static void initFX() {
        // Inicializa JavaFX Toolkit para que no falle por falta de UI Thread
        new JFXPanel();
    }

    @Test
    void testCrearGridYAgregarGrid() {
        FormularioController controller = new FormularioController(new Stage(), "usuario");
        GridPane grid = invokeCrearGrid(controller);

        Label label = new Label("Label");
        TextField input = new TextField();
        Button next = new Button("Next");
        Label error = new Label();

        // Llama método privado con reflexión
        assertDoesNotThrow(() -> {
            var method = FormularioController.class
                .getDeclaredMethod("agregarGrid", GridPane.class, Label.class, TextField.class, Button.class, Label.class);
            method.setAccessible(true);
            method.invoke(controller, grid, label, input, next, error);
        });

        assertTrue(grid.getChildren().contains(label));
        assertTrue(grid.getChildren().contains(next));
    }

    @Test
    void testMostrarPantallaDatosGeneralesConNumeroValido() {
        Stage stage = new Stage();
        FormularioController controller = new FormularioController(stage, "usuario");

        Platform.runLater(() -> {
            assertDoesNotThrow(controller::mostrarPantallaDatosGenerales);
        });
    }

    @Test
    void testValidacionNumeroInvalido() {
        Stage stage = new Stage();
        FormularioController controller = new FormularioController(stage, "usuario");

        Platform.runLater(() -> {
            controller.mostrarPantallaDatosGenerales();

            // Obtiene el scene y simula valores inválidos
            Scene scene = stage.getScene();
            TextField input = (TextField) ((GridPane) scene.getRoot()).getChildren().get(1);
            Button next = (Button) ((GridPane) scene.getRoot()).getChildren().get(2);
            Label error = (Label) ((GridPane) scene.getRoot()).getChildren().get(3);

            input.setText("abc");
            next.fire();

            assertEquals("Ingrese un número válido", error.getText());
        });
    }

    @Test
    void testValidacionNumeroFueraDeRango() {
        Stage stage = new Stage();
        FormularioController controller = new FormularioController(stage, "usuario");

        Platform.runLater(() -> {
            controller.mostrarPantallaDatosGenerales();

            Scene scene = stage.getScene();
            TextField input = (TextField) ((GridPane) scene.getRoot()).getChildren().get(1);
            Button next = (Button) ((GridPane) scene.getRoot()).getChildren().get(2);
            Label error = (Label) ((GridPane) scene.getRoot()).getChildren().get(3);

            input.setText("200");
            next.fire();

            assertEquals("Ingrese un número entre 1 y 100", error.getText());
        });
    }

    // Método auxiliar para acceder a métodos privados
    private GridPane invokeCrearGrid(FormularioController controller) {
        try {
            var method = FormularioController.class.getDeclaredMethod("crearGrid");
            method.setAccessible(true);
            return (GridPane) method.invoke(controller);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
