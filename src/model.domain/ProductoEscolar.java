public class ProductoEscolar extends Producto {
    private String nivelEscolar;

    public ProductoEscolar(String nivelEscolar) {
    super(null, null, 0.0, 0);
    this.nivelEscolar = nivelEscolar;
    }

    public String getNivelEscolar() {
        return nivelEscolar;
    }

    public void setNivelEscolar(String nivelEscolar) {
        this.nivelEscolar = nivelEscolar;
    }
    
    @Override
    public double calcularPrecioFinal() {
        if (this.nivelEscolar != null && this.nivelEscolar.equalsIgnoreCase("primaria")) {
            return getPrecio() * 0.90;
        }
        return getPrecio();
    }
}

