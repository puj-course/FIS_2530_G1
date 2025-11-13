package bioprint.modulocalculadora;

import java.util.ArrayList;
import java.util.List;

public class GrupoFuentes implements FuenteHuella {
    
    public List<FuenteHuella> fuentes = new ArrayList<>();

    public GrupoFuentes() {
        this.fuentes = new ArrayList<>();
    }
    
    public void addFuente(FuenteHuella f) {
        fuentes.add(f);
    }

    @Override
    public double calcularCO2() {
        return fuentes.stream().mapToDouble(FuenteHuella::calcularCO2).sum();
    }
}