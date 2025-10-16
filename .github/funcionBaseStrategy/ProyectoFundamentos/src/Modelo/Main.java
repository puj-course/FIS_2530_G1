package Modelo;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== 🌍 PRUEBA BÁSICA DEL MODELO DE HUELLA DE CARBONO ===\n");

        // 🥦 Alimentación
        Alimentacion alimentacion = new Alimentacion("omnívora", "3–5 veces por semana", "todos los días", "mayormente locales");
        System.out.println("Alimentación:");
        System.out.println("  Tipo: " + alimentacion.getTipoDieta());
        System.out.println("  Frecuencia carne: " + alimentacion.getFrecuencia());
        System.out.printf("  Emisiones: %.2f kgCO₂%n%n", alimentacion.calcularCO2());

        // ⚡ Energía
        Energia energia = new Energia(150, "energía hidroeléctrica", "sí, todos", "a veces");
        System.out.println("Energía:");
        System.out.println("  Consumo mensual: " + energia.getConsumoMensualKwh() + " kWh");
        System.out.println("  Fuente: " + energia.getFuenteEnergia());
        System.out.printf("  Emisiones: %.2f kgCO₂%n%n", energia.calcularCO2());

        // 🚗 Transporte
        TransporteStrategy estrategia = new VehiculoStrategy("gasolina");
        Transporte transporte = new Transporte(estrategia, 20, 5); // 20 km diarios, 5 días por semana
        System.out.println("Transporte:");
        System.out.println("  Tipo: Carro (gasolina)");
        System.out.println("  Distancia diaria: " + transporte.getKmDiarios() + " km");
        System.out.printf("  Emisiones: %.2f kgCO₂%n%n", transporte.calcularCO2());

        // 🌎 Total estimado
        double total = alimentacion.calcularCO2() + energia.calcularCO2() + transporte.calcularCO2();
        System.out.printf("Huella total estimada: %.2f kgCO₂%n", total);
    }
}
