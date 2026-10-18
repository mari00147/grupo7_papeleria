package model.structures;


import java.util.NoSuchElementException;

public class Cola<T> {
    private Nodo<T> front;
    private Nodo<T> back;
    private int size;

    public Cola() {
        this.front = null;
        this.back = null;
        this.size = 0;
    }

   
    public void enqueue(T dato) {
        if (dato == null) {
            throw new IllegalArgumentException("El dato no puede se null");
        }

        Nodo<T> nuevoNodo = new Nodo<>(dato);
        if (isEmpty()) {
            this.front = nuevoNodo;
        } else {
            this.back.setSiguiente(nuevoNodo);
        }
        this.back = nuevoNodo;
        this.size++;
    }

   
    public T dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("La cola esta vacia");
        }
        T dato = this.front.getDato();
        this.front = this.front.getSiguiente();
        if (this.front == null) {
            this.back = null;
        }
        this.size--;
        return dato;
    }

    
    public T front() {
        if (isEmpty()) {
            throw new NoSuchElementException("La cola esta vacia");
        }
        return this.front.getDato();
    }

    public boolean isEmpty() {
        return this.front == null;
    }


    
    public int size() {
        return this.size;
    }

    
}
