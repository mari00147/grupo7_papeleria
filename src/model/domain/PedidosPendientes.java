package model.domain;

import model.structures.Cola;

public class PedidosPendientes {
    private String descripcion;
    private Cola<Pedido> fila;

    public PedidosPendientes(String descripcion) {
        if (descripcion == null || descripcion.isBlank()) {
            throw new IllegalArgumentException("La descripción no puede ser null ni vacía");
        }
        this.descripcion = descripcion;
        this.fila = new Cola<>();
    }

    public Cola<Pedido> getFila() {
        return fila;
    }
}