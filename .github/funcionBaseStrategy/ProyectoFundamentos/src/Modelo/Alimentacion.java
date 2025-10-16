package Modelo;

public class Alimentacion extends FuenteHuella {
    private String tipoDieta;
    private String frecuenciaCarne;
    private String frecuenciaLacteos;
    private String origenProductos;

    public Alimentacion(String tipoDieta, String frecuenciaCarne, String frecuenciaLacteos, String origenProductos) {
        super("Alimentación");
        this.tipoDieta = tipoDieta;
        this.frecuenciaCarne = frecuenciaCarne;
        this.frecuenciaLacteos = frecuenciaLacteos;
        this.origenProductos = origenProductos;
    }

    @Override
    public void calcularEmisiones() {
        double base = 0;

        switch (tipoDieta.toLowerCase()) {
            case "vegana": base = 1.5; break;
            case "vegetariana": base = 2.0; break;
            case "omnívora": base = 3.5; break;
            case "alta en carne roja": base = 5.0; break;
            default: base = 3.0;
        }

        switch (frecuenciaCarne.toLowerCase()) {
            case "nunca": base *= 0.8; break;
            case "1–2 veces por semana": base *= 1.0; break;
            case "3–5 veces por semana": base *= 1.2; break;
            case "todos los días": base *= 1.5; break;
        }

        switch (frecuenciaLacteos.toLowerCase()) {
            case "nunca": base *= 0.9; break;
            case "todos los días": base *= 1.1; break;
        }

        if (origenProductos.equalsIgnoreCase("mayormente importados"))
            base *= 1.2;

        emisiones = base * 30; // huella mensual estimada (kg CO₂e)
    }

    public String getTipoDieta() {
        return tipoDieta;
    }

    public double calcularCO2() {
        calcularEmisiones();
        return emisiones;

    }

    public String getFrecuencia() {
        return frecuenciaCarne;
    }
}

