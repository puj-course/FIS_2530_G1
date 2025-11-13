package bioprint.ModuloUsuarios;

import org.springframework.context.ApplicationContext;

import bioprint.BioPrint;
import bioprint.ModuloCalculadora.FormularioController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LoginController extends javafx.application.Application {

    private Stage primaryStage;
    private UsuarioService usuarioService;
    
    private Notificador bot;

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;

        ApplicationContext context = BioPrint.context;
        usuarioService = context.getBean(UsuarioService.class);
        bot = new Notificador();

        mostrarPantallaInicio();
    }

    private void mostrarPantallaInicio() {
    GridPane grid = new GridPane();
    grid.setPadding(new Insets(20));
    grid.setVgap(10);
    grid.setHgap(10);

    Label welcomeLabel = new Label("Bienvenido a BioPrint");
    GridPane.setConstraints(welcomeLabel, 0, 0);

    Button loginButton = new Button("Iniciar Sesión");
    GridPane.setConstraints(loginButton, 0, 1);

    Button registerButton = new Button("Registrarse");
    GridPane.setConstraints(registerButton, 1, 1);

    // 🔹 Botón para salir de la aplicación
    Button exitButton = new Button("Salir");
    GridPane.setConstraints(exitButton, 2, 1);
    exitButton.setOnAction(e -> {
            primaryStage.close(); // Cierra la ventana actual
            System.exit(0);});

    loginButton.setOnAction(e -> mostrarPantallaLogin());
    registerButton.setOnAction(e -> mostrarPantallaRegistro());

    grid.getChildren().addAll(welcomeLabel, loginButton, registerButton, exitButton);

    Scene scene = new Scene(grid, 500, 150);
    primaryStage.setScene(scene);
    primaryStage.setTitle("Bienvenida");
    primaryStage.show();
    }

    private void mostrarPantallaLogin() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setVgap(10);
        grid.setHgap(10);

        Label userLabel = new Label("Usuario:");
        TextField userField = new TextField();

        Label passLabel = new Label("Contraseña:");
        PasswordField passField = new PasswordField();

        Label messageLabel = new Label();

        Button loginButton = new Button("Iniciar Sesión");
        loginButton.setOnAction(e -> {
            String nombre = userField.getText();
            String contrasena = passField.getText();
            if(usuarioService.validarUsuario(nombre, contrasena)){
                bot.enviarMensaje("Usuario "+nombre+" inició sesión");
                FormularioController formularioController = new FormularioController(primaryStage, nombre);
                formularioController.mostrarPantallaDatosGenerales();
            } else {
                messageLabel.setText("Usuario o contraseña inválidos");
            }
        });

        Button backButton = new Button("Volver");
        backButton.setOnAction(e -> mostrarPantallaInicio());

        grid.addRow(0, userLabel, userField);
        grid.addRow(1, passLabel, passField);
        grid.addRow(2, loginButton, backButton);
        grid.addRow(3, messageLabel);

        primaryStage.setScene(new Scene(grid, 400, 200));
        primaryStage.setTitle("Iniciar Sesión");
    }

    private void mostrarPantallaRegistro() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setVgap(10);
        grid.setHgap(10);

        Label userLabel = new Label("Usuario:");
        TextField userField = new TextField();

        Label passLabel = new Label("Contraseña:");
        PasswordField passField = new PasswordField();

        Label messageLabel = new Label();

        Button registerButton = new Button("Registrarse");
        registerButton.setOnAction(e -> {
            String nombre = userField.getText();
            String contrasena = passField.getText();
            if(usuarioService.usuarioExiste(nombre)){
                messageLabel.setText("Ya existe este usuario");
                return;
            }
            if(nombre.isEmpty() || contrasena.isEmpty()){
                messageLabel.setText("Los campos no pueden estar vacíos");
                return;
            }
            Usuario user = new Usuario();
            user.setNombre(nombre);
            user.setContrasena(contrasena);
            usuarioService.guardar(user);
            bot.enviarMensaje("Nuevo usuario registrado: "+nombre);
            messageLabel.setText("Usuario registrado exitosamente");
        });

        Button backButton = new Button("Volver");
        backButton.setOnAction(e -> mostrarPantallaInicio());

        grid.addRow(0, userLabel, userField);
        grid.addRow(1, passLabel, passField);
        grid.addRow(2, registerButton, backButton);
        grid.addRow(3, messageLabel);

        primaryStage.setScene(new Scene(grid, 400, 200));
        primaryStage.setTitle("Registro");
    }
}