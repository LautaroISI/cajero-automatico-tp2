package ui;

import exception.CuentaInactivaException;
import exception.LimiteExtraccionException;
import exception.SaldoInsuficienteException;
import model.CuentaBancaria;
import service.CuentaService;
import util.Formateador;

import java.util.InputMismatchException;
import java.util.Scanner;


public class MenuCajero {

    private Scanner sc;
    private CuentaService servicio;
    private CuentaBancaria cuentaActual; // la cuenta que está usando el cliente ahora

    public MenuCajero(CuentaService servicio) {
        this.sc = new Scanner(System.in);
        this.servicio = servicio;
    }

    // Punto de entrada del menú interactivo
    public void iniciar() {
        System.out.println("\n  ========================================");
        System.out.println("         BIENVENIDO AL CAJERO ATM        ");
        System.out.println("  ========================================");

        seleccionarCuenta();

        if (cuentaActual == null) {
            System.out.println("  Cuenta no encontrada. Saliendo...");
            return;
        }

        mostrarMenuPrincipal();
    }

  
    private void seleccionarCuenta() {
        System.out.print("\n  Ingrese número de cuenta: ");
        String numero = sc.next();
        cuentaActual = servicio.buscarPorNumero(numero);

        if (cuentaActual == null) {
            System.out.println("  No existe una cuenta con ese número.");
        } else {
            System.out.println("  Bienvenido/a, " + cuentaActual.getTitular() + "!");
        }
    }

   
    private void mostrarMenuPrincipal() {
        int opcion;

        do {
            System.out.println("\n  ========================================");
            System.out.println("  Cuenta: " + cuentaActual.getNumeroCuenta() +
                               " | Titular: " + cuentaActual.getTitular());
            Formateador.imprimirSeparador();
            System.out.println("  1. Depositar");
            System.out.println("  2. Extraer");
            System.out.println("  3. Transferir a otra cuenta");
            System.out.println("  4. Consultar saldo");
            System.out.println("  5. Ver historial");
            System.out.println("  6. Ver todas las cuentas");
            System.out.println("  0. Salir");
            System.out.print("  Opción: ");

         
            opcion = leerEntero();

            try {
                
                switch (opcion) {

                    case 1 -> {
                        System.out.print("  Monto a depositar: $");
                        double monto = leerDouble();
                        cuentaActual.depositar(monto);
                        System.out.println("  ✓ Depósito exitoso. Saldo actual: " +
                            Formateador.formatearMonto(cuentaActual.getSaldo()));
                    }

                    case 2 -> {
                        System.out.print("  Monto a extraer: $");
                        double monto = leerDouble();
                        cuentaActual.extraer(monto);
                        System.out.println("  ✓ Extracción exitosa. Saldo actual: " +
                            Formateador.formatearMonto(cuentaActual.getSaldo()));
                    }

                    case 3 -> {
                        System.out.print("  Número de cuenta destino: ");
                        String nroDest = sc.next();
                        CuentaBancaria destino = servicio.buscarPorNumero(nroDest);

                        if (destino == null) {
                            System.out.println("  [!] Cuenta destino no encontrada.");
                        } else {
                            System.out.print("  Monto a transferir: $");
                            double monto = leerDouble();
                            cuentaActual.transferir(destino, monto);
                            System.out.println("  ✓ Transferencia exitosa.");
                            System.out.println("  Saldo actual: " +
                                Formateador.formatearMonto(cuentaActual.getSaldo()));
                        }
                    }

                    case 4 -> {
                        double saldo = cuentaActual.consultarSaldo();
                        System.out.println("  Saldo disponible: " + Formateador.formatearMonto(saldo));
                    }

                    case 5 -> {
                        cuentaActual.mostrarHistorial();
                    }

                    case 6 -> {
                        servicio.listarCuentas();
                    }

                    case 0 -> {
                        System.out.println("\n  Gracias por usar el cajero. ¡Hasta pronto!");
                    }

                    default -> {
                        System.out.println("  [!] Opción inválida. Ingrese un número del 0 al 6.");
                    }
                }

            // Cada excepción da un mensaje específico al usuario
            } catch (SaldoInsuficienteException e) {
                System.out.println("  [ERROR] Saldo insuficiente: " + e.getMessage());
            } catch (LimiteExtraccionException e) {
                System.out.println("  [ERROR] Límite excedido: " + e.getMessage());
            } catch (CuentaInactivaException e) {
                System.out.println("  [ERROR] Cuenta inactiva: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("  [ERROR] Operación fallida: " + e.getMessage());
            }

        } while (opcion != 0);

        sc.close();
    }

    
    private int leerEntero() {
        try {
            int valor = sc.nextInt();
            return valor;
        } catch (InputMismatchException e) {
            System.out.println("  [!] Ingrese un número válido.");
            sc.nextLine(); // limpia el buffer del scanner
            return -1;
        }
    }

   
    private double leerDouble() {
        try {
            double valor = sc.nextDouble();
            return valor;
        } catch (InputMismatchException e) {
            System.out.println("  [!] Ingrese un monto válido.");
            sc.nextLine(); // limpia el buffer
            return -1;
        }
    }
}
