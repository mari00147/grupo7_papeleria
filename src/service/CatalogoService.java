package service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import model.domain.CatalogoNavegable;
import model.domain.Producto;
import model.structures.ListaDoble;
import model.structures.NodoDoble;

public class CatalogoService {
    private static final String ARCHIVO = "data/catalogo.txt";
    private static final String SEPARADOR = ";";

    private final CatalogoNavegable catalogo = new CatalogoNavegable("Catálogo de la papelería");
    private final VentaService ventaService;

    public CatalogoService(VentaService ventaService) {
        this.ventaService = ventaService;
        cargarCatalogo();
    }

    public boolean existeEnInventario(String codigo) {
        return ventaService.existeProducto(codigo);
    }

    public void registrarEnCatalogo(String codigo) {
        Producto p = ventaService.buscarProducto(codigo);
        if (p == null) {
            throw new IllegalArgumentException("No existe un producto con código " + codigo);
        }
        catalogo.getProductos().insertarAlFinal(p);
    }

    public String listarCatalogoEnOrden() {
        if (catalogo.getProductos().isEmpty()) {
            return "No hay productos en el catálogo";
        }
        return catalogo.getProductos().recorrerAdelante();
    }

    public String listarCatalogoEnOrdenInverso() {
        if (catalogo.getProductos().isEmpty()) {
            return "No hay productos en el catálogo";
        }
        return catalogo.getProductos().recorrerAtras();
    }

    public Producto verProductoAnterior() {
        return catalogo.getProductos().anterior();
    }

    public Producto verProductoSiguiente() {
        return catalogo.getProductos().siguiente();
    }

    public Producto retirarProducto() {
        return catalogo.getProductos().retirarActual();
    }

    public int contarProductos() {
        return catalogo.getProductos().size();
    }

    public boolean hayProductos() {
        return !catalogo.getProductos().isEmpty();
    }

    public boolean hayProductoAnterior() {
        return catalogo.getProductos().hayAnterior();
    }

    public boolean hayProductoSiguiente() {
        return catalogo.getProductos().haySiguiente();
    }

    public boolean hayProductoSeleccionado() {
        return catalogo.getProductos().hayActual();
    }

    //archivos: catálogo 

    private void escribirCatalogo() throws IOException {
        File carpeta = new File(ARCHIVO).getParentFile();
        if (carpeta != null) {
            carpeta.mkdirs();
        }

        StringBuilder sb = new StringBuilder();
        for (NodoDoble<Producto> n = catalogo.getProductos().getHead(); n != null; n = n.getSiguiente()) {
            sb.append(n.getDato().getCodigo()).append("\n");
        }

        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(ARCHIVO))) {
            escritor.write(sb.toString());
        }
    }

    public String guardarCatalogo() {
        try {
            escribirCatalogo();
            return "Se guardaron " + catalogo.getProductos().size() + " productos en " + ARCHIVO;
        } catch (IOException e) {
            return "No se pudo guardar: " + e.getMessage();
        }
    }

    public String cargarCatalogo() {
        ListaDoble<Producto> lista = catalogo.getProductos();
        while (lista.getHead() != null) {
            lista.eliminar(lista.getHead());
        }

        try (BufferedReader lector = new BufferedReader(new FileReader(new File(ARCHIVO)))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                String[] c = linea.split(SEPARADOR);
                if (c.length != 1 || c[0].trim().isEmpty()) {
                    continue;
                }
                Producto p = ventaService.buscarProducto(c[0].trim());
                if (p == null) {
                    continue; // el código ya no está en el inventario
                }
                lista.insertarAlFinal(p);
            }
        } catch (FileNotFoundException e) {
            return "No existe " + ARCHIVO + "; se inicia con el catálogo vacío";
        } catch (IOException e) {
            return "No se pudo leer: " + e.getMessage();
        }
        return "Se cargaron " + lista.size() + " productos";
    }
}