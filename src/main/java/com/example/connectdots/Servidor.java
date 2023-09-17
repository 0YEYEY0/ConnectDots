package com.example.connectdots;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.beans.Observable;
import javafx.scene.shape.Line;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import static javafx.application.Application.launch;

public class Servidor implements Runnable {

    public static void main(String[] args) {
        launch(args);
    }
    private ArrayList<Socket> clientes;



    public Servidor() {
        this.clientes = new ArrayList();
    }

    @Override
    public void run() {

        ServerSocket servidor = null;
        Socket sc = null;
        InputStream in;
        byte[] buffer=new byte[1024];

        try {
            //Creamos el socket del servidor
            servidor = new ServerSocket(6000);
            System.out.println("Servidor iniciado");

            //Siempre estara escuchando peticiones
            while (true) {

                //Espero a que un cliente se conecte
                sc = servidor.accept();

                System.out.println("Cliente conectado");

                clientes.add(sc);

                in= sc.getInputStream();

                int bytesRead = in.read(buffer);
                String json = new String(buffer,0,bytesRead);

                ObjectMapper om = new ObjectMapper();

                Line linea_entrante = om.readValue(json,Line.class);

                enviarInfo(linea_entrante);
                sc.close();
            }

        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

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
