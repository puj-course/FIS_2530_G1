package bioprint.Frontend.menu;

import bioprint.ModuloCalculadora.EstrategiaTransporte;
import bioprint.ModuloCalculadora.Transporte;
import bioprint.ModuloCalculadora.FuenteHuella;
import bioprint.ModuloCalculadora.FuenteHuellaFactory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class TransporteController {

    @FXML private ChoiceBox<String> tipoTransporteChoice;
    @FXML private TextField distanciaField;
    @FXML private ChoiceBox<String> diasUsoChoice;
    @FXML private ChoiceBox<String> viajeAvionChoice;
    @FXML private Button volverBtn;
    @FXML private Button siguienteBtn;
    @FXML private Label mensajeLabel;
    @FXML private Circle imagenCircular;

    @Autowired
    private SesionFormulario sesion;

    @FXML
    public void initialize() {
        // Inicialización de opciones visuales
        tipoTransporteChoice.getItems().addAll(
                "Carro gasolina",
                "Carro diésel",
                "Moto",
                "Transporte público",
                "Bicicleta/caminar",
                "Vehículo eléctrico"
        );

        diasUsoChoice.getItems().addAll("1", "2", "3", "4", "5", "6", "7");

        viajeAvionChoice.getItems().addAll(
                "No viajo",
                "1-2 veces",
                "3-5 veces",
                "Más de 5 veces"
        );

        try {
            Image img = new Image(getClass().getResource("/references/feliz.png").toExternalForm());
            imagenCircular.setFill(new ImagePattern(img));
        } catch (Exception e) {
            System.out.println("No se pudo cargar la imagen: " + e.getMessage());
        }
    }

    @FXML
    private void irASiguiente() {
        try {
            // Verificación de campos vacíos
            if (tipoTransporteChoice.getValue() == null ||
                distanciaField.getText().isEmpty() ||
                diasUsoChoice.getValue() == null ||
                viajeAvionChoice.getValue() == null) {

                mensajeLabel.setText("Por favor completa todas las respuestas antes de continuar.");
                return;
            }

            // Validación de distancia
            double distancia;
            try {
                distancia = Double.parseDouble(distanciaField.getText());
                if (!validar(distancia, 1000, 0, false)) {
                    mensajeLabel.setText("La distancia debe estar entre 0 y 1000 km.");
                    return;
                }
            } catch (NumberFormatException e) {
                mensajeLabel.setText("La distancia debe ser un número válido.");
                return;
            }

            // Validación de días
            int dias = Integer.parseInt(diasUsoChoice.getValue());
            if (!validar(dias, 7, 1, false)) {
                mensajeLabel.setText("Los días deben estar entre 1 y 7.");
                return;
            }

            // Validación de tipo de transporte
            int tipoTransporte = mapTransporte(tipoTransporteChoice.getValue());
            if (!validar(tipoTransporte, 6, 1, true)) {
                mensajeLabel.setText("Selecciona un tipo de transporte válido.");
                return;
            }

            // Validación de vuelos
            int vuelos = mapAvion(viajeAvionChoice.getValue());
            if (!validar(vuelos, 4, 0, true)) {
                mensajeLabel.setText("Selecciona una opción válida para los vuelos.");
                return;
            }

            // Estrategia según tipo de transporte
            EstrategiaTransporte estrategia = switch (tipoTransporte) {
                case 1 -> kmDia -> kmDia * 0.21;  // Gasolina
                case 2 -> kmDia -> kmDia * 0.25;  // Diésel
                case 3 -> kmDia -> kmDia * 0.12;  // Moto
                case 4 -> kmDia -> kmDia * 0.09;  // Transporte público
                case 6 -> kmDia -> kmDia * 0.03;  // Vehículo eléctrico
                default -> kmDia -> 0.0;          // Bicicleta/caminar
            };

            // Cálculo de huella de transporte
            Transporte transporte = new Transporte(estrategia, distancia * dias);
            sesion.getGrupo().addFuente(FuenteHuellaFactory.crearFuente(transporte));

            // Cálculo de huella de vuelos
            double huellaVuelos = switch (vuelos) {
                case 1 -> 0;
                case 2 -> 500;
                case 3 -> 1200;
                case 4 -> 2500;
                default -> 0;
            };
            FuenteHuella vuelosFuente = () -> huellaVuelos;
            sesion.getGrupo().addFuente(vuelosFuente);

            // Continuar a la siguiente pantalla
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/PreguntasHuella5.fxml"));
            loader.setControllerFactory(bioprint.BioPrintApp.getContext()::getBean);
            Parent root = loader.load();

            Stage stage = (Stage) siguienteBtn.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            mensajeLabel.setText("Error al avanzar: " + e.getMessage());
        }
    }

    @FXML
    private void volverMenuAnterior() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/PreguntasHuella3.fxml"));
            loader.setControllerFactory(bioprint.BioPrintApp.getContext()::getBean);
            Parent root = loader.load();

            Stage stage = (Stage) volverBtn.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            mensajeLabel.setText("Error al volver: " + e.getMessage());
        }
    }

    // ------------------ MAPEOS ------------------

    private int mapTransporte(String tipo) {
        return switch (tipo) {
            case "Carro gasolina" -> 1;
            case "Carro diésel" -> 2;
            case "Moto" -> 3;
            case "Transporte público" -> 4;
            case "Bicicleta/caminar" -> 5;
            case "Vehículo eléctrico" -> 6;
            default -> 0;
        };
    }

    private int mapAvion(String opcion) {
        return switch (opcion) {
            case "No viajo" -> 1;
            case "1-2 veces" -> 2;
            case "3-5 veces" -> 3;
            case "Más de 5 veces" -> 4;
            default -> 0;
        };
    }

    private boolean validar(double valor, double max, double min, boolean entero) {
        if (valor < min || valor > max) return false;
        if (entero && valor != Math.floor(valor)) return false;
        return true;
    }
}
