package com.example.connectdots;




import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Connect_Dots");
        Pane root = new Pane();
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);

        // Crear puntos
        Punto punto1 = new Punto(100, 100);
        Punto punto2 = new Punto(200, 100);
        Punto punto3 = new Punto(200, 200);
        Punto punto4 = new Punto(100, 200);

        root.getChildren().addAll(punto1, punto2, punto3, punto4);

        primaryStage.show();
    }
}

