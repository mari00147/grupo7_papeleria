package service;

import model.domain.CatalogoNavegable;
import model.domain.Producto;
import model.structures.ListaDoble;
import model.structures.NodoDoble;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CatalogoService {
    private static final String ARCHIVO_CATALOGO = "data/catalogo.txt";
    private static final String SEPARADOR = ";";

    // Un solo catálogo por Service
    private final CatalogoNavegable catalogo = new CatalogoNavegable("Catálogo de la papelería");
    private final VentaService ventaService;

    // El VentaService debe crearse ANTES (ya cargó el inventario y el catálogo refiere productos por código)
    public CatalogoService(VentaService ventaService) {
        this.ventaService = ventaService;
        cargarCatalogo();
    }

    private ListaDoble<Producto> lista() {
        return catalogo.getProductos();
    }

    // ---------- Funcionalidad ----------

    public boolean existeEnInventario(String codigo) {
        return ventaService.buscarProducto(codigo) != null;
    }

    public void registrarProducto(String codigo) {
        Producto p = ventaService.buscarProducto(codigo);
        if (p == null) {
            throw new IllegalArgumentException("El producto " + codigo + " no existe en el inventario");
        }
        lista().insertarAlFinal(p);
    }

    public String listarCatalogoAdelante() {
        if (lista().isEmpty()) {
            return "No hay productos en el catálogo";
        }
        return lista().recorrerAdelante();
    }

    public String listarCatalogoAtras() {
        if (lista().isEmpty()) {
            return "No hay productos en el catálogo";
        }
        return lista().recorrerAtras();
    }

    public Producto verProductoAnterior() {
        return lista().anterior();
    }

    public Producto verProductoSiguiente() {
        return lista().siguiente();
    }

    // Solo lo quita del catálogo; el inventario no se toca
    public Producto retirarDelCatalogo() {
        return lista().retirarActual();
    }

    public int contarProductos() {
        return lista().size();
    }

    public boolean hayProductoAnterior() { return lista().hayAnterior(); }
    public boolean hayProductoSiguiente() { return lista().haySiguiente(); }
    public boolean hayProductoSeleccionado() { return lista().haySeleccionado(); }

    // ---------- Archivos ----------

    private void escribirCatalogo() throws IOException {
        File carpeta = new File(ARCHIVO_CATALOGO).getParentFile();
        if (carpeta != null) {
            carpeta.mkdirs();
        }
        StringBuilder sb = new StringBuilder();
        NodoDoble<Producto> n = lista().getHead(); // ADELANTE, de head a tail
        while (n != null) {
            sb.append(n.getDato().getCodigo()).append("\n");
            n = n.getSiguiente();
        }
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(ARCHIVO_CATALOGO))) {
            escritor.write(sb.toString());
        }
    }

    public String guardarCatalogo() {
        try {
            escribirCatalogo();
            return "Se guardaron " + lista().size() + " productos en " + ARCHIVO_CATALOGO;
        } catch (IOException e) {
            return "No se pudo guardar: " + e.getMessage();
        }
    }

    public String cargarCatalogo() {
        File archivo = new File(ARCHIVO_CATALOGO);

        while (lista().getHead() != null) {      // cargar reemplaza, no duplica
            lista().eliminar(lista().getHead());
        }

        try (BufferedReader lector = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                String[] campos = linea.split(SEPARADOR, -1);
                if (campos.length != 1) {
                    continue;
                }
                Producto p = ventaService.buscarProducto(campos[0].trim());
                if (p == null) {
                    continue; // código que no está en el inventario: no se puede enlazar
                }
                lista().insertarAlFinal(p);
            }
        } catch (FileNotFoundException e) {
            return "No existe " + ARCHIVO_CATALOGO + "; se inicia con el catálogo vacío";
        } catch (IOException e) {
            return "No se pudo leer: " + e.getMessage();
        }
        return "Se cargaron " + lista().size() + " productos";
    }
}
