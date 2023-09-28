package com.example.connectdots;
public class ListaSimple<T> {
    private Nodo<T> cabeza;
    private int tamaño;

    public ListaSimple() {
        cabeza = null;
        tamaño = 0;
    }

    public void agregar(T elemento) {
        Nodo<T> nuevoNodo = new Nodo<>(elemento);
        nuevoNodo.siguiente = cabeza;
        cabeza = nuevoNodo;
        tamaño++;
    }

    public T obtener(int indice) {
        if (indice < 0 || indice >= tamaño || cabeza == null) {
            throw new IndexOutOfBoundsException("Índice fuera de rango");
        }



        Nodo<T> nodoActual = cabeza;
        for (int i = 0; i < indice; i++) {
            nodoActual = nodoActual.siguiente;
        }

        return nodoActual.valor;
    }

    public int tamaño() {
        return tamaño;
    }

    private class Nodo<E> {
        E valor;
        Nodo<E> siguiente;

        Nodo(E valor) {
            this.valor = valor;
            this.siguiente = null;
        }
    }
}

