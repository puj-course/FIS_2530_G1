package bioprint.Frontend.InicioSesion;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Pattern;

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

public class RegistroController {

    @FXML
    public TextField txtNombre;

    @FXML
    public TextField txtEmail;

    @FXML
    public PasswordField txtContrasena;

    @FXML
    public PasswordField txtConfirmarContrasena;


    @FXML
    public void registrarUsuario(ActionEvent event) {
        String nombre = txtNombre.getText().trim();
        String email = txtEmail.getText().trim();
        String contrasena = txtContrasena.getText();
        String confirmar = txtConfirmarContrasena.getText();

        if (nombre.isEmpty() || email.isEmpty() || contrasena.isEmpty() || confirmar.isEmpty()) {
            mostrarAlerta("Error", "Todos los campos son obligatorios.");
            return;
        }

        if (!esEmailValido(email)) {
            mostrarAlerta("Error", "Por favor ingresa un correo electrónico válido.");
            return;
        }


        if (!esContrasenaSegura(contrasena)) {
            mostrarAlerta("Error", "La contraseña debe tener al menos 8 caracteres, una mayúscula, un número y un carácter especial.");
            return;
        }

        if (!contrasena.equals(confirmar)) {
            mostrarAlerta("Error", "Las contraseñas no coinciden.");
            return;
        }

        boolean exito = guardarUsuario(nombre, contrasena);

        if (exito) {
            mostrarAlerta("Éxito", "Usuario registrado correctamente.");
            limpiarCampos();

        } else {
            mostrarAlerta("Error", "Hubo un problema al registrar el usuario.");
        }
    }
   public boolean guardarUsuario(String nombre, String contrasena) {
    try {
        URL url = new URL("http://localhost:8080/usuarios");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/json");

        String jsonInput = String.format("{\"nombre\":\"%s\", \"contrasena\":\"%s\"}", nombre, contrasena);

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonInput.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int responseCode = conn.getResponseCode();
        System.out.println("Código de respuesta: " + responseCode);

        return (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED);

    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}

    public boolean esEmailValido(String email) {
        String patronEmail = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return Pattern.matches(patronEmail, email);
    }

    public boolean esContrasenaSegura(String contrasena) {
        String patronContrasena = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,}$";
        return Pattern.matches(patronContrasena, contrasena);
    }


    public void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public void limpiarCampos() {
        txtNombre.clear();
        txtEmail.clear();
        txtContrasena.clear();
        txtConfirmarContrasena.clear();
    }

    @FXML
    public void onVolver(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
 @FXML
    public Circle imagenMascota;

    @FXML
    public void initialize() {
        try {
            Image img = new Image("/references/feliz.png");
            imagenMascota.setFill(new ImagePattern(img));
        } catch (Exception e) {
            System.out.println("No se pudo cargar la imagen: " + e.getMessage());
            e.printStackTrace();
        }
    }
}