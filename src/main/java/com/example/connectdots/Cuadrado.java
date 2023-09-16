package com.example.connectdots;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.shape.Line;

public class Cuadrado {
    private Line[] lineas;
    private boolean completo;
    private int jugadorIndex; // Almacena el índice del jugador que completó el cuadrado

    public Cuadrado(Line[] lineas, int jugadorIndex) {
        this.lineas = lineas;
        this.completo = false;
        this.jugadorIndex = jugadorIndex; // Inicializa el índice del jugador
    }

    public Line[] getLineas() {
        return lineas;
    }

    public boolean estaCompleto() {
        return completo;
    }

    public void marcarComoCompleto() {
        completo = true;
    }


    public int getJugadorIndex() {
        return jugadorIndex;
    }
}



