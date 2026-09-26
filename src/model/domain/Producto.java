package model.domain;

public abstract class Producto implements Vendible {

    private String codigo;
    private String nombre;
    private double precio;
    private int cantidadStock;

    public Producto(String codigo, String nombre, double precio, int cantidadStock) {

        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("El código no puede estar vacío");
        }

        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }

        if (precio <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor que cero");
        }

        if (cantidadStock < 0) {
            throw new IllegalArgumentException("La cantidad de stock no puede ser negativa");
        }

        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidadStock = cantidadStock;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCantidadStock() {
        return cantidadStock;
    }

    public void setCantidadStock(int cantidadStock) {
        this.cantidadStock = cantidadStock;
    }

    @Override
    public void vender(int cantidad) {

        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero");
        }

        if (cantidad > cantidadStock) {
            throw new IllegalArgumentException(
                    "No se encuentra disponible la cantidad requerida");
        }

        cantidadStock -= cantidad;

        System.out.println("Procesando ...");
    }

    @Override
    public abstract double calcularPrecioFinal();
}
