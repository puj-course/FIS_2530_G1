/*package bioprint.ModuloCalculadora;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Formulario {
    public static boolean validar(int numero, int max, int min, boolean opciones) {
        if (numero > max || numero < min) {
            if (opciones) {
                System.out.println("Por favor elegir entre las opciones dadas");
                return false;
            }
            System.out.println("Por favor ingresar un número entre " + min + " y " + max);
            return false;
        }
        return true;
    }

    public static boolean validar(double numero, double max, double min, boolean opciones) {
        if (numero > max || numero < min) {
            if (opciones) {
                System.out.println("Por favor elegir entre las opciones dadas");
                return false;
            }
            System.out.println("Por favor ingresar un número entre " + min + " y " + max);
            return false;
        }
        return true;
    }

    public static double formulario() {
        Scanner sc = new Scanner(System.in);
        GrupoFuentes grupo = new GrupoFuentes(); // Composite
        Sujeto sujeto = new Sujeto();             // Observable
        sujeto.addObservador(new ConsolaObservador());

        int personas = 0;
        double luz = 0;
        double gas = 0;
        double agua = 0;
        int fuenteEnergia = 0;
        int bombillos = 0;
        int aparatos = 0;
        int duchas = 0;
        int duracionDucha = 0;
        int ahorrador = 0;
        int tipoTransporte = 0;
        System.out.println("-----------------------------------------------------------------");
        System.out.println("CALCULADORA DE HUELLA DE CARBONO");
        System.out.println("-----------------------------------------------------------------");
        System.out.println("INGRESE -100 EN CUALQUIER MOMENTO PARA SALIR DEL FORMULARIO");
        try {
            // DATOS GENERALES
            while (true) {
                System.out.print("\nNúmero de personas en tu hogar: ");
                personas = sc.nextInt();
                sc.nextLine();
                if (personas == -100) return -1;
                if (!validar(personas, 100, 0, false)) continue;
                break;
            }

            // ELECTRICIDAD
            while (true) {
                System.out.println("\nSección 1: Electricidad");
                System.out.print("Consumo mensual de electricidad (kWh): ");
                luz = sc.nextDouble();
                sc.nextLine();
                if (luz == -100) return -1;
                if (!validar(luz, 1000, 1, false)) continue;
                break;
            }

            while (true) {
                System.out.print("Consumo mensual de gas (m³): ");
                gas = sc.nextDouble();
                sc.nextLine();
                if (gas == -100) return -1;
                if (!validar(gas, 10000, 1, false)) continue;
                break;
            }

            while (true) {
                System.out.print("Consumo mensual de agua (m³): ");
                agua = sc.nextDouble();
                sc.nextLine();
                if (agua == -100) return -1;
                if (!validar(agua, 100, 1, false)) continue;
                break;
            }

            while (true) {
                System.out.println("Fuente de energía principal:");
                System.out.println("1. Hidroeléctrica  2. Solar  3. Eólica  4. Gas/Carbón");
                fuenteEnergia = sc.nextInt();
                sc.nextLine();
                if (fuenteEnergia == -100) return -1;
                if (!validar(fuenteEnergia, 4, 1, true)) continue;
                break;
            }

            while (true) {
                System.out.println("¿Usas bombillos LED o ahorradores?");
                System.out.println("1. Todos  2. Algunos  3. Ninguno");
                bombillos = sc.nextInt();
                sc.nextLine();
                if (bombillos == -100) return -1;
                if (!validar(bombillos, 3, 1, true)) continue;
                break;
            }

            while (true) {
                System.out.println("¿Dejas aparatos enchufados cuando no los usas?");
                System.out.println("1. Nunca  2. A veces  3. Siempre");
                aparatos = sc.nextInt();
                sc.nextLine();
                if (aparatos == -100) return -1;
                if (!validar(aparatos, 3, 1, true)) continue;
                break;
            }

            double ajusteEnergia = 1.0;
            if (fuenteEnergia == 4) ajusteEnergia += 0.2;
            if (bombillos == 3) ajusteEnergia += 0.1;
            if (aparatos == 3) ajusteEnergia += 0.1;

            double luzAjustada = luz * ajusteEnergia;
            grupo.addFuente(FuenteHuellaFactory.crearFuente(luzAjustada, gas, agua));

            // AGUA
            while (true) {
                System.out.println("\nSección 2: Agua");
                System.out.print("Número de duchas al día (por persona): ");
                duchas = sc.nextInt();
                sc.nextLine();
                if (duchas == -100) return -1;
                if (!validar(duchas, 5, 0, false)) continue;
                break;
            }

            while (true) {
                System.out.print("Duración promedio de cada ducha (minutos): ");
                duracionDucha = sc.nextInt();
                sc.nextLine();
                if (duracionDucha == -100) return -1;
                if (!validar(duracionDucha, 60, 1, false)) continue;
                break;
            }

            while (true) {
                System.out.print("¿Tienes dispositivos ahorradores de agua? (1=Sí, 2=No): ");
                ahorrador = sc.nextInt();
                sc.nextLine();
                if (ahorrador == -100) return -1;
                if (!validar(ahorrador, 2, 1, true)) continue;
                break;
            }

            double consumoDucha = duchas * duracionDucha * personas * (ahorrador == 1 ? 0.8 : 1.0);
            double extraAgua = consumoDucha * 0.05;
            grupo.addFuente(FuenteHuellaFactory.crearFuente(0, 0, extraAgua));

            // TRANSPORTE
            while (true) {
                System.out.println("\nSección 3: Transporte");
                System.out.println("1. Carro gasolina  2. Carro diésel  3. Moto  4. Transporte público  5. Bicicleta/caminar  6. Vehículo eléctrico");
                tipoTransporte = sc.nextInt();
                sc.nextLine();
                if (tipoTransporte == -100) return -1;
                if (!validar(tipoTransporte, 6, 1, true)) continue;
                break;
            }

            double km = 0;
            while (true) {
                System.out.print("Distancia promedio recorrida al día (km): ");
                km = sc.nextDouble();
                sc.nextLine();
                if (km == -100) return -1;
                if (!validar(km, 1000, 0, false)) continue;
                break;
            }

            int dias = 0;
            while (true) {
                System.out.print("Número de días a la semana que usas ese medio: ");
                dias = sc.nextInt();
                sc.nextLine();
                if (dias == -100) return -1;
                if (!validar(dias, 7, 1, false)) continue;
                break;
            }

            int vuelos = 0;
            while (true) {
                System.out.println("¿Viajas en avión durante el año?");
                System.out.println("1. No viajo  2. 1-2 veces  3. 3-5 veces  4. Más de 5 veces");
                vuelos = sc.nextInt();
                sc.nextLine();
                if (vuelos == -100) return -1;
                if (!validar(vuelos, 4, 1, true)) continue;
                break;
            }

            EstrategiaTransporte estrategia;
            switch (tipoTransporte) {
                case 1 -> estrategia = kmDia -> kmDia * 0.21;
                case 2 -> estrategia = kmDia -> kmDia * 0.25;
                case 3 -> estrategia = kmDia -> kmDia * 0.12;
                case 4 -> estrategia = kmDia -> kmDia * 0.09;
                case 6 -> estrategia = kmDia -> kmDia * 0.03;
                default -> estrategia = kmDia -> 0.0;
            }

            Transporte transporte = new Transporte(estrategia, km * dias);
            grupo.addFuente(FuenteHuellaFactory.crearFuente(transporte));

            double huellaVuelos = switch (vuelos) {
                case 2 -> 500;
                case 3 -> 1200;
                case 4 -> 2500;
                default -> 0;
            };
            FuenteHuella vuelosFuente = () -> huellaVuelos;
            grupo.addFuente(vuelosFuente);

            // ALIMENTACIÓN
            int tipoDieta = 0;
            while (true) {
                System.out.println("\nSección 4: Alimentación");
                System.out.println("1. Vegetariana  2. Vegana  3. Omnívora  4. Alta en carne roja");
                tipoDieta = sc.nextInt();
                sc.nextLine();
                if (tipoDieta == -100) return -1;
                if (!validar(tipoDieta, 4, 1, true)) continue;
                break;
            }

            int carne = 0;
            while (true) {
                System.out.println("Frecuencia de consumo de carne roja:");
                System.out.println("1. Nunca  2. 1–2/semana  3. 3–5/semana  4. Todos los días");
                carne = sc.nextInt();
                sc.nextLine();
                if (carne == -100) return -1;
                if (!validar(carne, 4, 1, true)) continue;
                break;
            }

            int lacteos = 0;
            while (true) {
                System.out.println("Frecuencia de consumo de lácteos:");
                System.out.println("1. Nunca  2. 1–2/semana  3. 3–5/semana  4. Todos los días");
                lacteos = sc.nextInt();
                sc.nextLine();
                if (lacteos == -100) return -1;
                if (!validar(lacteos, 4, 1, true)) continue;
                break;
            }

            int origen = 0;
            while (true) {
                System.out.println("¿Consumes productos locales o importados?");
                System.out.println("1. Locales  2. Mezclado  3. Importados");
                origen = sc.nextInt();
                sc.nextLine();
                if (origen == -100) return -1;
                if (!validar(origen, 3, 1, true)) continue;
                break;
            }

            String dieta = switch (tipoDieta) {
                case 1 -> "vegetariana";
                case 2 -> "vegana";
                case 3 -> "omnivora";
                case 4 -> "alta en carne roja";
                default -> "omnivora";
            };

            grupo.addFuente(FuenteHuellaFactory.crearFuente(dieta, carne));

            // CÁLCULO TOTAL
            CalculadoraHuella calculadora = CalculadoraHuella.getInstance();
            double total = calculadora.calcularTotal(grupo) / personas;

            ArbolesAdapter eq = new ArbolesAdapter();
            System.out.println("\n========================================");
            System.out.printf(" Su huella total anual por persona: %.2f kgCO2%n", total);
            System.out.println(eq.mostrar(total));

            sujeto.notificar(total);

            if (total < 2000)
                System.out.println("Excelente: tu huella es muy baja.");
            else if (total < 5000)
                System.out.println("Moderada: podrías mejorar algunos hábitos.");
            else
                System.out.println("Alta: considera reducir transporte y consumo energético.");

            return total;
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Usa números cuando se soliciten valores numéricos.");
        }
        return -1;
    }
}*/