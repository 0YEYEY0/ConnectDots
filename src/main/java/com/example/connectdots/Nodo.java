package com.example.connectdots;

public class Nodo {
    public String valor; // Cambia el tipo según tus necesidades (puede ser el nombre del jugador)
    public Nodo siguiente;

    public Nodo(String valor) {
        this.valor = valor;
        this.siguiente = null;
    }
}

