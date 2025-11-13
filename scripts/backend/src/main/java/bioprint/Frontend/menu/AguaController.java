package bioprint.Frontend.menu;
import bioprint.ModuloCalculadora.GrupoFuentes;
import bioprint.ModuloCalculadora.FuenteHuellaFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import bioprint.BioPrintApp;

@Component
public class AguaController {

    @FXML private ChoiceBox<String> duchasPorDiaChoice;
    @FXML private TextField duracionDuchaField;
    @FXML private ChoiceBox<String> ahorradoresChoice;
    @FXML private Label mensajeLabel;
    @FXML private Button volverBtn;
    @FXML private Button siguienteBtn;
    @FXML private Circle imagenCircular;

    @Autowired
    private SesionFormulario sesion;

    @FXML
    public void initialize() {
        duchasPorDiaChoice.getItems().addAll("1", "2", "3", "Más de 3");
        ahorradoresChoice.getItems().addAll("Sí", "No");

        mensajeLabel.setText("¿Qué tal te fue en el consumo de agua?");
        try {
            Image img = new Image(getClass().getResource("/references/feliz.png").toExternalForm());
            imagenCircular.setFill(new ImagePattern(img));
        } catch (Exception e) {
            System.out.println("No se pudo cargar la imagen: " + e.getMessage());
        }
    }

    @FXML
    private void onSiguiente() {
        try {
            // Validar campos vacíos
            if (duchasPorDiaChoice.getValue() == null ||
                duracionDuchaField.getText().isEmpty() ||
                ahorradoresChoice.getValue() == null) {

                mensajeLabel.setText("Por favor completa todas las respuestas.");
                return;
            }

            // --- 1️⃣ Leer los valores del formulario ---
            int duchas;
            if (duchasPorDiaChoice.getValue().equals("Más de 3")) {
                duchas = 4; // Valor representativo para "más de 3"
            } else {
                duchas = Integer.parseInt(duchasPorDiaChoice.getValue());
            }

            int duracion = Integer.parseInt(duracionDuchaField.getText());
            boolean tieneAhorradores = ahorradoresChoice.getValue().equalsIgnoreCase("Sí");

            // --- 2️⃣ Validaciones ---
            if (duchas < 0 || duchas > 5) {
                mensajeLabel.setText("El número de duchas debe estar entre 0 y 5.");
                return;
            }

            if (duracion < 1 || duracion > 60) {
                mensajeLabel.setText("La duración de la ducha debe estar entre 1 y 60 minutos.");
                return;
            }

            // --- 3️⃣ Cálculo de consumo de agua ---
            int personas = sesion.getPersonas(); 
            double consumoDucha = duchas * duracion * personas * (tieneAhorradores ? 0.8 : 1.0);
            double extraAgua = consumoDucha * 0.05;

            // --- 4️⃣ Guardar resultados en la sesión ---
            sesion.setDuchasPorDia(String.valueOf(duchas));
            sesion.setDuracionDucha(String.valueOf(duracion));
            sesion.setTieneAhorradores(tieneAhorradores ? "Sí" : "No");
            sesion.setConsumoAgua(consumoDucha);
            sesion.setExtraAgua(extraAgua);

            // Si tienes un grupo o calculadora general:
            // grupo.addFuente(FuenteHuellaFactory.crearFuente(0, 0, extraAgua));

            // --- 5️⃣ Cambiar de escena ---
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/PreguntasHuella4.fxml"));
            loader.setControllerFactory(BioPrintApp.getContext()::getBean);
            Parent root = loader.load();

            Stage stage = (Stage) siguienteBtn.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (NumberFormatException e) {
            mensajeLabel.setText("Por favor ingresa números válidos.");
        } catch (Exception e) {
            e.printStackTrace();
            mensajeLabel.setText("Error al avanzar: " + e.getMessage());
        }
    }

    @FXML
    private void onVolver() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/PreguntasHuella2.fxml"));
            loader.setControllerFactory(BioPrintApp.getContext()::getBean);
            Parent root = loader.load();

            Stage stage = (Stage) volverBtn.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            mensajeLabel.setText("Error al volver: " + e.getMessage());
        }
    }
}
