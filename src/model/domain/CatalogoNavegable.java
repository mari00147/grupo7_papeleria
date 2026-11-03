package model.domain;

import model.structures.ListaDoble;

public class CatalogoNavegable {
    private String nombre;
    private ListaDoble<Producto> productos;

    public CatalogoNavegable(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre del catálogo no puede estar vacio");
        }
        this.nombre = nombre;
        this.productos = new ListaDoble<>();
    }

    public String getNombre() { 
        return nombre; 
    }

    public ListaDoble<Producto> getProductos()
     { return productos;
        
      }
}