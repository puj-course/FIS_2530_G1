package bioprint.modulocalculadora;

public class ArbolesAdapter{
    public String mostrar(double kgCO2) {
        if(kgCO2<0){
            return "los kilogramos de CO2 no pueden ser menores a 0";
        }
        int arboles = (int) (kgCO2 / 20); // 1 árbol ≈ 20 kg CO₂/año
        return "Equivale a plantar " + arboles + " árboles.";
    }
}