package bioprint;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class BioPrintApp extends Application {

    private ConfigurableApplicationContext context;

    @Override
    public void init() {
        // Inicializa el contexto de Spring Boot antes de iniciar JavaFX
        context = new SpringApplicationBuilder(BioPrintApp.class).run();
    }

    @Override
    public void start(Stage stage) throws Exception {
        // Cargar el FXML y permitir que Spring inyecte dependencias en el controlador
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mascota/MascotaView.fxml"));
        loader.setControllerFactory(context::getBean);

        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.setTitle("Mascota Virtual - BioPrint");
        stage.show();
    }

    @Override
    public void stop() {
        // Cierra el contexto de Spring cuando se cierre la aplicación
        context.close();
    }
}
