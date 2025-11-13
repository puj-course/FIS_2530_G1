package bioprint.Frontend.InicioSesion;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

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

public class LoginController {

    @FXML
    public TextField txtUsuario;

    @FXML
    public PasswordField txtPassword;

    @FXML
    public Circle imagenCircular;

    @FXML
    public void iniciarSesion(ActionEvent event) {
        System.out.println("Botón 'Iniciar sesión' presionado");

        HttpURLConnection con = null;
        try {
            String usuario = txtUsuario.getText().trim();
            String contrasena = txtPassword.getText().trim();

            if (usuario.isEmpty() || contrasena.isEmpty()) {
                mostrarAlerta("Campos vacíos", "Debe ingresar usuario y contraseña");
                return;
            }

            URL url = new URL("http://localhost:8080/usuarios");
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Accept", "application/json");

            int status = con.getResponseCode();

            if (status == 200) {
                Scanner sc = new Scanner(con.getInputStream());
                String json = sc.useDelimiter("\\A").next();
                sc.close();

                boolean encontrado = false;

                String[] usuarios = json.split("\\},\\{");
                for (String u : usuarios) {
                    if (u.contains("\"nombre\":\"" + usuario + "\"") &&
                        u.contains("\"contrasena\":\"" + contrasena + "\"")) {
                        encontrado = true;
                        break;
                    }
                }

                if (encontrado) {
                    mostrarAlerta("Correcto", "Inicio de sesión exitoso");
                    System.out.println("Usuario encontrado en la base de datos.");

                    // 🔹 Cambiar de pantalla al menú
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/InicioSesion/menu.fxml"));
                    Parent root = loader.load();
                    Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.show();

                } else {
                    mostrarAlerta("Error", "Usuario o contraseña incorrecta ");
                    System.out.println("Usuario NO encontrado en la base de datos.");
                }

            } else {
                mostrarAlerta("Error", "No se pudo conectar con el servidor (código " + status + ")");
            }

        } catch (Exception e) {
            mostrarAlerta("Error en la conexión", "Detalles: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (con != null) con.disconnect();
        }
    }

    @FXML
    public void onIrARegistro(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/InicioSesion/Registrarse.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudo abrir la pantalla de registro");
            e.printStackTrace();
        }
    }

    public void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.show();
    }

    @FXML
    public void initialize() {
        try {
            Image img = new Image("/references/feliz.png");
            imagenCircular.setFill(new ImagePattern(img));
        } catch (Exception e) {
            System.out.println("No se pudo cargar la imagen: " + e.getMessage());
            e.printStackTrace();
        }
    }
}




