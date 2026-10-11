package model.structures;

public class Pila<T> {

private Nodo<T> tope;
private int tamanio;


public Pila() {
    this.tope = null;
    this.tamanio = 0;
}

public void push(T valor){
    if (valor == null) {
        throw new IllegalArgumentException("No se puede agregar un valor nulo a la pila");
    }
    Nodo<T> nuevo = new Nodo<>(valor);
    nuevo.setSiguiente(this.tope);
    this.tope = nuevo;
     this.tamanio++;
}
    public T pop(){
     if (isEmpty()) {
        throw new IllegalStateException("la pila esta vacia");
    }
    T valor = this.tope.getDato();
     this.tope = this.tope.getSiguiente();
    this.tamanio--;
    return valor;
}
    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("La pila está vacía");
        }
        return this.tope.getDato();
}
    public boolean isEmpty() {
        return this.tope == null;
    }

    public int size() {
        return this.tamanio;
}
}