package com.example.connectdots;


import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.List;

class Punto extends Circle {
    private List<Line> conexiones = new ArrayList<>(); // Lista de conexiones
    public Punto(double x, double y) {
        super(5, javafx.scene.paint.Color.BLACK); // Tamaño y color de los puntos
        setCenterX(x);
        setCenterY(y);
    }

    public void conectar(Line linea) {
        conexiones.add(linea); // Agregar la línea a la lista de conexiones
    }
}


