package com.example.connectdots;




import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class Main extends Application {
    private Punto puntoSeleccionado = null; // Punto que el jugador está conectando
    private Line lineaActual = null; // Línea que el jugador está dibujando

    private Pane root; // Declarar la variable root aquí

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Mi Juego");
        root = new Pane(); // Inicializar la variable root aquí
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);

        // Crear puntos
        Punto punto1 = new Punto(100, 100);
        Punto punto2 = new Punto(200, 100);
        Punto punto3 = new Punto(200, 200);
        Punto punto4 = new Punto(100, 200);

        root.getChildren().addAll(punto1, punto2, punto3, punto4);

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
        for (var node : root.getChildren()) {
            if (node instanceof Punto) {
                Punto punto = (Punto) node;
                if (punto.contains(x, y) && !punto.conectado) {
                    return punto;
                }
            }
        }
        return null;
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



