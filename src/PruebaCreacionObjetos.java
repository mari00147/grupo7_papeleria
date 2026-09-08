import model.domain.Producto;
import model.domain.ProductoEscolar;
import model.domain.ProductoOficina;

public class PruebaCreacionObjetos {
    public static void main(String[] args) {
        ProductoOficina oficina = new ProductoOficina("001", "lapiz", 1500, 100, "escritura");
        ProductoEscolar escolar = new ProductoEscolar("002", "Cuaderno", 5000, 100, "primaria");

        System.out.println("Precio final oficina: " + oficina.calcularPrecioFinal());
        System.out.println("Precio final escolar: " + escolar.calcularPrecioFinal());
    }
}