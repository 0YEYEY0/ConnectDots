package com.example.connectdots;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import java.util.ArrayList;
import java.util.List;

/**
 * La clase `PuntoList` representa una cuadrícula de puntos con conexiones
 * horizontales y verticales entre ellos.
 */
public class PuntoList {
    private int numRows;
    private int numCols;
    private double spacing;
    private Punto[][] puntos;
    private ListaSimple<ListaSimple<Boolean>> lineaHorizontal;
    private ListaSimple<ListaSimple<Boolean>> lineaVertical;

    /**
     * Crea una nueva instancia de la clase `PuntoList` con el número de filas
     * y columnas especificado, así como la información de espaciado inicial.
     *
     * @param numRows  Número de filas en la cuadrícula.
     * @param numCols  Número de columnas en la cuadrícula.
     * @param startX   Coordenada X del punto de inicio.
     * @param startY   Coordenada Y del punto de inicio.
     * @param spacing  Espaciado entre los puntos.
     */
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

    /**
     * Obtiene el número de filas en la cuadrícula.
     *
     * @return Número de filas.
     */
    public int getNumRows() {
        return numRows;
    }

    /**
     * Obtiene el número de columnas en la cuadrícula.
     *
     * @return Número de columnas.
     */
    public int getNumCols() {
        return numCols;
    }

    /**
     * Obtiene el punto en la fila y columna especificadas.
     *
     * @param fila     Índice de fila del punto.
     * @param columna  Índice de columna del punto.
     * @return Punto en la posición especificada.
     */
    public Punto getPunto(int fila, int columna) {
        return puntos[fila][columna];
    }

    /**
     * Obtiene el espaciado en el eje X entre los puntos.
     *
     * @return Espaciado en el eje X.
     */
    public double getSpacingX() {
        return spacing / (numCols - 1);
    }

    /**
     * Obtiene el espaciado en el eje Y entre los puntos.
     *
     * @return Espaciado en el eje Y.
     */
    public double getSpacingY() {
        return spacing / (numRows - 1);
    }

    /**
     * Obtiene la fila en la que se encuentra el punto especificado.
     *
     * @param punto Punto cuya fila se va a encontrar.
     * @return Índice de fila del punto, o -1 si no se encuentra en la cuadrícula.
     */
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

    /**
     * Obtiene la columna en la que se encuentra el punto especificado.
     *
     * @param punto Punto cuya columna se va a encontrar.
     * @return Índice de columna del punto, o -1 si no se encuentra en la cuadrícula.
     */
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

    /**
     * Comprueba si un punto está conectado a otros puntos en la cuadrícula.
     *
     * @param punto Punto que se va a comprobar.
     * @return `true` si el punto está conectado, `false` en caso contrario.
     */
    public boolean estaConectado(Punto punto) {
        int fila = getRow(punto);
        int columna = getCol(punto);

        return (fila > 0 && lineaHorizontal.obtener(fila).obtener(columna)) ||
                (fila < numRows - 1 && lineaHorizontal.obtener(fila).obtener(columna)) ||
                (columna > 0 && lineaVertical.obtener(fila).obtener(columna - 1)) ||
                (columna < numCols - 1 && lineaVertical.obtener(fila).obtener(columna));
    }

    /**
     * Comprueba si un punto está conectado horizontalmente en la posición especificada.
     *
     * @param fila     Índice de fila del punto.
     * @param columna  Índice de columna del punto.
     * @return `true` si el punto está conectado horizontalmente, `false` en caso contrario.
     */
    public boolean estaConectadoHorizontal(int fila, int columna) {
        return lineaHorizontal.obtener(fila).obtener(columna);
    }

    /**
     * Comprueba si un punto está conectado verticalmente en la posición especificada.
     *
     * @param fila     Índice de fila del punto.
     * @param columna  Índice de columna del punto.
     * @return `true` si el punto está conectado verticalmente, `false` en caso contrario.
     */
    public boolean estaConectadoVertical(int fila, int columna) {
        return lineaVertical.obtener(fila).obtener(columna);
    }

    /**
     * Conecta dos puntos horizontalmente en la posición especificada.
     *
     * @param fila     Índice de fila en la que se realizará la conexión.
     * @param columna  Índice de columna en la que se realizará la conexión.
     */
    public void conectarHorizontal(int fila, int columna) {
        if (fila >= 0 && fila < numRows - 1 && columna >= 0 && columna < numCols) {
            ListaSimple<Boolean> filaHorizontal = lineaHorizontal.obtener(fila);
            filaHorizontal.agregar(true);
        }
    }

    /**
     * Conecta dos puntos verticalmente en la posición especificada.
     *
     * @param fila     Índice de fila en la que se realizará la conexión.
     * @param columna  Índice de columna en la que se realizará la conexión.
     */
    public void conectarVertical(int fila, int columna) {
        if (fila >= 0 && fila < numRows && columna >= 0 && columna < numCols - 1) {
            ListaSimple<Boolean> filaVertical = lineaVertical.obtener(fila);
            filaVertical.agregar(true);
        }
    }

    /**
     * La clase `Punto` representa un punto en la cuadrícula con conexiones a otras líneas.
     */
    public static class Punto extends Circle {
        private List<Line> conexiones = new ArrayList<>();

        /**
         * Crea un nuevo punto en la posición especificada.
         *
         * @param x Coordenada X del punto.
         * @param y Coordenada Y del punto.
         */
        public Punto(double x, double y) {
            super(5, javafx.scene.paint.Color.BLACK);
            setCenterX(x);
            setCenterY(y);
        }

        /**
         * Conecta el punto a una línea.
         *
         * @param linea Línea a la que se conectará el punto.
         */
        public void conectar(Line linea) {
            conexiones.add(linea);
        }
    }
}





