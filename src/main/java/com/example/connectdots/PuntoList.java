package com.example.connectdots;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import java.util.ArrayList;
import java.util.List;

public class PuntoList {
    private int numRows;
    private int numCols;
    private double spacing;
    private Punto[][] puntos;
    private boolean[][] lineaHorizontal;
    private boolean[][] lineaVertical;

    public PuntoList(int numRows, int numCols, double startX, double startY, double spacing) {
        this.numRows = numRows;
        this.numCols = numCols;
        this.spacing = spacing;

        puntos = new Punto[numRows][numCols];
        lineaHorizontal = new boolean[numRows - 1][numCols];
        lineaVertical = new boolean[numRows][numCols - 1];

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                double x = startX + j * spacing;
                double y = startY + i * spacing;
                puntos[i][j] = new Punto(x, y, i, j);

                if (i < numRows - 1) {
                    lineaHorizontal[i][j] = false;
                }
                if (j < numCols - 1) {
                    lineaVertical[i][j] = false;
                }
            }
        }
    }

    public int getNumRows() {
        return numRows;
    }

    public int getNumCols() {
        return numCols;
    }

    public Punto getPunto(int fila, int columna) {
        return puntos[fila][columna];
    }

    public int getRow(Punto punto) {
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                if (puntos[i][j] == punto) {
                    return i;
                }
            }
        }
        return -1;
    }

    public int getCol(Punto punto) {
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                if (puntos[i][j] == punto) {
                    return j;
                }
            }
        }
        return -1;
    }

    public boolean estaConectado(Punto punto) {
        int fila = getRow(punto);
        int columna = getCol(punto);

        return (fila > 0 && lineaHorizontal[fila - 1][columna]) ||
                (fila < numRows - 1 && lineaHorizontal[fila][columna]) ||
                (columna > 0 && lineaVertical[fila][columna - 1]) ||
                (columna < numCols - 1 && lineaVertical[fila][columna]);
    }

    public boolean estaConectadoHorizontal(int fila, int columna) {
        return lineaHorizontal[fila][columna];
    }

    public boolean estaConectadoVertical(int fila, int columna) {
        return lineaVertical[fila][columna];
    }

    public void conectarHorizontal(int fila, int columna) {
        lineaHorizontal[fila][columna] = true;
    }

    public void conectarVertical(int fila, int columna) {
        lineaVertical[fila][columna] = true;
    }

    public class Punto extends Circle {
        private int fila;
        private int columna;
        private List<Line> conexiones = new ArrayList<>(); // Lista de conexiones

        public Punto(double x, double y, int fila, int columna) {
            super(5, javafx.scene.paint.Color.BLACK);
            setCenterX(x);
            setCenterY(y);
            this.fila = fila;
            this.columna = columna;
        }

        public void conectar(Line linea) {
            conexiones.add(linea); // Agregar la línea a la lista de conexiones
        }

        public int getFila() {
            return fila;
        }

        public int getColumna() {
            return columna;
        }
    }
}


