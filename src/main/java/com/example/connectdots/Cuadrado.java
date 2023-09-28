/**
 * Esta clase representa un cuadrado en el juego "Connect Dots".
 * Un cuadrado se completa cuando se conectan sus cuatro lados con líneas.
 */
package com.example.connectdots;

import javafx.scene.shape.Line;

public class Cuadrado {
    private Line[] lineas;      // Array de líneas que forman los lados del cuadrado
    private boolean completo;   // Indica si el cuadrado está completo o no
    private int jugadorIndex;   // Almacena el índice del jugador que completó el cuadrado

    /**
     * Constructor de la clase Cuadrado.
     *
     * @param lineas       Las líneas que forman los lados del cuadrado.
     * @param jugadorIndex El índice del jugador que está asociado con este cuadrado.
     */
    public Cuadrado(Line[] lineas, int jugadorIndex) {
        this.lineas = lineas;
        this.completo = false;       // Inicialmente, el cuadrado no está completo
        this.jugadorIndex = jugadorIndex; // Inicializa el índice del jugador
    }

    /**
     * Obtiene las líneas que forman los lados del cuadrado.
     *
     * @return Un array de objetos Line que representan los lados del cuadrado.
     */
    public Line[] getLineas() {
        return lineas;
    }

    /**
     * Comprueba si el cuadrado está completo o no.
     *
     * @return true si el cuadrado está completo, false en caso contrario.
     */
    public boolean estaCompleto() {
        return completo;
    }

    /**
     * Marca el cuadrado como completo.
     */
    public void marcarComoCompleto() {
        completo = true;
    }

    /**
     * Obtiene el índice del jugador asociado con este cuadrado.
     *
     * @return El índice del jugador.
     */
    public int getJugadorIndex() {
        return jugadorIndex;
    }
}




