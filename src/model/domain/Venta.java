package model.domain;

import model.structures.ListaSimple;

public class Venta {

    private ListaSimple<ItemVenta> items;

    public Venta() {
        items = new ListaSimple<>();
    }

    public ListaSimple<ItemVenta> getItems() {
        return items;
    }

    public void agregarItem(ItemVenta item) {
        if (item == null) {
            throw new IllegalArgumentException("El item no puede ser nulo");
        }

        items.insertarFinal(item);
    }
}
