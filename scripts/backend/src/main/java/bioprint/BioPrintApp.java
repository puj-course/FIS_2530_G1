package bioprint;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

@SpringBootApplication
public class BioPrintApp extends Application {

    private static ConfigurableApplicationContext context; // 🔹 Hacemos el contexto accesible

    @Override
    public void init() {
        // Inicia el contexto de Spring Boot
        context = new SpringApplicationBuilder(BioPrintApp.class).run();
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
        loader.setControllerFactory(context::getBean); 

        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.setTitle("Inicio de Sesión - BioPrint");
        stage.centerOnScreen();
        stage.show();
    }

    @Override
    public void stop() {
        context.close();
    }

    public static ConfigurableApplicationContext getContext() {
        return context;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
