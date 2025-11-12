package bioprint.Frontend.mascota;

import java.io.File;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MascotaController {

    @FXML
    public Label mensajeLabel;

    @FXML
    public ImageView mascotaImg;

    public String nivelActual = "medio";
    public int mensajeIndex = 0;

    public final String basePath = "scripts/backend/src/main/resources/references";

    public void initialize() {
        mostrarMascota();
        mensajeLabel.setText("");
    }

    public void mostrarMascota() {

        String fileName;

        if (nivelActual.equals("bajo")) fileName = "marchito.png";
        else if (nivelActual.equals("medio")) fileName = "triste.png"; 
        else fileName = "feliz.png";

        File file = new File(basePath + fileName);
        mascotaImg.setImage(new Image(file.toURI().toString()));
    }

    @FXML
    public void mostrarRecomendaciones() {
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


