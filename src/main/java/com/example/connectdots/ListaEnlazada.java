/**
 * Esta clase representa una lista enlazada simple utilizada para gestionar los turnos de los jugadores en un juego.
 * Cada nodo de la lista contiene información sobre un jugador y su turno en el juego.
 */
package com.example.connectdots;

public class ListaEnlazada {
    private Nodo cabeza; // Referencia al primer nodo de la lista
    private Nodo jugadorActual; // Referencia al jugador actual

    /**
     * Constructor de la clase ListaEnlazada.
     * Inicializa la lista enlazada como vacía y el jugador actual como nulo.
     */
    public ListaEnlazada() {
        cabeza = null;
        jugadorActual = null;
    }

    /**
     * Agrega un nuevo jugador a la lista enlazada.
     *
     * @param valor El nombre o identificación del jugador a agregar.
     */
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

    /**
     * Inicia los turnos de los jugadores. El primer jugador de la lista se convierte en el jugador actual.
     */
    public void iniciarTurnos() {
        jugadorActual = cabeza;
    }

    /**
     * Obtiene el nombre o identificación del jugador actual.
     *
     * @return El nombre o identificación del jugador actual, o null si no hay jugador actual.
     */
    public String obtenerJugadorActual() {
        if (jugadorActual != null) {
            return jugadorActual.valor;
        }
        return null;
    }

    /**
     * Avanza al siguiente turno de jugador. Si se llega al final de la lista, se vuelve al primer jugador.
     */
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


