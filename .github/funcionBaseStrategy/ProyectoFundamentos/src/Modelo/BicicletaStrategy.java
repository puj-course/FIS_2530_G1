package Modelo;

public class BicicletaStrategy implements TransporteStrategy {
    @Override
    public double calcularHuella(double distanciaKm, int diasSemana) {
        return 0; // no emite CO₂
    }
}
