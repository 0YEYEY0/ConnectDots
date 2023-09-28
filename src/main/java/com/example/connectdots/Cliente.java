/**
 * Esta clase representa un cliente que se conecta a un servidor a través de un socket
 * y recibe objetos serializados. Notifica a los observadores cuando recibe un objeto.
 */
package com.example.connectdots;

import javafx.scene.shape.Line;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cliente extends Observable implements Runnable {

    private int puerto;
    private Line linea;

    /**
     * Constructor de la clase Cliente.
     *
     * @param puerto El puerto al que se conectará el cliente.
     * @param linea  La línea que se utilizará para representar los datos recibidos.
     */
    public Cliente(int puerto, Line linea) {
        this.puerto = puerto;
        this.linea = linea;
    }

    /**
     * Método que se ejecuta cuando se inicia un nuevo hilo para el cliente.
     * Establece una conexión con el servidor y recibe objetos serializados,
     * notificando a los observadores cuando se recibe un objeto.
     */
    @Override
    public void run() {
        // Host del servidor
        final String HOST = "172.18.238.230";
        try {
            // Creo el socket para conectarme con el servidor
            Socket sc = new Socket(HOST, puerto);
            ObjectInputStream ois = new ObjectInputStream(sc.getInputStream());
            while (true) {
                // Lee un objeto serializado desde el servidor
                Main g = (Main) ois.readObject();

                // Notifica a los observadores que se ha recibido un objeto
                this.setChanged();
                this.notifyObservers(g);
                this.clearChanged();
            }

        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

