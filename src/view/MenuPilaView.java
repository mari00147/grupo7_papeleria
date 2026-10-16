package view;

import java.util.Scanner;

import model.domain.Venta;
import service.VentaService;

public class MenuPilaView {
    private final VentaService ventaService;
    private final Scanner entrada;

    public MenuPilaView(VentaService ventaService) {
        this.ventaService = ventaService;
        this.entrada = new Scanner(System.in);
    }

    public void iniciar() {
        int opcion = -1;
        do {
            mostrarOpciones();
            try {
                opcion = Integer.parseInt(entrada.nextLine());
                switch (opcion) {
                    case 1 -> registrar();
                    case 2 -> deshacer();
                    case 3 -> consultarUltima();
                    case 4 -> verSiHayVentas();
                    case 5 -> contar();
                    case 6 -> listar();
                    case 0 -> System.out.println("Saliendo del menú de pilas...");
                    default -> System.out.println("Opción inválida");
                }
            } catch (IllegalStateException | IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (opcion != 0);
    }

    private void mostrarOpciones() {
        System.out.println("\n1. Registrar venta (push)");
        System.out.println("2. Deshacer última venta (pop)");
        System.out.println("3. Ver última venta (peek)");
        System.out.println("4. ¿Hay ventas por deshacer? (isEmpty)");
        System.out.println("5. Cantidad de ventas (size)");
        System.out.println("6. Listar ventas");
        System.out.println("0. Salir");
        System.out.print("Elija una opción: ");
    }

    private void registrar() {
        System.out.print("Código del producto: ");
        String codigo = entrada.nextLine();
        System.out.print("Cantidad: ");
        int cantidad = Integer.parseInt(entrada.nextLine());
        ventaService.registrarVenta(codigo, cantidad);
        System.out.println("Venta registrada.");
    }

    private void deshacer() {
        Venta venta = ventaService.deshacerUltimaVenta();
        System.out.println("Venta anulada: " + venta);
    }

    private void consultarUltima() {
        System.out.println("Última venta: " + ventaService.consultarUltimaVenta());
    }

    private void verSiHayVentas() {
        if (ventaService.sinVentasPorDeshacer()) {
            System.out.println("No hay ventas por deshacer");
        } else {
            System.out.println("Hay ventas por deshacer");
        }
    }

    private void contar() {
        System.out.println("Cantidad de ventas: " + ventaService.contarVentas());
    }

    private void listar() {
        System.out.println(ventaService.listarVentas());
    }
}
