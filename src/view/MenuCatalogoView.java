package view;

import service.CatalogoService;
import utils.ConsoleUtils;

public class MenuCatalogoView {
    private final CatalogoService catalogoService;

    public MenuCatalogoView(CatalogoService catalogoService) {
        this.catalogoService = catalogoService;
    }

    public void iniciar() {
        int opcion;
        do {
            mostrarOpciones();
            opcion = ConsoleUtils.leerEntero("Elija una opción: ");
            switch (opcion) {
                case 1 -> registrar();
                case 2 -> System.out.println(catalogoService.listarCatalogoEnOrden());
                case 3 -> System.out.println(catalogoService.listarCatalogoEnOrdenInverso());
                case 4 -> verAnterior();
                case 5 -> verSiguiente();
                case 6 -> retirar();
                case 7 -> guardarTodo();
                case 0 -> guardarTodo();
                default -> System.out.println("Opción inválida");
            }
        } while (opcion != 0);
    }

    private void mostrarOpciones() {
        System.out.println("\n--- Catálogo navegable ---");
        System.out.println("1. Registrar producto en el catálogo");
        System.out.println("2. Listar del primero al último");
        System.out.println("3. Listar del último al primero");
        System.out.println("4. Hojear producto anterior");
        System.out.println("5. Hojear producto siguiente");
        System.out.println("6. Retirar el producto que estoy mirando");
        System.out.println("7. Guardar");
        System.out.println("0. Guardar y salir");
    }

    private void registrar() {
        String codigo = ConsoleUtils.leerTexto("Código del producto del inventario: ").trim();
        if (!catalogoService.existeEnInventario(codigo)) {
            System.out.println("No existe un producto con código " + codigo);
            return;
        }
        catalogoService.registrarEnCatalogo(codigo);
        System.out.println("Producto agregado al catálogo");
    }

    private void verAnterior() {
        if (!catalogoService.hayProductos()) {
            System.out.println("No hay productos en el catálogo");
            return;
        }
        if (!catalogoService.hayProductoAnterior()) {
            System.out.println("No hay producto anterior");
            return;
        }
        System.out.println("Estás viendo: " + catalogoService.verProductoAnterior());
    }

    private void verSiguiente() {
        if (!catalogoService.hayProductos()) {
            System.out.println("No hay productos en el catálogo");
            return;
        }
        if (!catalogoService.hayProductoSiguiente()) {
            System.out.println("No hay producto siguiente");
            return;
        }
        System.out.println("Estás viendo: " + catalogoService.verProductoSiguiente());
    }

    private void retirar() {
        if (!catalogoService.hayProductoSeleccionado()) {
            System.out.println("No hay producto seleccionado: hojee uno primero (opción 4 o 5)");
            return;
        }
        System.out.println("Retirado del catálogo: " + catalogoService.retirarProducto());
    }

    private void guardarTodo() {
        System.out.println(catalogoService.guardarCatalogo());
    }
}