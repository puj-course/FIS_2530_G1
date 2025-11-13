package bioprint.Frontend.menu;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

@Component
public class ElectricidadController {

    @FXML private Circle imagenCircular;
    @FXML private Label mensajeLabel;
    @FXML private ChoiceBox<String> fuenteEnergiaChoice;
    @FXML private ChoiceBox<String> bombillosChoice;
    @FXML private ChoiceBox<String> aparatosChoice;
    @FXML private Button volverBtn;
    @FXML private Button siguienteBtn;

    @Autowired
    private SesionFormulario sesion;

    @FXML
    public void initialize() {
        // Cargar opciones
        fuenteEnergiaChoice.getItems().setAll("Hidroeléctrica", "Solar", "Eólica", "Gas/Carbón");
        bombillosChoice.getItems().setAll("Todos", "Algunos", "Ninguno");
        aparatosChoice.getItems().setAll("Nunca", "A veces", "Siempre");

        mensajeLabel.setText("¿Qué tal te fue en el consumo de electricidad?");
        try {
            Image img = new Image(getClass().getResource("/references/feliz.png").toExternalForm());
            imagenCircular.setFill(new ImagePattern(img));
        } catch (Exception e) {
            System.out.println("No se pudo cargar la imagen: " + e.getMessage());
        }
    }

    // --- Método de validación general ---
    private boolean validar(int numero, int max, int min, boolean opciones, String campo) {
        if (numero > max || numero < min) {
            if (opciones) {
                mensajeLabel.setText("Por favor elige una opción válida para " + campo + ".");
            } else {
                mensajeLabel.setText("Por favor ingresa un número válido para " + campo + " (" + min + "-" + max + ").");
            }
            return false;
        }
        return true;
    }

    // --- Botón SIGUIENTE ---
    @FXML
    private void irASiguiente() {
        try {
            // Verificar que el usuario haya elegido todo
            if (fuenteEnergiaChoice.getValue() == null ||
                bombillosChoice.getValue() == null ||
                aparatosChoice.getValue() == null) {
                mensajeLabel.setText("Por favor responde todas las preguntas antes de continuar.");
                return;
            }

            // Convertir elecciones en números (para simular el código de consola)
            int fuenteEnergia = fuenteEnergiaChoice.getSelectionModel().getSelectedIndex() + 1; // 1-4
            int bombillos = bombillosChoice.getSelectionModel().getSelectedIndex() + 1;         // 1-3
            int aparatos = aparatosChoice.getSelectionModel().getSelectedIndex() + 1;           // 1-3

            // Validaciones tipo consola
            if (!validar(fuenteEnergia, 4, 1, true, "fuente de energía")) return;
            if (!validar(bombillos, 3, 1, true, "uso de bombillos")) return;
            if (!validar(aparatos, 3, 1, true, "uso de aparatos")) return;

            // --- Cálculo del ajuste de energía ---
            double ajusteEnergia = 1.0;
            if (fuenteEnergia == 4) ajusteEnergia += 0.2; // Gas/Carbón
            if (bombillos == 3) ajusteEnergia += 0.1;     // Ninguno
            if (aparatos == 3) ajusteEnergia += 0.1;      // Siempre enchufados

            // Recuperar el consumo de luz base desde la sesión (de PreguntasHuella1)
            double luzBase = sesion.getConsumoLuz();
            double luzAjustada = luzBase * ajusteEnergia;

            // Guardar en la sesión
            sesion.setFuenteEnergia(fuenteEnergiaChoice.getValue());
            sesion.setUsaLed(bombillosChoice.getValue());
            sesion.setDejaAparatos(aparatosChoice.getValue());
            sesion.setConsumoLuzAjustado(luzAjustada); // puedes agregar este campo en la clase SesionFormulario

            cambiarEscena("/PreguntasHuella3.fxml");

        } catch (Exception ex) {
            ex.printStackTrace();
            mensajeLabel.setText("Error al avanzar: " + ex.getMessage());
        }
    }

    @FXML
    private void volverMenuAnterior() {
        try {
            cambiarEscena("/PreguntasHuella1.fxml");
        } catch (Exception ex) {
            ex.printStackTrace();
            mensajeLabel.setText("Error al volver: " + ex.getMessage());
        }
    }

    private void cambiarEscena(String ruta) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(ruta));
        loader.setControllerFactory(bioprint.BioPrintApp.getContext()::getBean);
        Parent root = loader.load();

        Stage stage = (Stage) siguienteBtn.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
