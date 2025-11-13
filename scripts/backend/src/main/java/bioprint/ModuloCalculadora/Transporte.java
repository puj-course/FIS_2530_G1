package bioprint.modulocalculadora;

public class Transporte implements FuenteHuella {
    public EstrategiaTransporte estrategia;
    public double km;

    public Transporte(EstrategiaTransporte estrategia, double km) {
        this.estrategia = estrategia;
        this.km = km;
    }

    @Override
    public double calcularCO2() {
        return estrategia.calcularCO2(km);
    }
}