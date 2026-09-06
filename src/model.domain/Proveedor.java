
public class Proveedor {

    private String nombre;
    private String telefono;
    private String categoriaProductos;

    public Proveedor(String categoriaProductos, String nombre, String telefono) {
        this.categoriaProductos = categoriaProductos;
        this.nombre = nombre;
        this.telefono = telefono;

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCategoriaProductos() {
        return categoriaProductos;
    }

    public void setCategoriaProductos(String categoriaProductos) {
        this.categoriaProductos = categoriaProductos;
    }

}
