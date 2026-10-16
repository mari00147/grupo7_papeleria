package utils;

import java.util.Scanner;

public class ConsoleUtils {

    private static final Scanner entrada = new Scanner(System.in);

    private ConsoleUtils() {
    }

    public static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return entrada.nextLine();
    }

    public static int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Integer.parseInt(entrada.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un número entero.");
            }
        }
    }

    public static double leerDouble(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Double.parseDouble(entrada.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un número.");
            }
        }
    }

}
