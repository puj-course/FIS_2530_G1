package Modelo;

import Modelo.TransporteStrategy;

public class Transporte extends FuenteHuella {
    private TransporteStrategy estrategia;
    private double distanciaDiaria;
    private int diasSemana;

    public Transporte(TransporteStrategy estrategia, double distanciaDiaria, int diasSemana) {
        super("Transporte");
        this.estrategia = estrategia;
        this.distanciaDiaria = distanciaDiaria;
        this.diasSemana = diasSemana;
    }

    public void calcularEmisiones() {
        emisiones = estrategia.calcularHuella(distanciaDiaria, diasSemana);
    }

    public double getKmDiarios() {
        return distanciaDiaria;
    }

    public double calcularCO2() {
        calcularEmisiones();
        return emisiones;
    }
}
