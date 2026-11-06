package service;

import java.io.BufferedReader; 
import java.io.BufferedWriter; 
import java.io.File; 
import java.io.FileNotFoundException; 
import java.io.FileReader; 
import java.io.FileWriter; 
import java.io.IOException; 
import java.util.List;

import model.domain.ItemVenta;
import model.domain.Producto;
import model.domain.ProductoEscolar;
import model.domain.ProductoOficina; 
import model.domain.Venta;
import model.structures.Pila;
import model.domain.Pedido;
import model.domain.PedidosPendientes;
import model.structures.Cola;
public class VentaService {

    private static final String ARCHIVO_INVENTARIO = "data/inventario.txt"; 
    private static final String SEPARADOR = ";"; 

    private final List<Producto> productos;
    private final Pila<Venta> ventasRecientes;
    private final List<Pedido> pedidos;
    private final PedidosPendientes pendientes = new PedidosPendientes("Pedidos a proveedor");


    public VentaService(List<Producto> productos, List<Pedido> pedidos) {
        this.productos = productos;
        this.ventasRecientes = new Pila<>();
        this.pedidos = pedidos;
        cargarInventario();
        
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

    public String listarPedidosPendientes() {
        Cola<Pedido> fila = pendientes.getFila();
        if (fila.isEmpty()) {
            return "No hay pedidos pendientes";
        }
        StringBuilder sb = new StringBuilder();
        int total = fila.size(); 
        for (int i = 1; i <= total; i++) {
            Pedido pedido = fila.dequeue();
            sb.append(i).append(". ").append(pedido).append("\n");
            fila.enqueue(pedido);
        }
        return sb.toString();
}

    //lista doble y archivos 

    public Producto buscarProducto(String codigo) {
        for (Producto p : productos) {
            if (p.getCodigo().equals(codigo)) {
                return p;
            }
        }
        return null;
    }

    public boolean existeProducto(String codigo) {
        return buscarProducto(codigo) != null;
    }

    private String tipoDe(Producto p) {
        if (p instanceof ProductoEscolar) return "ESCOLAR";
        return "OFICINA";
    }

    private void escribirInventario() throws IOException {
        File carpeta = new File(ARCHIVO_INVENTARIO).getParentFile();
        if (carpeta != null) {
            carpeta.mkdirs();
        }

        StringBuilder sb = new StringBuilder();
        for (Producto p : productos) {
            sb.append(String.join(SEPARADOR, tipoDe(p), p.getCodigo(), p.getNombre(),
                    String.valueOf(p.getPrecio()), String.valueOf(p.getCantidadStock())))
              .append("\n");
        }

        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(ARCHIVO_INVENTARIO))) {
            escritor.write(sb.toString());
        }
    }

    public String guardarInventario() {
        try {
            escribirInventario();
            return "Se guardaron " + productos.size() + " productos en " + ARCHIVO_INVENTARIO;
        } catch (IOException e) {
            return "No se pudo guardar: " + e.getMessage();
        }
    }

    public String cargarInventario() {
        productos.clear();

        try (BufferedReader lector = new BufferedReader(new FileReader(new File(ARCHIVO_INVENTARIO)))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                String[] c = linea.split(SEPARADOR);
                if (c.length != 5) {
                    continue;
                }
                try {
                    String codigo = c[1];
                    String nombre = c[2];
                    double precio = Double.parseDouble(c[3]);
                    int stock = Integer.parseInt(c[4]);

                    switch (c[0]) {
                        case "OFICINA" -> productos.add(new ProductoOficina(codigo, nombre, precio, stock, "Papelería general"));
                        case "ESCOLAR" -> productos.add(new ProductoEscolar(codigo, nombre, precio, stock, "primaria"));
                        default -> { /* tipo desconocido: se omite la línea */ }
                    }
                } catch (IllegalArgumentException e) {
                   
                }
            }
        } catch (FileNotFoundException e) {
            return "No existe " + ARCHIVO_INVENTARIO + "; se inicia con el inventario vacío";
        } catch (IOException e) {
            return "No se pudo leer: " + e.getMessage();
        }
        return "Se cargaron " + productos.size() + " productos";
    }
}