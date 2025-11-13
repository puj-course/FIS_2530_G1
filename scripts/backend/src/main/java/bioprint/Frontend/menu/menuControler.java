package bioprint.Frontend.menu;

import org.springframework.stereotype.Component;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import bioprint.BioPrintApp;
import javafx.scene.Node; 
import javafx.scene.shape.Circle;

import java.io.IOException;

@Component
public class menuControler {

    @FXML
    private Circle imagenCircular;

    @FXML
    private void onIrAMascota(ActionEvent event) {
        cambiarEscena(event, "/MascotaView.fxml", "Recomendación de Mascota");
    }

    @FXML
    private void onIrAHuella(ActionEvent event) {
        cambiarEscena(event, "/PreguntasHuella1.fxml", "Huella de Carbono");
    }

    private void cambiarEscena(ActionEvent event, String fxmlPath, String tituloVentana) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            loader.setControllerFactory(BioPrintApp.getContext()::getBean);
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle(tituloVentana);
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error al cargar: " + fxmlPath);
        }
    }

    @FXML
    private void onExit(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    public void initialize() {
        try {
            Image img = new Image(getClass().getResource("/references/feliz.png").toExternalForm());
            imagenCircular.setFill(new ImagePattern(img));
        } catch (Exception e) {
            System.out.println("No se pudo cargar la imagen: " + e.getMessage());
        }
    }
}

