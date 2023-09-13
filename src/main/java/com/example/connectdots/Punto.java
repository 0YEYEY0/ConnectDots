package com.example.connectdots;


import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Punto extends Circle {
    public Punto(double x, double y) {
        super(5, Color.BLACK); // Tamaño y color de los puntos
        setCenterX(x);
        setCenterY(y);
    }
}

