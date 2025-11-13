package bioprint;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import bioprint.modulousuarios.LoginController;
import javafx.application.Application;

@SpringBootApplication
public class BioPrint {
    public static ApplicationContext context;
    
    public static void main(String[] args) {
        // Inicializar Spring Boot
        context = SpringApplication.run(BioPrint.class, args);

        // Lanzar JavaFX
        Application.launch(LoginController.class, args);
    }
}
