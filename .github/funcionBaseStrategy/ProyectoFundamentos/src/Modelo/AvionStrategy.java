package Modelo;

public class AvionStrategy implements TransporteStrategy {
    private int vuelosAnuales;

    public AvionStrategy(int vuelosAnuales) {
        this.vuelosAnuales = vuelosAnuales;
    }

    public double calcularHuella(double distanciaKm, int diasSemana) {
        // Aproximación: 250 kg CO₂e por vuelo corto
        return vuelosAnuales * 250;
    }
}
