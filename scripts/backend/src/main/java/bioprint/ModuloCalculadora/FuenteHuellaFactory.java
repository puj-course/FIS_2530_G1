package bioprint.modulocalculadora;

public class FuenteHuellaFactory {
    //Energia
    public static FuenteHuella crearFuente(double luzKwh, double gasM3, double aguaM3) {
        return new FuenteHuella() {
            @Override
            public double calcularCO2() {
                double co2Luz = luzKwh * 0.5;
                double co2Gas = gasM3 * 2.3; 
                double co2Agua = aguaM3 * 0.35;
                return co2Luz + co2Gas + co2Agua;
            }
        };
    }

    //Alimentación
    public static FuenteHuella crearFuente(String tipoDieta, int frecuenciaCarneRoja) {
        return new FuenteHuella() {
            @Override
            public double calcularCO2() {
                return switch (tipoDieta.toLowerCase()) {
                    case "vegana" -> 1000;
                    case "vegetariana" -> 1300;
                    case "omnivora" -> 2000 + frecuenciaCarneRoja * 20;
                    case "alta en carne roja" -> 2800 + frecuenciaCarneRoja * 50;
                    default -> 1800;
                };
            }
        };
    }

    //Transporte 
    public static FuenteHuella crearFuente(Transporte transporte) {
        return transporte;  
    }

    public static FuenteHuella crearFuente(FuenteHuella fuente1, FuenteHuella fuente2) {
        return new FuenteHuella() {
            @Override
            public double calcularCO2() {
                return (fuente1.calcularCO2()+fuente2.calcularCO2());
            }
        };
    }
}