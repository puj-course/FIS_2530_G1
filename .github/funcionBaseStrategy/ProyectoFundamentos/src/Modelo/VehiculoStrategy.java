package Modelo;

public class VehiculoStrategy implements TransporteStrategy {
        private String tipoCombustible;

    public VehiculoStrategy (String tipoCombustible) {
            this.tipoCombustible = tipoCombustible;
        }

        public double calcularHuella(double distanciaKm, int diasSemana) {
            double factor = tipoCombustible.equalsIgnoreCase("gasolina") ? 0.21 : 0.25; // kg CO₂e/km
            return distanciaKm * diasSemana * factor;
        }
}
