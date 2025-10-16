package Modelo;

public abstract class FuenteHuella {
    protected String nombre;
    protected double emisiones; // en kg CO₂e

    public FuenteHuella(String nombre) {
        this.nombre = nombre;
        this.emisiones = 0;
    }

    public String getNombre() {
        return nombre;
    }

    public double getEmisiones() {
        return emisiones;
    }

    // Método abstracto que cada fuente implementa según sus datos
    public abstract void calcularEmisiones();

}
