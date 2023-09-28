package com.example.connectdots;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * Clase principal que representa el juego Connect Dots.
 */
public class Main extends Application {
    // Variables de instancia

    /**
     * Representa la lista de puntos utilizados en el juego.
     */
    private PuntoList puntos;

    /**
     * Panel raíz que contiene todos los elementos del juego.
     */
    private Pane root;

    /**
     * Cuadro de jugador que muestra información sobre los jugadores.
     */
    private VBox playerBox;

    /**
     * Arreglo de botones que representan a los jugadores.
     */
    private Button[] jugadores;

    /**
     * Etiqueta que muestra el turno del jugador actual.
     */
    private Label turnoLabel;

    /**
     * Arreglo de etiquetas que muestran los puntajes de los jugadores.
     */
    private Label[] puntajes;

    /**
     * Arreglo que almacena los puntajes de los jugadores.
     */
    private int[] puntajesJugadores;

    /**
     * Índice del jugador actual en el arreglo de jugadores.
     */
    private int jugadorActualIndex = 0;

    /**
     * Matriz que registra los cuadrados formados en el juego.
     */
    private boolean[][][] cuadradosFormados = new boolean[8][8][4];

    /**
     * Punto seleccionado durante el juego.
     */
    private PuntoList.Punto puntoSeleccionado = null;

    /**
     * Punto que representa un puntero en el juego.
     */
    private PuntoList.Punto puntero;

    /**
     * Lista enlazada que contiene los nombres de los jugadores.
     */
    private ListaEnlazada listaJugadores;

    /**
     * Arreglo de colores utilizados para representar a los jugadores.
     */
    private Color[] coloresJugadores = {
            Color.RED,
            Color.BLUE,
            Color.GREEN,
            Color.YELLOW
    };

    /**
     * Método principal que inicia la aplicación JavaFX.
     *
     * @param args Argumentos de la línea de comandos (no utilizados).
     */
    public static void main(String[] args) {
        launch(args);
    }
    /**
     * Este método de inicio de la aplicación configura la interfaz gráfica y maneja la lógica del juego.
     *
     * @param primaryStage El escenario principal de la aplicación.
     */
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Connect Dots");
        BorderPane borderPane = new BorderPane();
        root = new Pane();
        Scene scene = new Scene(borderPane, 900, 600);
        primaryStage.setScene(scene);

        jugadores = new Button[4];
        puntajes = new Label[4];
        puntajesJugadores = new int[4];

        playerBox = new VBox(10);
        playerBox.setPadding(new Insets(10));

        // Crea una lista enlazada de jugadores
        listaJugadores = new ListaEnlazada();
        listaJugadores.agregar("Jugador 1");
        listaJugadores.agregar("Jugador 2");
        listaJugadores.agregar("Jugador 3");
        listaJugadores.agregar("Jugador 4");
        listaJugadores.iniciarTurnos();

        for (int i = 0; i < jugadores.length; i++) {
            jugadores[i] = new Button("Jugador " + (i + 1));
            Color jugadorColor = coloresJugadores[i];
            BackgroundFill backgroundFill = new BackgroundFill(jugadorColor, CornerRadii.EMPTY, Insets.EMPTY);
            Background background = new Background(backgroundFill);
            jugadores[i].setBackground(background);

            final int jugadorIndex = i; // Necesario para acceder a la variable en el evento
            jugadores[i].setOnAction(event -> {
                // Cambia el jugador actual cuando se hace clic en el botón del jugador
                listaJugadores.iniciarTurnos();
                for (int j = 0; j < jugadorIndex; j++) {
                    listaJugadores.avanzarTurno();
                }
                actualizarTurno();
            });

            puntajesJugadores[i] = 0;
            puntajes[i] = new Label("Puntaje: " + puntajesJugadores[i]);
            puntajes[i].setStyle("-fx-font-weight: bold");

            HBox playerInfo = new HBox(10);
            playerInfo.getChildren().addAll(jugadores[i], puntajes[i]);
            playerBox.getChildren().add(playerInfo);
        }

        // Label para mostrar el turno del jugador actual
        turnoLabel = new Label();
        turnoLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        puntos = new PuntoList(10, 10, 50, 50, 50);

        for (int i = 0; i < puntos.getNumRows(); i++) {
            for (int j = 0; j < puntos.getNumCols(); j++) {
                PuntoList.Punto punto = puntos.getPunto(i, j);
                root.getChildren().add(punto);

                // Maneja el evento de clic en el punto
                punto.setOnMouseClicked(event -> {
                    if (!puntos.estaConectado(punto) && puntoSeleccionado != null) {
                        Line linea = new Line(
                                puntoSeleccionado.getCenterX(),
                                puntoSeleccionado.getCenterY(),
                                punto.getCenterX(),
                                punto.getCenterY()
                        );

                        // Establece un color de línea para el jugador actual
                        linea.setStroke(coloresJugadores[jugadorActualIndex]);
                        root.getChildren().add(linea);

                        verificarCuadrados(puntoSeleccionado, punto);

                        // Avanza al siguiente jugador en la lista enlazada
                        listaJugadores.avanzarTurno();
                        actualizarTurno();

                        puntoSeleccionado = null;
                    } else {
                        puntoSeleccionado = punto;
                    }
                });
            }
        }

        crearPuntero();

        borderPane.setCenter(root);
        borderPane.setRight(playerBox);
        borderPane.setTop(turnoLabel); // Agrega el Label de turno en la parte superior

        scene.setOnKeyPressed(event -> {
            double currentX = puntero.getCenterX();
            double currentY = puntero.getCenterY();
            double moveAmount = 50; // Cantidad de movimiento en píxeles

            switch (event.getCode()) {
                case UP:
                    currentY -= moveAmount;
                    break;
                case DOWN:
                    currentY += moveAmount;
                    break;
                case LEFT:
                    currentX -= moveAmount;
                    break;
                case RIGHT:
                    currentX += moveAmount;
                    break;
            }

            // Asegúrate de que el puntero no salga de los límites de la ventana
            currentX = Math.max(0, Math.min(currentX, scene.getWidth()));
            currentY = Math.max(0, Math.min(currentY, scene.getHeight()));

            puntero.setCenterX(currentX);
            puntero.setCenterY(currentY);
        });

        // Establecer el enfoque en el Scene para recibir eventos del teclado
        scene.getRoot().requestFocus();

        primaryStage.setScene(scene);
        primaryStage.show();
        actualizarTurno(); // Llama a esta función al inicio para asegurarte de que el turno se muestre correctamente

    }


    /**
     * Actualiza el turno del jugador actual y aumenta el puntaje del jugador actual en 1.
     */
    private void actualizarTurno() {
        // Obtiene el nombre del jugador actual
        String jugadorActual = listaJugadores.obtenerJugadorActual();

        // Actualiza la etiqueta de turno para mostrar el nombre del jugador actual
        turnoLabel.setText("Turno de: " + jugadorActual);

        // Encuentra el índice del jugador actual en el arreglo de botones de jugadores
        for (int i = 0; i < jugadores.length; i++) {
            if (jugadores[i].getText().equals(jugadorActual)) {
                jugadorActualIndex = i;
            }
        }

        // Suma 1 al puntaje del jugador actual
        puntajesJugadores[jugadorActualIndex]++; //suma 1 punto al seleccionar un jugador
        puntajes[jugadorActualIndex].setText("Puntaje: " + puntajesJugadores[jugadorActualIndex]);
    }

    /**
     * Verifica si se forma un cuadrado cuando se conectan dos puntos y, si es así, aumenta el puntaje del jugador actual.
     *
     * @param punto1 El primer punto conectado.
     * @param punto2 El segundo punto conectado.
     */
    private void verificarCuadrados(PuntoList.Punto punto1, PuntoList.Punto punto2) {
        int fila1 = puntos.getRow(punto1);
        int columna1 = puntos.getCol(punto1);
        int fila2 = puntos.getRow(punto2);
        int columna2 = puntos.getCol(punto2);

        //Verifica si las lineas son verticales o horizontales
        boolean Horizontal = fila1 == fila2;
        boolean Vertical = columna1 == columna2;

        if (Horizontal) {
            if (columna1 < columna2) {
                // izquierda a derecha
                cuadradosFormados[fila1][columna1][0] = true;
            } else {
                // derecha a izquierza
                cuadradosFormados[fila1][columna2][0] = true;
            }
        } else if (Vertical) {
            if (fila1 < fila2) {
                // arriba a abajo
                cuadradosFormados[fila1][columna1][1] = true;
            } else {
                // abajo a arriba
                cuadradosFormados[fila2][columna1][1] = true;
            }
        }

        // Check if squares are formed and award points
        int puntos = 0;
        for (int row = 0; row < 7; row++) {
            for (int col = 0; col < 7; col++) {
                boolean cuadrado = cuadradosFormados[row][col][0] && cuadradosFormados[row][col][1] &&
                        cuadradosFormados[row][col+1][0] && cuadradosFormados[row+1][col][1];
                if (cuadrado && !cuadradosFormados[row][col][2]) {
                    puntos++;
                    cuadradosFormados[row][col][2] = true; // Mark square as awarded
                }
            }
        }

        // Da los puntos al jugador respectivo
        if (puntos > 0) {
            puntajesJugadores[jugadorActualIndex-1] += puntos;
            puntajes[jugadorActualIndex].setText("Puntaje: " + puntajesJugadores[jugadorActualIndex-1]);
            listaJugadores.avanzarTurno();
            actualizarTurno();
        }
    }

    /**
     * Get the line between two points.
     *
     * @param punto1 The first point.
     * @param punto2 The second point.
     * @return The line between the points.
     */
    private Line getLineBetweenPoints(PuntoList.Punto punto1, PuntoList.Punto punto2) {
        Line line = new Line(
                punto1.getCenterX(),
                punto1.getCenterY(),
                punto2.getCenterX(),
                punto2.getCenterY()
        );

        line.setStroke(coloresJugadores[jugadorActualIndex]);
        // Update the turn after drawing the line
        listaJugadores.avanzarTurno();
        actualizarTurno();

        return line;


         }

    /**
     * Crea un puntero visual en la posición inicial (50, 50) con color amarillo y lo agrega al panel raíz.
     */
    private void crearPuntero() {
        puntero = new PuntoList.Punto(50, 50); // Inicializa en la posición (50, 50)
        puntero.setFill(Color.YELLOW); // Establece el color a amarillo
        root.getChildren().add(puntero);
    }
}











