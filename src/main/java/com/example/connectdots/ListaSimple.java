package com.example.connectdots;

/**
 * Esta clase representa una lista simple enlazada genérica.
 *
 * @param <T> El tipo de elemento que se almacenará en la lista.
 */
public class ListaSimple<T> {
    private Nodo<T> cabeza; // Referencia al primer nodo de la lista
    private int tamaño;    // Tamaño actual de la lista

    /**
     * Constructor de la clase ListaSimple.
     * Inicializa la lista como vacía y el tamaño como 0.
     */
    public ListaSimple() {
        cabeza = null;
        tamaño = 0;
    }

    /**
     * Agrega un elemento al principio de la lista.
     *
     * @param elemento El elemento a agregar a la lista.
     */
    public void agregar(T elemento) {
        Nodo<T> nuevoNodo = new Nodo<>(elemento);
        nuevoNodo.siguiente = cabeza;
        cabeza = nuevoNodo;
        tamaño++;
    }

    /**
     * Obtiene el elemento en el índice especificado.
     *
     * @param indice El índice del elemento que se desea obtener.
     * @return El elemento en el índice especificado.
     * @throws IndexOutOfBoundsException Si el índice está fuera de rango.
     */
    public T obtener(int indice) throws IndexOutOfBoundsException {
        if (indice < 0 || indice >= tamaño || cabeza == null) {
            throw new IndexOutOfBoundsException("Índice fuera de rango");
        }

        Nodo<T> nodoActual = cabeza;
        for (int i = 0; i < indice; i++) {
            nodoActual = nodoActual.siguiente;
        }

        return nodoActual.valor;
    }

    /**
     * Obtiene el tamaño actual de la lista.
     *
     * @return El tamaño actual de la lista.
     */
    public int tamaño() {
        return tamaño;
    }

    /**
     * Clase interna que representa un nodo en la lista enlazada.
     *
     * @param <E> El tipo de valor que almacena el nodo.
     */
    private class Nodo<E> {
        E valor;        // Valor almacenado en el nodo
        Nodo<E> siguiente; // Referencia al siguiente nodo

        /**
         * Constructor de la clase Nodo.
         *
         * @param valor El valor que se almacenará en el nodo.
         */
        Nodo(E valor) {
            this.valor = valor;
            this.siguiente = null;
        }
    }
}


