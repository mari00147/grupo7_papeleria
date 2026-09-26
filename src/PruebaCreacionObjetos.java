import model.domain.ProductoOficina;
import model.domain.ProductoEscolar;
import model.domain.ItemVenta;
import model.domain.Venta;

public class PruebaCreacionObjetos {

    public static void main(String[] args) {

        ProductoOficina oficina = new ProductoOficina(
                "001",
                "lapiz",
                1500,
                100,
                "escritura");

        ProductoEscolar escolar = new ProductoEscolar(
                "002",
                "Cuaderno",
                5000,
                100,
                "primaria");

        System.out.println("Precio final oficina: " + oficina.calcularPrecioFinal());
        System.out.println("Precio final escolar: " + escolar.calcularPrecioFinal());

        ItemVenta itemOficina = new ItemVenta(oficina, 2);
        ItemVenta itemEscolar = new ItemVenta(escolar, 3);

        Venta venta = new Venta();

        venta.agregarItem(itemOficina);
        venta.agregarItem(itemEscolar);

        System.out.println("Cantidad de items en la venta: " + venta.getItems().size());
    }
}