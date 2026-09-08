
import model.domain.*;
import java.time.LocalDate;

public class App {
    public static void main(String[] args) {
        System.out.println("   Papelería / Demostración   ");


        ProductoOficina resma = new ProductoOficina("OF-101", "Resma de Papel Carta", 20000.0, 50, "Papelería general");

        ProductoEscolar cuadernoPrimaria = new ProductoEscolar("ES-201", "Cuaderno Cuadriculado", 10000.0, 15, "primaria");
        ProductoEscolar cuadernoSecundaria = new ProductoEscolar("ES-202", "Cuaderno Argollado", 10000.0, 20, "secundaria");

        System.out.println("Producto Oficina (+8% recargo):");
        System.out.println("   " + resma.getNombre() + " | Precio Base: $" + resma.getPrecio() + " -> Precio Final: $" + resma.calcularPrecioFinal());

        System.out.println("Producto Escolar (Primaria -10% descuento):");
        System.out.println("   " + cuadernoPrimaria.getNombre() + " | Precio Base: $" + cuadernoPrimaria.getPrecio() + " -> Precio Final: $" + cuadernoPrimaria.calcularPrecioFinal());

        System.out.println("Producto Escolar (Secundaria sin descuento):");
        System.out.println("   " + cuadernoSecundaria.getNombre() + " | Precio Base: $" + cuadernoSecundaria.getPrecio() + " -> Precio Final: $" + cuadernoSecundaria.calcularPrecioFinal());

        System.out.println("\n--- 2. VENTA Y DESCUENTO DE STOCK ---");
        System.out.println("Stock inicial de Resma: " + resma.getCantidadStock());

        try {
            System.out.println("Vendiendo 10 unidades de Resma de Papel...");
            resma.vender(10);
            System.out.println("Stock actualizado de Resma: " + resma.getCantidadStock());
        } catch (Exception e) {
            System.err.println("Error en venta: " + e.getMessage());
        }

        System.out.println("\n--- 3. RELACIONES DE VENTA (Asociación) ---");
        
        ItemVenta item1 = new ItemVenta(resma, 2);
        ItemVenta item2 = new ItemVenta(cuadernoPrimaria, 3);

        Venta venta = new Venta();
        venta.agregarItem(item1);
        venta.agregarItem(item2);

        System.out.println("Venta creada exitosamente con " + venta.getItems().size() + " ítems distintos.");


        System.out.println("\n--- 4. RELACIÓN PROVEEDOR Y PEDIDOS (1 a 0..*) ---");
        
        Proveedor proveedorNorma = new Proveedor("Distribuidora Norma", "601-555-0199", "Escolar y Oficina");


        Pedido pedido1 = new Pedido(LocalDate.now(), "Registrado", proveedorNorma);
        Pedido pedido2 = new Pedido(LocalDate.now().plusDays(3), "Pendiente", proveedorNorma);

        System.out.println("Proveedor: " + proveedorNorma.getNombre());
        System.out.println("  -> Pedido 1 [Fecha: " + pedido1.getFecha() + "] | Estado: " + pedido1.getEstado() + " | Proveedor: " + pedido1.getProveedor().getNombre());
        System.out.println("  -> Pedido 2 [Fecha: " + pedido2.getFecha() + "] | Estado: " + pedido2.getEstado() + " | Proveedor: " + pedido2.getProveedor().getNombre());


        System.out.println("\n--- 5. VALIDACIÓN DE EXCEPCIONES ---");

        try {
            System.out.println("Intentando vender 100 cuadernos (Stock disponible: " + cuadernoPrimaria.getCantidadStock() + ")...");
            cuadernoPrimaria.vender(100);
        } catch (IllegalStateException e) {
            System.out.println("-> Excepción capturada (Stock insuficiente): " + e.getMessage());
        }

        try {
            System.out.println("Intentando crear un producto con precio negativo (-1500)...");
            ProductoOficina productoInvalido = new ProductoOficina("OF-999", "Cinta Pegante", -1500.0, 10, "Papelería");
        } catch (IllegalArgumentException e) {
            System.out.println("-> Excepción capturada (Precio inválido): " + e.getMessage());
        }

        System.out.println("      TODAS LAS PRUEBAS EJECUTADAS CON ÉXITO     ");
    }
}