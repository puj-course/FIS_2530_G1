package com.ejemplo.miproyectospring;

import java.util.ArrayList;
import java.util.List;

public abstract class Sujeto {
    private List<Observador> observadores = new ArrayList<>();

    public void agregarObservador(Observador o) {
        observadores.add(o);
    }

    public void eliminarObservador(Observador o) {
        observadores.remove(o);
    }

    public void notificarObservadores(String categoria, int respuesta) {
        for (Observador o : observadores) {
            o.actualizar(categoria, respuesta);
        }
    }
}
