package bioprint.modulocalculadora;

public class EstrategiaBus implements EstrategiaTransporte {
    @Override
    public double calcularCO2(double km) { 
        if(km<0)return 0;
        return km * 0.1; 
    }
}