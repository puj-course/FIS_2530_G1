package Modelo;

public class PublicoStrategy implements TransporteStrategy {
    @Override
    public double calcularHuella(double distanciaKm, int diasSemana) {
        return distanciaKm * diasSemana * 0.1; // menos emisiones por persona
    }
}
