package com.example.connectdots;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class Main extends Application implements Observer {
    private Punto[][] puntos = new Punto[10][10];
    private Pane root;
    private VBox playerBox;
    private Button[] jugadores;
    private Label[] puntajes;
    private int[] puntajesJugadores;
    private int jugadorActualIndex = 0;
    private boolean[][] cuadradosFormados = new boolean[9][9];

    public static void main(String[] args) {
        launch(args);
    }

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

        for (int i = 0; i < jugadores.length; i++) {
            jugadores[i] = new Button("Jugador " + (i + 1));
            Color jugadorColor = Color.web(obtenerColorJugador(i));
            BackgroundFill backgroundFill = new BackgroundFill(jugadorColor, CornerRadii.EMPTY, Insets.EMPTY);
            Background background = new Background(backgroundFill);
            jugadores[i].setBackground(background);

            puntajesJugadores[i] = 0;
            puntajes[i] = new Label("Puntaje: " + puntajesJugadores[i]);
            puntajes[i].setStyle("-fx-font-weight: bold");

            HBox playerInfo = new HBox(10);
            playerInfo.getChildren().addAll(jugadores[i], puntajes[i]);
            playerBox.getChildren().add(playerInfo);
        }

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                puntos[i][j] = new Punto(50 + i * 50, 50 + j * 50, i, j);
                root.getChildren().add(puntos[i][j]);
            }
        }

        borderPane.setCenter(root);
        borderPane.setRight(playerBox);

        scene.setOnMouseClicked(event -> {
            Punto puntoClic = encontrarPuntoClic(event.getX(), event.getY());
            if (puntoClic != null && !puntoClic.estaConectado()) {
                if (puntoSeleccionado == null) {
                    puntoSeleccionado = puntoClic;
                } else if (!puntoSeleccionado.equals(puntoClic)) {
                    Line linea = new Line(puntoSeleccionado.getCenterX(), puntoSeleccionado.getCenterY(),
                            puntoClic.getCenterX(), puntoClic.getCenterY());
                    root.getChildren().add(linea);

                    verificarCuadrados(puntoSeleccionado, puntoClic);

                    jugadorActualIndex = (jugadorActualIndex + 1) % jugadores.length;
                    puntajes[jugadorActualIndex].setText("Puntaje: " + puntajesJugadores[jugadorActualIndex]);

                    puntoSeleccionado = null;
                }
            }
        });

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Punto puntoSeleccionado = null;

    private Punto encontrarPuntoClic(double x, double y) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Punto punto = puntos[i][j];
                if (punto.contains(x, y) && !punto.estaConectado()) {
                    return punto;
                }
            }
        }
        return null;
    }

    private String obtenerColorJugador(int index) {
        String[] colores = {"#FF0000", "#0000FF", "#00FF00", "#FFFF00"};
        return colores[index % colores.length];
    }

    private void verificarCuadrados(Punto punto1, Punto punto2) {
        int fila1 = punto1.getFila();
        int columna1 = punto1.getColumna();
        int fila2 = punto2.getFila();
        int columna2 = punto2.getColumna();

        // Verificar si se forma un cuadrado
        if (Math.abs(fila1 - fila2) == 1 && Math.abs(columna1 - columna2) == 1) {
            int minFila = Math.min(fila1, fila2);
            int maxFila = Math.max(fila1, fila2);
            int minColumna = Math.min(columna1, columna2);
            int maxColumna = Math.max(columna1, columna2);

            // Verificar si el cuadrado ya se formó
            if (!cuadradosFormados[minFila][minColumna]) {
                boolean cuadradoCompleto = true;

                // Verificar si todas las líneas que forman el cuadrado están conectadas
                for (int i = minFila; i <= maxFila; i++) {
                    for (int j = minColumna; j <= maxColumna; j++) {
                        if (!puntos[i][j].estaConectado()) {
                            cuadradoCompleto = false;
                            break;
                        }
                    }
                    if (!cuadradoCompleto) break;
                }

                // Si el cuadrado está completo, marcarlo como formado
                if (cuadradoCompleto) {
                    cuadradosFormados[minFila][minColumna] = true;
                    puntajesJugadores[jugadorActualIndex]++;
                    puntajes[jugadorActualIndex].setText("Puntaje: " + puntajesJugadores[jugadorActualIndex]);
                    System.out.println("Cuadrado formado en [" + minFila + "][" + minColumna + "]");
                }
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        root.getChildren().add((Line) arg);
    }

    private class Punto extends Circle {
        private int fila;
        private int columna;

        public Punto(double x, double y, int fila, int columna) {
            super(5, javafx.scene.paint.Color.BLACK);
            setCenterX(x);
            setCenterY(y);
            this.fila = fila;
            this.columna = columna;
        }

        public boolean estaConectado() {
            return root.getChildren().stream()
                    .filter(child -> child instanceof Line)
                    .map(child -> (Line) child)
                    .anyMatch(linea -> (linea.getStartX() == getCenterX() && linea.getStartY() == getCenterY() &&
                            linea.getEndX() == getCenterX() && linea.getEndY() == getCenterY()) ||
                            (linea.getEndX() == getCenterX() && linea.getEndY() == getCenterY() &&
                                    linea.getStartX() == getCenterX() && linea.getStartY() == getCenterY()));
        }

        public int getFila() {
            return fila;
        }

        public int getColumna() {
            return columna;
        }
    }
}



























































