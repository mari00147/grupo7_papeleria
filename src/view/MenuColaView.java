package view;

import model.domain.Pedido;
import service.VentaService;
import utils.ConsoleUtils;

public class MenuColaView {
    private final VentaService ventaService;

    public MenuColaView(VentaService ventaService) {
        this.ventaService = ventaService;
    }

    public void iniciar() {
        int opcion;
        do {
            mostrarOpciones();
            opcion = ConsoleUtils.leerEntero("Elija una opción: ");
            switch (opcion) {
                case 1 -> registrarPedido();
                case 2 -> procesar();
                case 3 -> consultarSiguiente();
                case 4 -> verSiHayPendientes();
                case 5 -> contar();
                case 6 -> listar();
                case 0 -> System.out.println("Saliendo del menú de colas...");
                default -> System.out.println("Opción inválida");
            }
        } while (opcion != 0);
    }

    private void mostrarOpciones() {
        System.out.println("1. Registrar pedido pendiente (enqueue)");
        System.out.println("2. Procesar el siguiente pedido (dequeue)");
        System.out.println("3. Ver cuál sigue (front)");
        System.out.println("4. ¿Hay pedidos pendientes? (isEmpty)");
        System.out.println("5. Cantidad de pendientes (size)");
        System.out.println("6. Listar la fila");
        System.out.println("0. Salir");
    }

    private void registrarPedido() {
        int id = ConsoleUtils.leerEntero("Id del pedido: ");
        ventaService.registrarPedidoPendiente(id);
        System.out.println("Pedido registrado en la fila de pendientes.");
    }

    private void procesar() {
        if (ventaService.sinPedidosPendientes()) {
            System.out.println("No hay pedidos pendientes");
            return;
        }
        Pedido pedido = ventaService.procesarSiguientePedido();
        System.out.println("Pedido procesado: " + pedido);
    }

    private void consultarSiguiente() {
        if (ventaService.sinPedidosPendientes()) {
            System.out.println("No hay pedidos pendientes");
            return;
        }
        Pedido pedido = ventaService.consultarSiguientePedido();
        System.out.println("Sigue: " + pedido);
    }

    private void verSiHayPendientes() {
        if (ventaService.sinPedidosPendientes()) {
            System.out.println("No hay pedidos pendientes");
        } else {
            System.out.println("Hay pedidos pendientes");
        }
    }

    private void contar() {
        System.out.println("Pedidos pendientes: " + ventaService.contarPendientes());
    }

    private void listar() {
        System.out.println(ventaService.listarPedidosPendientes());
    }
}