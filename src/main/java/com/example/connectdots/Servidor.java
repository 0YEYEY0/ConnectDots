package com.example.connectdots;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.shape.Line;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import static javafx.application.Application.launch;

/**
 * La clase `Servidor` representa un servidor que escucha conexiones de clientes
 * y envía información a todos los clientes conectados.
 */
public class Servidor implements Runnable {

    /**
     * Inicia el servidor y comienza a escuchar conexiones de clientes.
     *
     * @param args Argumentos de línea de comandos (no utilizados en este caso).
     */
    public static void main(String[] args) {
        launch(args);
    }

    private ArrayList<Socket> clientes;

    /**
     * Crea una instancia de la clase `Servidor` y inicializa la lista de clientes.
     */
    public Servidor() {
        this.clientes = new ArrayList<>();
    }

    @Override
    public void run() {
        ServerSocket servidor = null;
        Socket sc = null;
        InputStream in;
        byte[] buffer = new byte[1024];

        try {
            // Creamos el socket del servidor
            servidor = new ServerSocket(6000);
            System.out.println("Servidor iniciado");

            // Siempre estará escuchando peticiones
            while (true) {
                // Espero a que un cliente se conecte
                sc = servidor.accept();
                System.out.println("Cliente conectado");
                clientes.add(sc);
                in = sc.getInputStream();
                int bytesRead = in.read(buffer);
                String json = new String(buffer, 0, bytesRead);
                ObjectMapper om = new ObjectMapper();
                Line linea_entrante = om.readValue(json, Line.class);
                enviarInfo(linea_entrante);
                sc.close();
            }

        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Envía información a todos los clientes conectados.
     *
     * @param g La línea que se enviará a los clientes.
     */
    public void enviarInfo(Line g) {
        for (Socket sock : clientes) {
            try {
                ObjectMapper om = new ObjectMapper();
                String json = om.writeValueAsString(g);
                OutputStream os = sock.getOutputStream();
                os.write(json.getBytes());
                os.flush();
                sock.close();
            } catch (IOException ex) {
                Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}

