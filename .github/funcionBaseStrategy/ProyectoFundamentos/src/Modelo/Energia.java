package Modelo;

public class Energia extends FuenteHuella {
    private double consumoMensualKwh;
    private String fuenteEnergia;
    private String tipoBombillos;
    private String habitoDesenchufar;

    public Energia(double consumoMensualKwh, String fuenteEnergia, String tipoBombillos, String habitoDesenchufar) {
        super("Energía");
        this.consumoMensualKwh = consumoMensualKwh;
        this.fuenteEnergia = fuenteEnergia;
        this.tipoBombillos = tipoBombillos;
        this.habitoDesenchufar = habitoDesenchufar;
    }

    @Override
    public void calcularEmisiones() {
        double factorEmision = 0.4; // valor base (kg CO₂e/kWh)

        switch (fuenteEnergia.toLowerCase()) {
            case "energía solar":
                factorEmision = 0.05;
                break;
            case "energía eólica":
                factorEmision = 0.02;
                break;
            case "energía hidroeléctrica":
                factorEmision = 0.1;
                break;
            case "gas o carbón":
                factorEmision = 0.8;
                break;
        }

        double ajusteBombillos = tipoBombillos.equalsIgnoreCase("todos") ? 0.9 :
                tipoBombillos.equalsIgnoreCase("algunos") ? 1.0 : 1.1;

        double ajusteDesenchufar = habitoDesenchufar.equalsIgnoreCase("nunca") ? 1.2 :
                habitoDesenchufar.equalsIgnoreCase("a veces") ? 1.0 : 0.9;

        emisiones = consumoMensualKwh * factorEmision * ajusteBombillos * ajusteDesenchufar;
    }

    public double getConsumoMensualKwh() {
        return consumoMensualKwh;
    }

    public String getFuenteEnergia() {
        return fuenteEnergia;
    }

    public double calcularCO2() {
        calcularEmisiones();
        return emisiones;
    }
}
