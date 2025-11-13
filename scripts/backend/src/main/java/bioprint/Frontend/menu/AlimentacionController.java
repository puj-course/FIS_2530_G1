package bioprint.Frontend.menu;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bioprint.BioPrintApp;
import bioprint.ModuloCalculadora.*;

@Component
public class AlimentacionController {

    @FXML private ChoiceBox<String> tipoDietaChoice;
    @FXML private ChoiceBox<String> consumoCarneChoice;
    @FXML private ChoiceBox<String> consumoLacteosChoice;
    @FXML private ChoiceBox<String> productosLocalesChoice;
    @FXML private Label mensajeLabel;
    @FXML private Circle imagenCircular;

    @Autowired
    private SesionFormulario sesion;

    @FXML
    public void initialize() {
        tipoDietaChoice.getItems().addAll("Vegetariana", "Vegana", "Omnívora", "Alta en carnes rojas");
        consumoCarneChoice.getItems().addAll("Nunca", "1-2 veces/semana", "3-5 veces/semana", "Diariamente");
        consumoLacteosChoice.getItems().addAll("Nunca", "1-2 veces/semana", "3-5 veces/semana", "Diariamente");
        productosLocalesChoice.getItems().addAll("Locales", "Mixto", "Importados");

        try {
            Image img = new Image(getClass().getResource("/references/feliz.png").toExternalForm());
            imagenCircular.setFill(new ImagePattern(img));
        } catch (Exception e) {
            System.out.println("No se pudo cargar la imagen: " + e.getMessage());
        }
    }

    @FXML
    private void volverMenuAnterior(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/PreguntasHuella4.fxml"));
            loader.setControllerFactory(BioPrintApp.getContext()::getBean);
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void finalizarCalculo(ActionEvent event) {
        try {
            // Validar que todos los campos tengan valor
            if (tipoDietaChoice.getValue() == null ||
                consumoCarneChoice.getValue() == null ||
                consumoLacteosChoice.getValue() == null ||
                productosLocalesChoice.getValue() == null) {

                mensajeLabel.setText("Por favor completa todas las respuestas.");
                return;
            }

            // Asignar valores a la sesión
            int tipoDieta = tipoDietaChoice.getSelectionModel().getSelectedIndex() + 1;
            int carne = consumoCarneChoice.getSelectionModel().getSelectedIndex() + 1;
            int lacteos = consumoLacteosChoice.getSelectionModel().getSelectedIndex() + 1;
            int origen = productosLocalesChoice.getSelectionModel().getSelectedIndex() + 1;

            sesion.setTipoDieta(tipoDieta);
            sesion.setFrecuenciaCarne(carne);
            sesion.setFrecuenciaLacteos(lacteos);
            sesion.setOrigenProductos(origen);

            // Crear fuente de huella (ejemplo similar al formulario original)
            String dietaStr = switch (tipoDieta) {
                case 1 -> "vegetariana";
                case 2 -> "vegana";
                case 3 -> "omnivora";
                case 4 -> "alta en carne roja";
                default -> "omnivora";
            };

            sesion.addFuente(FuenteHuellaFactory.crearFuente(dietaStr, carne));

            // Calcular huella total (opcional)
            double total = sesion.calcularTotal();
            System.out.println("Huella total calculada: " + total);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/PreguntasFinalizar.fxml"));
            loader.setControllerFactory(BioPrintApp.getContext()::getBean);
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            mensajeLabel.setText("Error al finalizar: " + e.getMessage());
        }
    }
}
