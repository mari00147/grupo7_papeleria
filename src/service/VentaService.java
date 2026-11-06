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
import model.domain.Venta;
import model.structures.Pila;
import model.domain.Pedido;
import model.domain.PedidosPendientes;
import model.structures.Cola;
import model.domain.ProductoEscolar;
import model.domain.ProductoOficina;
public class VentaService {

    private static final String  ARCHIVO_INVENTARIO = "data/inventario.txt";
    private static final String SEPARADOR = ";";

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
//archivos 

    public Producto buscarProducto(String codigo) {
        for (Producto p : productos) {
            if (p.getCodigo().equals(codigo)) {
                return p;
            }
        }
        return null;
    }

    private String tipoDe(Producto p) {
        if (p instanceof ProductoOficina) return "OFICINA";
        if (p instanceof ProductoEscolar) return "ESCOLAR";
        return "OTRO";
    }
     private String datoDe(Producto p) {
        if (p instanceof ProductoOficina o) return o.getCategoria();
        if (p instanceof ProductoEscolar e) return e.getNivelEscolar();
        return "";
    }

    private void escribirInventario() throws IOException {
        File carpeta = new File(ARCHIVO_INVENTARIO).getParentFile();
        if (carpeta != null) {
            carpeta.mkdirs();
        }
        StringBuilder sb = new StringBuilder();
        for (Producto p : productos) {
            sb.append(String.join(SEPARADOR, tipoDe(p), p.getCodigo(), p.getNombre(),
                    String.valueOf(p.getPrecio()), String.valueOf(p.getCantidadStock()), datoDe(p)))
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
        File archivo = new File(ARCHIVO_INVENTARIO);
        productos.clear();   // cargar reemplaza el contenido: no duplica

        try (BufferedReader lector = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                String[] c = linea.split(SEPARADOR, -1);
                if (c.length != 6) {
                    continue;
                }
                try {
                    double precio = Double.parseDouble(c[3]);
                    int stock = Integer.parseInt(c[4]);
                    Producto p;
                    switch (c[0]) {
                        case "OFICINA" -> p = new ProductoOficina(c[1], c[2], precio, stock, c[5]);
                        case "ESCOLAR" -> p = new ProductoEscolar(c[1], c[2], precio, stock, c[5]);
                        default -> { continue; }   // tipo desconocido: se omite la línea
                    }
                    productos.add(p);
                } catch (IllegalArgumentException e) {
                    // número mal escrito (NumberFormatException) o dato inválido: se omite la línea
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