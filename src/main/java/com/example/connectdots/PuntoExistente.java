package com.example.connectdots;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.List;

/**
 * Esta clase representa un punto existente en la cuadrícula del juego.
 * Cada punto tiene una posición en el plano y puede estar conectado a varias líneas.
 */
class PuntoExistente extends Circle {
    /** Lista de conexiones a líneas que están conectadas a este punto. */
    private List<Line> conexiones = new ArrayList<>(); // Lista de conexiones

    /**
     * Crea un nuevo punto existente en las coordenadas especificadas.
     *
     * @param x La coordenada X del punto.
     * @param y La coordenada Y del punto.
     */
    public PuntoExistente(double x, double y) {
        super(5, javafx.scene.paint.Color.BLACK); // Tamaño y color de los puntos
        setCenterX(x);
        setCenterY(y);
    }

    /**
     * Conecta este punto a una línea especificada.
     *
     * @param linea La línea que se conecta a este punto.
     */
    public void conectar(Line linea) {
        conexiones.add(linea); // Agregar la línea a la lista de conexiones
    }
}




