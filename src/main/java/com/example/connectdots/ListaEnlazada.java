package com.example.connectdots;

public class ListaEnlazada {
    private Nodo cabeza; // Referencia al primer nodo de la lista
    private Nodo jugadorActual; // Referencia al jugador actual

    public ListaEnlazada() {
        cabeza = null;
        jugadorActual = null;
    }

    public void agregar(String valor) {
        Nodo nuevoNodo = new Nodo(valor);
        if (cabeza == null) {
            cabeza = nuevoNodo;
        } else {
            Nodo actual = cabeza;
            while (actual.siguiente != null) {
                actual = actual.siguiente;
            }
            actual.siguiente = nuevoNodo;
        }
    }

    public void iniciarTurnos() {
        jugadorActual = cabeza;
    }

    public String obtenerJugadorActual() {
        if (jugadorActual != null) {
            return jugadorActual.valor;
        }
        return null;
    }

    public void avanzarTurno() {
        if (jugadorActual != null) {
            jugadorActual = jugadorActual.siguiente;
            if (jugadorActual == null) {
                // Si llegamos al final de la lista, volvemos al principio
                jugadorActual = cabeza;
            }
        }
    }
}

