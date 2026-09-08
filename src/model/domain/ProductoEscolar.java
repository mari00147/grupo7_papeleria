
public class ProductoEscolar extends Producto {
    private String nivelEscolar;

    public ProductoEscolar(String codigo, String nombre, double precio, int cantidadStock, String nivelEscolar) {
        super(codigo, nombre, precio, cantidadStock);
        this.nivelEscolar = nivelEscolar;
    }

    public String getNivelEscolar() {
        return nivelEscolar;
    }

    public void setNivelEscolar(String nivelEscolar) {
        this.nivelEscolar = nivelEscolar;
    }

    public double calcularPrecioFinal() {
        if (nivelEscolar != null && nivelEscolar.equalsIgnoreCase("primaria")) {
            return getPrecio() * 0.90;
        }
        return getPrecio();
    }
}
