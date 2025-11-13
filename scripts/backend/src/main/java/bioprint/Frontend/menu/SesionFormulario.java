package bioprint.Frontend.menu;

import bioprint.ModuloCalculadora.*;
import org.springframework.stereotype.Component;

@Component
public class SesionFormulario {

    // === ATRIBUTOS PRINCIPALES ===
    private final GrupoFuentes grupo = new GrupoFuentes(); // Composite principal
    private final Sujeto sujeto = new Sujeto();             // Observable
    
    private double totalHuella;
    private boolean calculado = false;

    // === PREGUNTAS GENERALES ===
    private int personas;
    private double consumoLuz;
    private double consumoGas;
    private double consumoAgua;

    // === ELECTRICIDAD ===
    private int fuenteEnergia;   // 1=hidro, 2=solar, 3=eólica, 4=gas/carbón
    private int bombillos;       // 1=todos, 2=algunos, 3=ninguno
    private int aparatos;        // 1=nunca, 2=a veces, 3=siempre
    private double luzAjustada;

    // === AGUA ===
    private int duchasPorDia;
    private int duracionDucha;
    private boolean tieneAhorradores;
    private double extraAgua;

    // === TRANSPORTE ===
    private int tipoTransporte;
    private double kmPorDia;
    private int diasSemana;
    private int vuelosAnuales;

    // === ALIMENTACIÓN ===
    private int tipoDieta;
    private int frecuenciaCarne;
    private int frecuenciaLacteos;
    private int origenProductos;

    // === CONSTRUCTOR ===
    public SesionFormulario() {
        sujeto.addObservador(new ConsolaObservador());
    }

    // ======================================================
    // === MÉTODOS PARA MANEJAR EL GRUPO Y EL CÁLCULO ======
    // ======================================================

    public GrupoFuentes getGrupo() {
        return grupo;
    }

    public void addFuente(FuenteHuella fuente) {
        grupo.addFuente(fuente);
    }

    public void reset() {
        grupo.limpiar();
        totalHuella = 0;
        calculado = false;
    }

    public double calcularTotal() {
        CalculadoraHuella calculadora = CalculadoraHuella.getInstance();
        totalHuella = calculadora.calcularTotal(grupo) / Math.max(personas, 1);
        calculado = true;
        sujeto.notificar(totalHuella);
        return totalHuella;
    }

    public double getTotalHuella() {
        return totalHuella;
    }

    public boolean isCalculado() {
        return calculado;
    }

    // ======================================================
    // === GETTERS Y SETTERS DE VARIABLES DEL FORMULARIO ====
    // ======================================================

    // --- Generales ---
    public int getPersonas() { return personas; }
    public void setPersonas(int personas) { this.personas = personas; }

    public double getConsumoLuz() { return consumoLuz; }
    public void setConsumoLuz(double consumoLuz) { this.consumoLuz = consumoLuz; }

    public double getConsumoGas() { return consumoGas; }
    public void setConsumoGas(double consumoGas) { this.consumoGas = consumoGas; }

    public double getConsumoAgua() { return consumoAgua; }
    public void setConsumoAgua(double consumoAgua) { this.consumoAgua = consumoAgua; }

    // ======================================================
    // ⚡ ELECTRICIDAD
    // ======================================================

    public int getFuenteEnergia() { return fuenteEnergia; }

    // Permite usar desde ChoiceBox<String>
    public void setFuenteEnergia(String value) {
        switch (value.toLowerCase()) {
            case "hidroeléctrica" -> this.fuenteEnergia = 1;
            case "solar" -> this.fuenteEnergia = 2;
            case "eólica" -> this.fuenteEnergia = 3;
            case "gas/carbón", "gas", "carbón" -> this.fuenteEnergia = 4;
            default -> this.fuenteEnergia = 1;
        }
    }

    public int getBombillos() { return bombillos; }

    public void setUsaLed(String value) {
        switch (value.toLowerCase()) {
            case "todos" -> this.bombillos = 1;
            case "algunos" -> this.bombillos = 2;
            case "ninguno" -> this.bombillos = 3;
            default -> this.bombillos = 2;
        }
    }

    public int getAparatos() { return aparatos; }

    public void setDejaAparatos(String value) {
        switch (value.toLowerCase()) {
            case "nunca" -> this.aparatos = 1;
            case "a veces" -> this.aparatos = 2;
            case "siempre" -> this.aparatos = 3;
            default -> this.aparatos = 2;
        }
    }

    public double getLuzAjustada() { return luzAjustada; }
    public void setConsumoLuzAjustado(double luzAjustada) { this.luzAjustada = luzAjustada; }

    // ======================================================
    // 💧 AGUA
    // ======================================================

    public int getDuchasPorDia() { return duchasPorDia; }

    // Para ChoiceBox<String>
    public void setDuchasPorDia(String value) {
        try {
            this.duchasPorDia = Integer.parseInt(value);
        } catch (NumberFormatException e) {
            this.duchasPorDia = 1;
        }
    }

    public int getDuracionDucha() { return duracionDucha; }

    public void setDuracionDucha(String text) {
        try {
            this.duracionDucha = Integer.parseInt(text);
        } catch (NumberFormatException e) {
            this.duracionDucha = 5;
        }
    }

    public boolean isTieneAhorradores() { return tieneAhorradores; }

    public void setTieneAhorradores(String value) {
        switch (value.toLowerCase()) {
            case "sí", "si" -> this.tieneAhorradores = true;
            case "no" -> this.tieneAhorradores = false;
            default -> this.tieneAhorradores = false;
        }
    }

    public double getExtraAgua() { return extraAgua; }
    public void setExtraAgua(double extraAgua) { this.extraAgua = extraAgua; }

    // ======================================================
    // 🧭 TRANSPORTE Y ALIMENTACIÓN (ya definidos, sin tocar)
    // ======================================================

    public int getTipoTransporte() { return tipoTransporte; }
    public void setTipoTransporte(int tipoTransporte) { this.tipoTransporte = tipoTransporte; }

    public double getKmPorDia() { return kmPorDia; }
    public void setKmPorDia(double kmPorDia) { this.kmPorDia = kmPorDia; }

    public int getDiasSemana() { return diasSemana; }
    public void setDiasSemana(int diasSemana) { this.diasSemana = diasSemana; }

    public int getVuelosAnuales() { return vuelosAnuales; }
    public void setVuelosAnuales(int vuelosAnuales) { this.vuelosAnuales = vuelosAnuales; }

    public int getTipoDieta() { return tipoDieta; }
    public void setTipoDieta(int tipoDieta) { this.tipoDieta = tipoDieta; }

    public int getFrecuenciaCarne() { return frecuenciaCarne; }
    public void setFrecuenciaCarne(int frecuenciaCarne) { this.frecuenciaCarne = frecuenciaCarne; }

    public int getFrecuenciaLacteos() { return frecuenciaLacteos; }
    public void setFrecuenciaLacteos(int frecuenciaLacteos) { this.frecuenciaLacteos = frecuenciaLacteos; }

    public int getOrigenProductos() { return origenProductos; }
    public void setOrigenProductos(int origenProductos) { this.origenProductos = origenProductos; }

}
