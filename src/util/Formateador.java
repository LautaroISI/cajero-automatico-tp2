package util;


public class Formateador {

    public static String formatearMonto(double monto) {
        return "$" + String.format("%,.2f", monto);
    }

    // Formatea el encabezado de una sección del menú
    public static void imprimirSeparador() {
        System.out.println("  ----------------------------------------");
    }
}
