package model;

import exception.CuentaInactivaException;
import exception.LimiteExtraccionException;
import exception.SaldoInsuficienteException;

import java.util.ArrayList;


public class CuentaBancaria {

    // final = inmutable, el número de cuenta nunca cambia (lo pide el TP)
    private final String numeroCuenta;
    private double saldo;
    private String titular;
    private boolean activa;

    // Lista de transacciones (objetos Transaccion, no solo Strings)
    private ArrayList<Transaccion> historialTransacciones;

    // Límite máximo por extracción (constante del negocio)
    private static final double LIMITE_EXTRACCION = 10000.0;

    // Constructor
    public CuentaBancaria(String numeroCuenta, String titular, double saldoInicial) {
        this.numeroCuenta = numeroCuenta;
        this.titular = titular;
        this.saldo = saldoInicial;
        this.activa = true; // por defecto la cuenta empieza activa
        this.historialTransacciones = new ArrayList<>();
    }

   
    public void depositar(double monto) throws CuentaInactivaException {
        if (!activa) {
            throw new CuentaInactivaException("La cuenta " + numeroCuenta + " está inactiva.");
        }

        if (monto <= 0) {
            System.out.println("  [!] El monto debe ser mayor a cero.");
            return;
        }

        saldo += monto;

        // Creamos un objeto Transaccion y lo guardamos en el historial
        String desc = "Depósito. Saldo resultante: $" + String.format("%,.2f", saldo);
        registrarTransaccion(TipoTransaccion.DEPOSITO, monto, desc);
    }

   
    public void extraer(double monto) throws CuentaInactivaException, LimiteExtraccionException, SaldoInsuficienteException {
        if (!activa) {
            throw new CuentaInactivaException("La cuenta " + numeroCuenta + " está inactiva.");
        }

        if (monto > LIMITE_EXTRACCION) {
            throw new LimiteExtraccionException("El monto $" + String.format("%,.2f", monto) +
                " supera el límite de extracción de $" + String.format("%,.2f", LIMITE_EXTRACCION));
        }

        if (saldo < monto) {
            throw new SaldoInsuficienteException("Saldo insuficiente. Tiene $" +
                String.format("%,.2f", saldo) + " y quiere extraer $" + String.format("%,.2f", monto));
        }

        saldo -= monto;

        String desc = "Extracción. Saldo resultante: $" + String.format("%,.2f", saldo);
        registrarTransaccion(TipoTransaccion.EXTRACCION, monto, desc);
    }

    
    public void transferir(CuentaBancaria destino, double monto) throws Exception {
        if (!activa) {
            throw new CuentaInactivaException("La cuenta origen " + numeroCuenta + " está inactiva.");
        }

        // Primero extraemos de esta cuenta (puede lanzar excepciones)
        this.extraer(monto);

        // Si llegamos acá, la extracción fue exitosa → depositamos en destino
        destino.depositar(monto);

        // Registramos la transferencia en el historial (además del registro individual)
        String desc = "Transferencia a cuenta " + destino.getNumeroCuenta() +
                      ". Saldo resultante: $" + String.format("%,.2f", saldo);
        registrarTransaccion(TipoTransaccion.TRANSFERENCIA, monto, desc);
    }

    public double consultarSaldo() {
        String desc = "Consulta de saldo. Saldo actual: $" + String.format("%,.2f", saldo);
        registrarTransaccion(TipoTransaccion.CONSULTA, 0, desc);
        return saldo;
    }


     
    private void registrarTransaccion(TipoTransaccion tipo, double monto, String descripcion) {
        Transaccion t = new Transaccion(tipo, monto, descripcion);
        historialTransacciones.add(t);
    }

    
    public void mostrarHistorial() {
        System.out.println("\n  === HISTORIAL - Cuenta " + numeroCuenta + " (" + titular + ") ===");

        if (historialTransacciones.isEmpty()) {
            System.out.println("  Sin movimientos.");
            return;
        }

        int inicio = Math.max(0, historialTransacciones.size() - 10);

        for (int i = inicio; i < historialTransacciones.size(); i++) {
            System.out.println("  " + historialTransacciones.get(i).toString());
        }
    }

    // ==========================================
    // ACTIVAR / DESACTIVAR CUENTA
    // ==========================================

    public void desactivar() {
        this.activa = false;
        System.out.println("  Cuenta " + numeroCuenta + " desactivada.");
    }

    public void activar() {
        this.activa = true;
        System.out.println("  Cuenta " + numeroCuenta + " activada.");
    }

    // ==========================================
    // GETTERS
    // ==========================================

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public String getTitular() {
        return titular;
    }

    public double getSaldo() {
        return saldo;
    }

    public boolean isActiva() {
        return activa;
    }
}
