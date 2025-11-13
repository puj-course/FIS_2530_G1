package bioprint.Frontend.InicioSesion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bioprint.BioPrintApp;
import bioprint.ModuloUsuarios.UsuarioService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

@Component
public class LoginController {

    @Autowired
    private UsuarioService usuarioService;

    @FXML
    private TextField txtUsuario;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Circle imagenCircular;

    @FXML
    private void iniciarSesion(ActionEvent event) {
        String usuario = txtUsuario.getText().trim();
        String contrasena = txtPassword.getText().trim();

        if (usuario.isEmpty() || contrasena.isEmpty()) {
            mostrarAlerta("Campos vacíos", "Debe ingresar usuario y contraseña");
            return;
        }

        // Validar usuario con servicio directamente
        boolean valido = usuarioService.validarUsuario(usuario, contrasena);

        if (valido) {
            mostrarAlerta("Correcto", "Inicio de sesión exitoso");
            System.out.println("Usuario encontrado en la base de datos.");

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/menu.fxml"));
               loader.setControllerFactory(BioPrintApp.getContext()::getBean);
                Parent root = loader.load();
                Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("Menú principal");
                stage.centerOnScreen();
                stage.show();
            } catch (Exception e) {
                mostrarAlerta("Error", "No se pudo cargar el menú");
                e.printStackTrace();
            }

        } else {
            mostrarAlerta("Error", "Usuario o contraseña incorrectos");
            System.out.println("Usuario no encontrado en la base de datos.");
        }
    }

    @FXML
    private void onIrARegistro(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Registrarse.fxml"));
loader.setControllerFactory(BioPrintApp.getContext()::getBean);

            Parent root = loader.load();
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Registro de usuario");
            stage.centerOnScreen();
            stage.show();

        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudo abrir la pantalla de registro");
            e.printStackTrace();
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.show();
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
