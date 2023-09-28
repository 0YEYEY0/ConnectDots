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
    private ListaSimple<ListaSimple<Boolean>> lineaHorizontal;
    private ListaSimple<ListaSimple<Boolean>> lineaVertical;

    public PuntoList(int numRows, int numCols, double startX, double startY, double spacing) {
        this.numRows = numRows;
        this.numCols = numCols;
        this.spacing = spacing;

        puntos = new Punto[numRows][numCols];
        lineaHorizontal = new ListaSimple<>();
        lineaVertical = new ListaSimple<>();

        for (int i = 0; i < numRows; i++) {
            ListaSimple<Boolean> filaHorizontal = new ListaSimple<>();
            for (int j = 0; j < numCols; j++) {
                double x = startX + j * spacing;
                double y = startY + i * spacing;
                puntos[i][j] = new Punto(x, y);

                if (i < numRows - 1) {
                    filaHorizontal.agregar(false);
                }
            }
            lineaHorizontal.agregar(filaHorizontal);

            if (i < numRows - 1) {
                ListaSimple<Boolean> filaVertical = new ListaSimple<>();
                for (int j = 0; j < numCols - 1; j++) {
                    filaVertical.agregar(false);
                }
                lineaVertical.agregar(filaVertical);
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

    public double getSpacingX() {
        return spacing / (numCols - 1);
    }

    public double getSpacingY() {
        return spacing / (numRows - 1);
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

        return (fila > 0 && lineaHorizontal.obtener(fila).obtener(columna)) ||
                (fila < numRows - 1 && lineaHorizontal.obtener(fila).obtener(columna)) ||
                (columna > 0 && lineaVertical.obtener(fila).obtener(columna - 1)) ||
                (columna < numCols - 1 && lineaVertical.obtener(fila).obtener(columna));
    }

    public boolean estaConectadoHorizontal(int fila, int columna) {
        return lineaHorizontal.obtener(fila).obtener(columna);
    }

    public boolean estaConectadoVertical(int fila, int columna) {
        return lineaVertical.obtener(fila).obtener(columna);
    }

    public void conectarHorizontal(int fila, int columna) {
        if (fila >= 0 && fila < numRows - 1 && columna >= 0 && columna < numCols) {
            ListaSimple<Boolean> filaHorizontal = lineaHorizontal.obtener(fila);
            filaHorizontal.agregar(true);
        }
    }

    public void conectarVertical(int fila, int columna) {
        if (fila >= 0 && fila < numRows && columna >= 0 && columna < numCols - 1) {
            ListaSimple<Boolean> filaVertical = lineaVertical.obtener(fila);
            filaVertical.agregar(true);
        }
    }



    public static class Punto extends Circle {
        private List<Line> conexiones = new ArrayList<>();

        public Punto(double x, double y) {
            super(5, javafx.scene.paint.Color.BLACK);
            setCenterX(x);
            setCenterY(y);
        }

        public void conectar(Line linea) {
            conexiones.add(linea);
        }
    }
}




