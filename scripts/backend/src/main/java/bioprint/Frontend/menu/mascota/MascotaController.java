package bioprint.Frontend.menu.mascota;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.springframework.stereotype.Component;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;
@Component

public class MascotaController {

    @FXML
    private Label mensajeLabel;

    @FXML
    private ImageView mascotaImg;

    private String nivelActual = "medio";
    private int mensajeIndex = 0;

    private final String basePath = "scripts/backend/src/main/resources/references";

    public void initialize() {
        mostrarMascota();
        mensajeLabel.setText("");
    }

    private void mostrarMascota() {

        String fileName;

        if (nivelActual.equals("bajo")) fileName = "marchito.png";
        else if (nivelActual.equals("medio")) fileName = "triste.png"; 
        else fileName = "feliz.png";

        File file = new File(basePath + fileName);
        mascotaImg.setImage(new Image(file.toURI().toString()));
    }

    @FXML
    private void mostrarRecomendaciones() {
        String[] frases = Recomendacion.obtenerRecomendaciones(nivelActual);
        if (mensajeIndex < frases.length) {
            if (!mensajeLabel.getText().isEmpty())
                mensajeLabel.setText(mensajeLabel.getText() + "\n" + frases[mensajeIndex]);
            else
                mensajeLabel.setText(frases[mensajeIndex]);

            mensajeIndex++;
        }
    }
}


