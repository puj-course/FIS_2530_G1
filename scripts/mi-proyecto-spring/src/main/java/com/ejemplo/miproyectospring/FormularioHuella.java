package com.ejemplo.miproyectospring;

public class FormularioHuella extends Sujeto {
	/*
	private CalculadoraHuella calculadora;
    private Scanner sc = new Scanner(System.in);

    public FormularioHuella(CalculadoraHuella calculadora) {
        this.calculadora = calculadora;
    }

    public void iniciarFormulario() {
        System.out.println("===  CALCULADORA DE HUELLA DE CARBONO ===");
        System.out.println("Responde con honestidad para obtener recomendaciones en tiempo real.\n");

        try {
            // DATOS GENERALES
            System.out.print("Número de personas en tu hogar: ");
            calculadora.setPersonas(sc.nextInt());

            //  ELECTRICIDAD
            System.out.println("\n 1. ELECTRICIDAD");
            System.out.print("Consumo mensual de electricidad (kWh): ");
            calculadora.setConsumoElectricidad(sc.nextDouble());
            sc.nextLine();

            System.out.println("Fuente de energía principal:");
            System.out.println("1. Hidroeléctrica\n2. Solar\n3. Eólica\n4. Gas o carbón");
            calculadora.setFuenteEnergia(sc.nextInt());
            notificarObservadores("fuenteEnergia", calculadora.getFuenteEnergia());
            sc.nextLine();

            System.out.println("¿Usas bombillos LED o ahorradores?");
            System.out.println("1. Todos\n2. Algunos\n3. Ninguno");
            calculadora.setUsoLed(sc.nextInt());
            notificarObservadores("usoLed", calculadora.getUsoLed());
            sc.nextLine();

            System.out.println("¿Sueles dejar aparatos enchufados?");
            System.out.println("1. Nunca\n2. A veces\n3. Siempre");
            calculadora.setEnchufados(sc.nextInt());
            notificarObservadores("enchufados", calculadora.getEnchufados());
            sc.nextLine();

            //  AGUA
            System.out.println("\n 2. AGUA");
            System.out.print("Consumo mensual de agua (m³): ");
            calculadora.setConsumoAgua(sc.nextDouble());

            System.out.print("Número de duchas al día (por persona): ");
            calculadora.setDuchas(sc.nextDouble());

            System.out.print("Duración promedio de cada ducha (minutos): ");
            calculadora.setDuracionDucha(sc.nextDouble());
            sc.nextLine();

            System.out.println("¿Tienes dispositivos ahorradores de agua?");
            System.out.println("1. Sí\n2. No");
            calculadora.setDispositivosAhorradores(sc.nextInt());
            notificarObservadores("dispositivosAhorradores", calculadora.getDispositivosAhorradores());
            sc.nextLine();

            //  TRANSPORTE
            System.out.println("\n 3. TRANSPORTE");
            System.out.println("Tipo de transporte principal:");
            System.out.println("1. Vehículo propio (gasolina)\n2. Vehículo propio (diésel)\n3. Motocicleta\n4. Transporte público\n5. Bicicleta / caminata\n6. Vehículo eléctrico");
            calculadora.setTipoTransporte(sc.nextInt());
            notificarObservadores("tipoTransporte", calculadora.getTipoTransporte());
            sc.nextLine();

            System.out.print("Distancia promedio recorrida al día (km): ");
            calculadora.setDistanciaDiaria(sc.nextDouble());

            System.out.print("Número de días a la semana que usas ese medio: ");
            calculadora.setDiasSemana(sc.nextInt());
            sc.nextLine();

            System.out.println("¿Viajas en avión durante el año?");
            System.out.println("1. No viajo\n2. 1–2 veces al año\n3. 3–5 veces al año\n4. Más de 5 veces al año");
            calculadora.setVuelosAnuales(sc.nextInt());
            sc.nextLine();

            //  ALIMENTACIÓN
            System.out.println("\n 4. ALIMENTACIÓN");
            System.out.println("Tipo de dieta principal:");
            System.out.println("1. Vegetariana\n2. Vegana\n3. Omnívora\n4. Alta en carne roja");
            calculadora.setTipoDieta(sc.nextInt());
            notificarObservadores("tipoDieta", calculadora.getTipoDieta());
            sc.nextLine();

            System.out.println("Frecuencia de consumo de carne roja:");
            System.out.println("1. Nunca\n2. 1–2 veces por semana\n3. 3–5 veces por semana\n4. Todos los días");
            calculadora.setFrecuenciaCarne(sc.nextInt());
            sc.nextLine();

            System.out.println("Frecuencia de consumo de productos lácteos:");
            System.out.println("1. Nunca\n2. 1–2 veces por semana\n3. 3–5 veces por semana\n4. Todos los días");
            calculadora.setFrecuenciaLacteos(sc.nextInt());
            sc.nextLine();

            System.out.println("¿Consumes productos locales o importados?");
            System.out.println("1. Mayormente locales\n2. Mezclado\n3. Mayormente importados");
            calculadora.setOrigenProductos(sc.nextInt());
            sc.nextLine();

        } catch (Exception e) {
            System.out.println(" Error en la entrada. Intenta nuevamente.");
        }
    }
*/}
