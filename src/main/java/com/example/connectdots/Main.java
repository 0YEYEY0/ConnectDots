package com.example.connectdots;



import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class Main extends Application {
    private Punto[][] puntos = new Punto[10][10]; // Matriz de puntos
    private Punto puntoSeleccionado = null; // Punto que el jugador está conectando
    private Line lineaActual = null; // Línea que el jugador está dibujando

    private Pane root; // Declarar la variable root aquí
    private VBox playerBox; // Panel de jugadores

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Mi Juego");
        BorderPane borderPane = new BorderPane();
        root = new Pane(); // Inicializar la variable root aquí
        Scene scene = new Scene(borderPane, 900, 600);
        primaryStage.setScene(scene);

        // Crear matriz de puntos
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                puntos[i][j] = new Punto(50 + i * 50, 50 + j * 50);
                root.getChildren().add(puntos[i][j]);
            }
        }

        playerBox = new VBox(10); // Panel de jugadores
        playerBox.setPadding(new Insets(10));
        Button addPlayerButton = new Button("Agregar Jugador");
        addPlayerButton.setOnAction(e -> agregarJugador());
        playerBox.getChildren().add(addPlayerButton);

        borderPane.setCenter(root);
        borderPane.setRight(playerBox);

        scene.setOnMouseClicked(event -> {
            if (puntoSeleccionado == null) {
                // El jugador selecciona el primer punto
                puntoSeleccionado = encontrarPuntoClic(event.getX(), event.getY());
            } else {
                // El jugador selecciona el segundo punto
                Punto segundoPunto = encontrarPuntoClic(event.getX(), event.getY());
                if (segundoPunto != null && !segundoPunto.equals(puntoSeleccionado)) {
                    // Conectar los puntos con una línea
                    Line linea = new Line(puntoSeleccionado.getCenterX(), puntoSeleccionado.getCenterY(),
                            segundoPunto.getCenterX(), segundoPunto.getCenterY());
                    root.getChildren().add(linea); // Agregar la línea al Pane
                    lineaActual = linea;

                    // Reiniciar la selección de puntos
                    puntoSeleccionado = null;
                }
            }
        });

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Punto encontrarPuntoClic(double x, double y) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (puntos[i][j].contains(x, y) && !puntos[i][j].conectado) {
                    return puntos[i][j];
                }
            }
        }
        return null;
    }

    private void agregarJugador() {
        if (playerBox.getChildren().size() < 4) {
            // Crea un nuevo botón de jugador
            Button jugadorButton = new Button("Jugador " + (playerBox.getChildren().size() + 1));
            // Asigna un color al jugador (cambia esto según tus colores deseados)
            jugadorButton.setStyle("-fx-background-color: " + obtenerColorJugador());

            playerBox.getChildren().add(jugadorButton);
        }
    }

    private String obtenerColorJugador() {
        // Agrega aquí tu lógica para obtener colores para cada jugador
        // Puedes utilizar CSS para establecer los colores o generar colores aleatorios
        // Ejemplo: "red", "blue", "green", "yellow", etc.
        // Asegúrate de llevar un seguimiento de los colores asignados a cada jugador.
        // Retorna el color correspondiente como una cadena.
        return "gray"; // Cambia esto según tus necesidades
    }

    private class Punto extends Circle {
        private boolean conectado = false; // Indicador de si el punto ya está conectado

        public Punto(double x, double y) {
            super(5, javafx.scene.paint.Color.BLACK); // Tamaño y color de los puntos
            setCenterX(x);
            setCenterY(y);
        }
    }
}





