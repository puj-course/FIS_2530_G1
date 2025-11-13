package bioprint.modulocalculadora;

public class CalculadoraHuella {
    public static CalculadoraHuella instancia;

    public CalculadoraHuella() {}

    public static CalculadoraHuella getInstance() {
        if (instancia == null) instancia = new CalculadoraHuella();
        return instancia;
    }

    public double calcularTotal(FuenteHuella fuente) {
        return fuente.calcularCO2();
    }
}