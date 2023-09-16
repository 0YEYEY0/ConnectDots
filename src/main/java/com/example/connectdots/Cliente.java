package com.example.connectdots;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cliente extends Observable implements Runnable {

    private int puerto;

    public Cliente(int puerto) {
        this.puerto = puerto;
    }

    @Override
    public void run() {
        //Host del servidor
        final String HOST = "172.18.238.230";
        try {
            //Creo el socket para conectarme con el cliente
            Socket sc = new Socket(HOST, puerto);
            ObjectInputStream ois = new ObjectInputStream(sc.getInputStream());
            while (true) {

                Main g = (Main) ois.readObject();

                this.setChanged();
                this.notifyObservers(g);
                this.clearChanged();

            }

        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
