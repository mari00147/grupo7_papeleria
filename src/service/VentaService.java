package service;

import java.util.List;

import model.domain.ItemVenta;
import model.domain.Producto;
import model.domain.Venta;
import model.structures.Pila;
import model.domain.Pedido;
import model.domain.PedidosPendientes;
import model.structures.Cola;
public class VentaService {

    private final List<Producto> productos;
    private final Pila<Venta> ventasRecientes;
    private final List<Pedido> pedidos;
    private final PedidosPendientes pendientes = new PedidosPendientes("Pedidos a proveedor");


    public VentaService(List<Producto> productos, List<Pedido> pedidos) {
        this.productos = productos;
        this.ventasRecientes = new Pila<>();
        this.pedidos = pedidos;
        
    }  
    
    // pilas
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

    public String listarVentas() {
        if (ventasRecientes.isEmpty()) {
            return "No hay ventas por deshacer";
        }
        Pila<Venta> auxiliar = new Pila<>();
        StringBuilder texto = new StringBuilder();
        int numero = 1;

        while (!ventasRecientes.isEmpty()) {
            Venta v = ventasRecientes.pop();
            texto.append(numero).append(". ").append(v).append("\n");
            auxiliar.push(v);
            numero++;
        }
        while (!auxiliar.isEmpty()) {
            ventasRecientes.push(auxiliar.pop());
        }
        return texto.toString();
    }

  //colas
    
    private Pedido buscarPedidoOFallar(int id) {
        for (Pedido p : pedidos) {
            if (p.getId() == id) {
                return p;
            }
        }
        throw new IllegalArgumentException("No existe un pedido con id " + id);
    }

    public void registrarPedidoPendiente(int id) {
        Pedido pedido = buscarPedidoOFallar(id); 
        pendientes.getFila().enqueue(pedido);
    }

    public Pedido procesarSiguientePedido() {
        
        return pendientes.getFila().dequeue();
    }

    public Pedido consultarSiguientePedido() {
        return pendientes.getFila().front();
    }

    public boolean sinPedidosPendientes() {
        return pendientes.getFila().isEmpty();
    }

    public int contarPendientes() {
        return pendientes.getFila().size();
    }

   
}