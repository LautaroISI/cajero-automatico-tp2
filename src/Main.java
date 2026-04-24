import exception.CuentaInactivaException;
import exception.LimiteExtraccionException;
import exception.SaldoInsuficienteException;
import model.CuentaBancaria;
import service.CuentaService;
import ui.MenuCajero;
import util.Formateador;


public class Main {

    public static void main(String[] args) {

        // ---- Creamos el servicio que administra todas las cuentas ----
        CuentaService servicio = new CuentaService();

        // Creamos 3 cuentas con saldos iniciales (lo pide el TP)
        CuentaBancaria c1 = new CuentaBancaria("001", "Juan Pérez", 20000.00);
        CuentaBancaria c2 = new CuentaBancaria("002", "Ana García", 15000.00);
        CuentaBancaria c3 = new CuentaBancaria("003", "Luis Torres", 10000.00);

        servicio.agregarCuenta(c1);
        servicio.agregarCuenta(c2);
        servicio.agregarCuenta(c3);

        // ---- Primero corremos la simulación automática del día ----
        simulacionDia(c1, c2, c3);

        // ---- Después iniciamos el menú interactivo ----
        System.out.println("\n\n  ========================================");
        System.out.println("       INICIANDO MODO INTERACTIVO        ");
        System.out.println("  ========================================");
        System.out.println("  Cuentas disponibles: 001, 002, 003");

        MenuCajero menu = new MenuCajero(servicio);
        menu.iniciar();
    }

   
    private static void simulacionDia(CuentaBancaria c1, CuentaBancaria c2, CuentaBancaria c3) {

        System.out.println("  ========================================");
        System.out.println("     SIMULACIÓN AUTOMÁTICA - DÍA DE OPS  ");
        System.out.println("  ========================================\n");

        // ---------- TRANSACCIONES DE JUAN (c1) ----------
        System.out.println(">>> Operaciones de Juan Pérez (Cuenta 001):");

        // Transacción 1: depósito normal
        try {
            c1.depositar(5000);
            System.out.println("  [OK] Depósito $5,000.00");
        } catch (CuentaInactivaException e) {
            System.out.println("  [ERROR] " + e.getMessage());
        }

        // Transacción 2: extracción normal
        try {
            c1.extraer(3000);
            System.out.println("  [OK] Extracción $3,000.00");
        } catch (Exception e) {
            System.out.println("  [ERROR] " + e.getMessage());
        }

        // Transacción 3: extracción que supera el límite (excepción esperada)
        try {
            c1.extraer(15000); // más de $10.000 → LimiteExtraccionException
            System.out.println("  [OK] Extracción $15,000.00");
        } catch (LimiteExtraccionException e) {
            System.out.println("  [EXCEPCIÓN CAPTURADA - LimiteExtraccion] " + e.getMessage());
        } catch (Exception e) {
            System.out.println("  [ERROR] " + e.getMessage());
        }

        // Transacción 4: consulta de saldo
        double saldoJuan = c1.consultarSaldo();
        System.out.println("  [OK] Consulta → Saldo de Juan: " + Formateador.formatearMonto(saldoJuan));

        // Transacción 5: transferencia a Ana
        try {
            c1.transferir(c2, 2000);
            System.out.println("  [OK] Transferencia $2,000.00 → Ana");
        } catch (Exception e) {
            System.out.println("  [ERROR] " + e.getMessage());
        }

        // ---------- TRANSACCIONES DE ANA (c2) ----------
        System.out.println("\n>>> Operaciones de Ana García (Cuenta 002):");

        // Transacción 6: depósito
        try {
            c2.depositar(8000);
            System.out.println("  [OK] Depósito $8,000.00");
        } catch (CuentaInactivaException e) {
            System.out.println("  [ERROR] " + e.getMessage());
        }

        // Transacción 7: extracción válida
        try {
            c2.extraer(5000);
            System.out.println("  [OK] Extracción $5,000.00");
        } catch (Exception e) {
            System.out.println("  [ERROR] " + e.getMessage());
        }

        // Transacción 8: extracción con saldo insuficiente (excepción esperada)
        try {
            c2.extraer(50000); // hay mucho menos que eso → SaldoInsuficienteException
            System.out.println("  [OK] Extracción $50,000.00");
        } catch (SaldoInsuficienteException e) {
            System.out.println("  [EXCEPCIÓN CAPTURADA - SaldoInsuficiente] " + e.getMessage());
        } catch (Exception e) {
            System.out.println("  [ERROR] " + e.getMessage());
        }

        // Transacción 9: consulta
        double saldoAna = c2.consultarSaldo();
        System.out.println("  [OK] Consulta → Saldo de Ana: " + Formateador.formatearMonto(saldoAna));

        // Transacción 10: transferencia a Luis
        try {
            c2.transferir(c3, 1500);
            System.out.println("  [OK] Transferencia $1,500.00 → Luis");
        } catch (Exception e) {
            System.out.println("  [ERROR] " + e.getMessage());
        }

        // ---------- TRANSACCIONES DE LUIS (c3) ----------
        System.out.println("\n>>> Operaciones de Luis Torres (Cuenta 003):");

        // Transacción 11: depósito
        try {
            c3.depositar(3000);
            System.out.println("  [OK] Depósito $3,000.00");
        } catch (CuentaInactivaException e) {
            System.out.println("  [ERROR] " + e.getMessage());
        }

        // Transacción 12: extracción
        try {
            c3.extraer(4000);
            System.out.println("  [OK] Extracción $4,000.00");
        } catch (Exception e) {
            System.out.println("  [ERROR] " + e.getMessage());
        }

        // Transacción 13: desactivamos la cuenta de Luis
        c3.desactivar();

        // Transacción 14: intentamos operar sobre cuenta inactiva (excepción esperada)
        try {
            c3.depositar(1000); // CuentaInactivaException
            System.out.println("  [OK] Depósito $1,000.00");
        } catch (CuentaInactivaException e) {
            System.out.println("  [EXCEPCIÓN CAPTURADA - CuentaInactiva] " + e.getMessage());
        }

        // Transacción 15: reactivamos y hacemos consulta final
        c3.activar();
        double saldoLuis = c3.consultarSaldo();
        System.out.println("  [OK] Cuenta reactivada. Saldo de Luis: " + Formateador.formatearMonto(saldoLuis));

        // ---------- HISTORIAL FINAL ----------
        System.out.println("\n\n  ======== HISTORIAL DE TRANSACCIONES ========");
        c1.mostrarHistorial();
        c2.mostrarHistorial();
        c3.mostrarHistorial();

        System.out.println("\n  ======== SALDOS FINALES DEL DÍA ========");
        System.out.println("  Juan:  " + Formateador.formatearMonto(c1.getSaldo()));
        System.out.println("  Ana:   " + Formateador.formatearMonto(c2.getSaldo()));
        System.out.println("  Luis:  " + Formateador.formatearMonto(c3.getSaldo()));
    }
}
