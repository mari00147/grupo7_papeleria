public class ProductoOficina extends Producto {
private String categoria;

public ProductoOficina(String codigo, String nombre, double precio, int cantidadStock,String categoria) {
    super(codigo,nombre, precio, cantidadStock);
    this.categoria = categoria;

}

public String getCategoria() {
    return categoria;
}

public void setCategoria(String categoria) {
    this.categoria = categoria;
}
   

public double CalcularPrecioFinal(){
 return getPrecio()*1.08;
}
}
