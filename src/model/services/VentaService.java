package model.services;

import java.util.List;

import model.domain.ItemVenta;
import model.domain.Producto;
import model.domain.Venta;
import model.structures.Pila;

public class VentaService {

    private final List<Producto> productos;
    private final Pila<Venta> ventasRecientes;

    public VentaService(List<Producto> productos) {
        this.productos = productos;
        this.ventasRecientes = new Pila<>();
    }  
    
    private Producto buscarProductoOFallar(String codigo) {
        for (Producto p : productos) {
            if (p.getCodigo().equals(codigo)) {
                return p;
            }
        }
        throw new IllegalArgumentException("No existe un producto con código " + codigo);
    }

    public void registrarVenta(String codigoProducto, int cantidad) {
        Producto producto = buscarProductoOFallar(codigoProducto);

        if (cantidad <=0){
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero");
        }

        if (cantidad > producto.getCantidadStock()) {
            throw new IllegalArgumentException("Stock insuficiente para " + producto.getNombre());
        }
        ItemVenta item = new ItemVenta(producto, cantidad);
        producto.vender(cantidad);

        Venta venta = new Venta();
        venta.agregarItem(item);
        ventasRecientes.push(venta);
    }

    public Venta deshacerUltimaVenta() {
        Venta venta = ventasRecientes.pop();

        for (ItemVenta item : venta.getItems()) {
            Producto p = item.getProducto();
            p.setCantidadStock(p.getCantidadStock() + item.getCantidad());
        }
        return venta;
    }

    public Venta consultarUltimaVenta() {
        return ventasRecientes.peek();
    }


    public boolean sinVentasPorDeshacer() {
        return ventasRecientes.isEmpty();
    }

    
    public int contarVentas() {
        return ventasRecientes.size();
    }

}