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
import javafx.stage.Stage;

public class Main extends Application {
    private PuntoList puntos;
    private Pane root;
    private VBox playerBox;
    private Button[] jugadores;
    private Label turnoLabel;
    private Label[] puntajes;
    private int[] puntajesJugadores;
    private int jugadorActualIndex = 0;
    private boolean[][] cuadradosFormados = new boolean[9][9];
    private PuntoList.Punto puntoSeleccionado = null;
    private PuntoList.Punto puntero;
    private ListaEnlazada listaJugadores;

    private Color[] coloresJugadores = {
            Color.RED,
            Color.BLUE,
            Color.GREEN,
            Color.YELLOW
    };

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

    private void actualizarTurno() {
        String jugadorActual = listaJugadores.obtenerJugadorActual();
        turnoLabel.setText("Turno de: " + jugadorActual);
        for (int i = 0; i < jugadores.length; i++) {
            if (jugadores[i].getText().equals(jugadorActual)) {
                jugadorActualIndex = i;
            }
        }
        // Sumar 1 al puntaje del jugador actual
        puntajesJugadores[jugadorActualIndex]++;
        puntajes[jugadorActualIndex].setText("Puntaje: " + puntajesJugadores[jugadorActualIndex]);
    }


    private void verificarCuadrados(PuntoList.Punto punto1, PuntoList.Punto punto2) {
        int fila1 = puntos.getRow(punto1);
        int columna1 = puntos.getCol(punto1);
        int fila2 = puntos.getRow(punto2);
        int columna2 = puntos.getCol(punto2);

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
                        if (!puntos.estaConectado(puntos.getPunto(i, j))) {
                            cuadradoCompleto = false;
                            break;
                        }
                    }
                    if (!cuadradoCompleto) break;
                }

                // Si el cuadrado está completo, marcarlo como formado y aumentar el puntaje
                if (cuadradoCompleto) {
                    cuadradosFormados[minFila][minColumna] = true;
                    puntajesJugadores[jugadorActualIndex]++;
                    puntajes[jugadorActualIndex].setText("Puntaje: " + puntajesJugadores[jugadorActualIndex]);
                    System.out.println("Cuadrado formado en [" + minFila + "][" + minColumna + "]");
                }
            }
        }
    }




    private void crearPuntero() {
        puntero = new PuntoList.Punto(50, 50); // Inicializa en la posición (50, 50)
        puntero.setFill(Color.YELLOW); // Establece el color a amarillo
        root.getChildren().add(puntero);
    }
}



















































































