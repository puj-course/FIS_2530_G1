package bioprint.Frontend.menu;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class GeneralPreguntasController {

    @FXML private Circle imagenCircular;
    @FXML private TextField txtElectricidad;  
    @FXML private TextField txtGas;           
    @FXML private TextField txtAgua;
    @FXML private TextField txtPersonas;
    @FXML private Label mensajeLabel;

    @FXML private Button volverButton;
    @FXML private Button siguienteButton;

    @Autowired
    private SesionFormulario sesion;

    @FXML
    public void initialize() {
        mensajeLabel.setText("Respondamos primero unas preguntas generales.");
        try {
            Image img = new Image(getClass().getResource("/references/feliz.png").toExternalForm());
            imagenCircular.setFill(new ImagePattern(img));
        } catch (Exception e) {
            System.out.println("No se pudo cargar la imagen: " + e.getMessage());
        }
    }

    // --- MÉTODO DE VALIDACIÓN ---
    private boolean validar(double numero, double max, double min, boolean opciones, String campo) {
        if (numero > max || numero < min) {
            if (opciones) {
                mensajeLabel.setText("Por favor elige entre las opciones dadas.");
            } else {
                mensajeLabel.setText("Por favor ingresa un valor válido para " + campo + " entre " + (int)min + " y " + (int)max + ".");
            }
            return false;
        }
        return true;
    }

    // --- BOTÓN SIGUIENTE ---
    @FXML
    private void onSiguiente(ActionEvent event) {
        try {
            // Validar que no haya campos vacíos
            if (txtPersonas.getText().isEmpty() || txtElectricidad.getText().isEmpty() ||
                txtGas.getText().isEmpty() || txtAgua.getText().isEmpty()) {
                mensajeLabel.setText("Por favor completa todos los campos.");
                return;
            }

            // Parsear valores
            int personas = Integer.parseInt(txtPersonas.getText());
            double electricidad = Double.parseDouble(txtElectricidad.getText());
            double gas = Double.parseDouble(txtGas.getText());
            double agua = Double.parseDouble(txtAgua.getText());

            // Validaciones numéricas (imitando la consola)
            if (!validar(personas, 100, 1, false, "personas")) return;
            if (!validar(electricidad, 1000, 1, false, "electricidad")) return;
            if (!validar(gas, 10000, 1, false, "gas")) return;
            if (!validar(agua, 100, 1, false, "agua")) return;

            // Guardar en la sesión
            sesion.setPersonas(personas);
            sesion.setConsumoLuz(electricidad);
            sesion.setConsumoGas(gas);
            sesion.setConsumoAgua(agua);

            // Cambiar escena
            cambiarEscena("/PreguntasHuella2.fxml", event);

        } catch (NumberFormatException e) {
            mensajeLabel.setText("Por favor ingresa solo números válidos en los campos.");
        } catch (Exception e) {
            mensajeLabel.setText("Error al avanzar: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void onVolver(ActionEvent event) {
        cambiarEscena("/menu.fxml", event); 
    }

    private void cambiarEscena(String ruta, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(ruta));
            loader.setControllerFactory(bioprint.BioPrintApp.getContext()::getBean);

            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            mensajeLabel.setText("No se pudo cargar la vista: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
