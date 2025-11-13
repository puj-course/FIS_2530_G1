package bioprint.ModuloCalculadora;

import org.springframework.context.ApplicationContext;

import bioprint.BioPrint;
import bioprint.ModuloUsuarios.PuntajeService;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class FormularioController {

    private Stage stage;
    private String nombreUsuario;
    private PuntajeService puntajeService;
    private int personas, bombillos, aparatos, duchas, duracionDucha, ahorrador, tipoTransporte, vuelos, tipoDieta, carne, lacteos, origen;
    private double luz, gas, agua;
    private int fuenteEnergia;

    private GrupoFuentes grupo = new GrupoFuentes(); // Composite

    public FormularioController(Stage stage, String nombreUsuario){
        this.stage = stage;
        this.nombreUsuario = nombreUsuario;
    }

    public void mostrarPantallaDatosGenerales(){
        GridPane grid = crearGrid();

        Label label = new Label("Número de personas en tu hogar:");
        TextField input = new TextField();
        Label errorLabel = new Label();

        Button next = new Button("Siguiente");
        next.setOnAction(e -> {
            try {
                personas = Integer.parseInt(input.getText());
                if (personas < 1 || personas > 100) {
                    errorLabel.setText("Ingrese un número entre 1 y 100");
                    return;
                }
                mostrarPantallaElectricidad();
            } catch (NumberFormatException ex) {
                errorLabel.setText("Ingrese un número válido");
            }
        });

        agregarGrid(grid, label, input, next, errorLabel);

        stage.setScene(new Scene(grid, 400, 200));
        stage.setTitle("Formulario - Datos Generales");
        stage.show();
    }

    private void mostrarPantallaElectricidad() {
        GridPane grid = crearGrid();

        Label labelLuz = new Label("Consumo mensual electricidad (kWh):");
        TextField inputLuz = new TextField();

        Label labelGas = new Label("Consumo mensual gas (m³):");
        TextField inputGas = new TextField();

        Label labelAgua = new Label("Consumo mensual agua (m³):");
        TextField inputAgua = new TextField();

        Label labelFuente = new Label("Fuente de energía principal:");
        ComboBox<String> fuenteCombo = new ComboBox<>();
        fuenteCombo.getItems().addAll("Hidroeléctrica", "Solar", "Eólica", "Gas/Carbón");

        Label errorLabel = new Label();
        Button next = new Button("Siguiente");

        next.setOnAction(e -> {
            try {
                luz = Double.parseDouble(inputLuz.getText());
                gas = Double.parseDouble(inputGas.getText());
                agua = Double.parseDouble(inputAgua.getText());
                fuenteEnergia = fuenteCombo.getSelectionModel().getSelectedIndex() + 1;
                if (luz <= 0 || gas <= 0 || agua <= 0 || fuenteEnergia < 1) {
                    errorLabel.setText("Todos los campos deben ser válidos");
                    return;
                }

                double ajusteEnergia = 1.0;
                if (fuenteEnergia == 4) ajusteEnergia += 0.2;
                // bombillos y aparatos se pueden agregar después
                grupo.addFuente(FuenteHuellaFactory.crearFuente(luz*ajusteEnergia, gas, agua));

                mostrarPantallaAgua();
            } catch (NumberFormatException ex) {
                errorLabel.setText("Ingrese números válidos");
            }
        });

        grid.addRow(0, labelLuz, inputLuz);
        grid.addRow(1, labelGas, inputGas);
        grid.addRow(2, labelAgua, inputAgua);
        grid.addRow(3, labelFuente, fuenteCombo);
        grid.addRow(4, next, errorLabel);

        stage.setScene(new Scene(grid, 500, 250));
        stage.setTitle("Formulario - Electricidad");
        stage.show();
    }

    private void mostrarPantallaAgua() {
        GridPane grid = crearGrid();

        Label labelDuchas = new Label("Número de duchas al día (por persona):");
        TextField inputDuchas = new TextField();

        Label labelDuracion = new Label("Duración promedio de cada ducha (min):");
        TextField inputDuracion = new TextField();

        Label labelAhorrador = new Label("Dispositivos ahorradores de agua (1=Sí, 2=No):");
        TextField inputAhorrador = new TextField();

        Label errorLabel = new Label();
        Button next = new Button("Siguiente");

        next.setOnAction(e -> {
            try {
                duchas = Integer.parseInt(inputDuchas.getText());
                duracionDucha = Integer.parseInt(inputDuracion.getText());
                ahorrador = Integer.parseInt(inputAhorrador.getText());

                double consumoDucha = duchas * duracionDucha * personas * (ahorrador == 1 ? 0.8 : 1.0);
                double extraAgua = consumoDucha * 0.05;
                grupo.addFuente(FuenteHuellaFactory.crearFuente(0,0,extraAgua));

                mostrarPantallaTransporte();
            } catch (NumberFormatException ex) {
                errorLabel.setText("Ingrese valores válidos");
            }
        });

        grid.addRow(0, labelDuchas, inputDuchas);
        grid.addRow(1, labelDuracion, inputDuracion);
        grid.addRow(2, labelAhorrador, inputAhorrador);
        grid.addRow(3, next, errorLabel);

        stage.setScene(new Scene(grid, 500, 250));
        stage.setTitle("Formulario - Agua");
        stage.show();
    }

    private void mostrarPantallaTransporte() {
        GridPane grid = crearGrid();

        Label labelTransporte = new Label("Tipo de transporte:");
        ComboBox<String> transporteCombo = new ComboBox<>();
        transporteCombo.getItems().addAll("Carro gasolina", "Carro diésel", "Moto", "Transp. público", "Bicicleta/Caminar", "Vehículo eléctrico");

        Label labelKm = new Label("Distancia promedio recorrida al día (km):");
        TextField inputKm = new TextField();

        Label labelDias = new Label("Número de días a la semana:");
        TextField inputDias = new TextField();

        Label labelVuelos = new Label("Viajes en avión por año:");
        ComboBox<String> vuelosCombo = new ComboBox<>();
        vuelosCombo.getItems().addAll("No viajo","1-2","3-5","Más de 5");

        Label errorLabel = new Label();
        Button next = new Button("Siguiente");

        next.setOnAction(e -> {
            try {
                tipoTransporte = transporteCombo.getSelectionModel().getSelectedIndex() + 1;
                double km = Double.parseDouble(inputKm.getText());
                int dias = Integer.parseInt(inputDias.getText());
                vuelos = vuelosCombo.getSelectionModel().getSelectedIndex() + 1;

                EstrategiaTransporte estrategia;
                switch (tipoTransporte) {
                    case 1 -> estrategia = kmDia -> kmDia*0.21;
                    case 2 -> estrategia = kmDia -> kmDia*0.25;
                    case 3 -> estrategia = kmDia -> kmDia*0.12;
                    case 4 -> estrategia = kmDia -> kmDia*0.09;
                    case 6 -> estrategia = kmDia -> kmDia*0.03;
                    default -> estrategia = kmDia -> 0.0;
                }

                Transporte transporte = new Transporte(estrategia, km*dias);
                grupo.addFuente(FuenteHuellaFactory.crearFuente(transporte));

                double huellaVuelos = switch(vuelos) {
                    case 2 -> 500;
                    case 3 -> 1200;
                    case 4 -> 2500;
                    default -> 0;
                };
                grupo.addFuente(() -> huellaVuelos);

                mostrarPantallaAlimentacion();
            } catch (Exception ex) {
                errorLabel.setText("Complete todos los campos correctamente");
            }
        });

        grid.addRow(0, labelTransporte, transporteCombo);
        grid.addRow(1, labelKm, inputKm);
        grid.addRow(2, labelDias, inputDias);
        grid.addRow(3, labelVuelos, vuelosCombo);
        grid.addRow(4, next, errorLabel);

        stage.setScene(new Scene(grid, 600, 300));
        stage.setTitle("Formulario - Transporte");
        stage.show();
    }

    private void mostrarPantallaAlimentacion() {
        GridPane grid = crearGrid();

        Label labelDieta = new Label("Tipo de dieta:");
        ComboBox<String> dietaCombo = new ComboBox<>();
        dietaCombo.getItems().addAll("Vegetariana","Vegana","Omnívora","Alta en carne roja");

        Label labelCarne = new Label("Frecuencia carne roja:");
        ComboBox<String> carneCombo = new ComboBox<>();
        carneCombo.getItems().addAll("Nunca","1-2/semana","3-5/semana","Todos los días");

        Label labelLacteos = new Label("Frecuencia lácteos:");
        ComboBox<String> lacteosCombo = new ComboBox<>();
        lacteosCombo.getItems().addAll("Nunca","1-2/semana","3-5/semana","Todos los días");

        Label labelOrigen = new Label("Productos locales/importados:");
        ComboBox<String> origenCombo = new ComboBox<>();
        origenCombo.getItems().addAll("Locales","Mezclado","Importados");

        Label errorLabel = new Label();
        Button finish = new Button("Finalizar");

        finish.setOnAction(e -> {
            tipoDieta = dietaCombo.getSelectionModel().getSelectedIndex() + 1;
            carne = carneCombo.getSelectionModel().getSelectedIndex() + 1;
            lacteos = lacteosCombo.getSelectionModel().getSelectedIndex() + 1;
            origen = origenCombo.getSelectionModel().getSelectedIndex() + 1;

            String dieta = switch(tipoDieta) {
                case 1 -> "vegetariana";
                case 2 -> "vegana";
                case 3 -> "omnivora";
                case 4 -> "alta en carne roja";
                default -> "omnivora";
            };

            grupo.addFuente(FuenteHuellaFactory.crearFuente(dieta, carne));

            CalculadoraHuella calculadora = CalculadoraHuella.getInstance();
            double total = calculadora.calcularTotal(grupo) / personas;

            mostrarPantallaResultados(total);
        });

        grid.addRow(0, labelDieta, dietaCombo);
        grid.addRow(1, labelCarne, carneCombo);
        grid.addRow(2, labelLacteos, lacteosCombo);
        grid.addRow(3, labelOrigen, origenCombo);
        grid.addRow(4, finish, errorLabel);

        stage.setScene(new Scene(grid, 500, 250));
        stage.setTitle("Formulario - Alimentación");
        stage.show();
    }

    private void mostrarPantallaResultados(double total){
        ApplicationContext context = BioPrint.context;
        puntajeService = context.getBean(PuntajeService.class);
        puntajeService.registrarPuntaje(nombreUsuario, total);  
        
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setVgap(10);
        grid.setHgap(10);

        // Etiqueta con el nombre del usuario y el total de huella
        Label label = new Label(String.format(
            "Usuario: %s\nSu huella total anual por persona: %.2f kgCO2",
            nombreUsuario, total
        ));
        long top = puntajeService.contarUsuariosConPuntajeMayor(nombreUsuario);
        // Consejo según el total
        Label consejo = new Label();
        if(total < 2000) consejo.setText("Excelente: tu huella es muy baja. Estás en el top "+top+" de los usuarios!");
        else if(total < 5000) consejo.setText("Moderada: podrías mejorar algunos hábitos. Estás en el top "+top+" de los usuarios!");
        else consejo.setText("Alta: considera reducir transporte y consumo energético. Estás en el top "+top+" de los usuarios!");
        // Botón para salir del programa
        Button salirButton = new Button("Salir del programa");
        salirButton.setOnAction(e -> {
            stage.close(); // Cierra la ventana actual
            System.exit(0); // Termina la aplicación
        });

        // Añadir los elementos al GridPane
        grid.addRow(0, label);
        grid.addRow(1, consejo);
        grid.addRow(2, salirButton);

        // Configurar escena y mostrar
        stage.setScene(new Scene(grid, 500, 200));
        stage.setTitle("Resultados - Huella de Carbono");
        stage.show();
    }

    private GridPane crearGrid() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setVgap(10);
        grid.setHgap(10);
        return grid;
    }

    private void agregarGrid(GridPane grid, Label label, TextField input, Button next, Label error) {
        grid.addRow(0, label, input);
        grid.addRow(1, next, error);
    }
}
