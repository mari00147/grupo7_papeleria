package model.domain;

import java.time.LocalDate;

public class Pedido {
    private LocalDate fecha;
    private String estado;
    private Proveedor proveedor;

    // Constructor que está pidiendo App.java
    public Pedido(LocalDate fecha, String estado, Proveedor proveedor) {
        this.fecha = fecha;
        this.estado = estado;
        this.proveedor = proveedor;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }
}