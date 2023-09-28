package com.example.connectdots;

/**
 * Esta clase representa un nodo en una estructura de datos enlazada.
 * Cada nodo contiene un valor y una referencia al siguiente nodo en la secuencia.
 */
public class Nodo {
    /** El valor almacenado en este nodo. Puede ser cualquier tipo según tus necesidades. */
    public String valor; // Cambia el tipo según tus necesidades (puede ser el nombre del jugador)

    /** La referencia al siguiente nodo en la secuencia. */
    public Nodo siguiente;

    /**
     * Crea un nuevo nodo con el valor especificado.
     *
     * @param valor El valor a almacenar en el nodo.
     */
    public Nodo(String valor) {
        this.valor = valor;
        this.siguiente = null;
    }
}


