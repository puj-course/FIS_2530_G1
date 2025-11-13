package bioprint.modulocalculadora;

public class EstrategiaCarro implements EstrategiaTransporte {
    @Override
    public double calcularCO2(double km) { 
        if(km<0) return 0;
        return km*0.21; 
    }
}