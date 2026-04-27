package service;

import model.CuentaBancaria;

import java.util.ArrayList;


public class CuentaService {

    // Lista de todas las cuentas registradas en el sistema
    private ArrayList<CuentaBancaria> cuentas;

    public CuentaService() {
        this.cuentas = new ArrayList<>();
    }

    // Agrega una cuenta nueva al sistema
    public void agregarCuenta(CuentaBancaria cuenta) {
        cuentas.add(cuenta);
    }

   
    public CuentaBancaria buscarPorNumero(String numeroCuenta) {
        for (CuentaBancaria c : cuentas) {
            if (c.getNumeroCuenta().equals(numeroCuenta)) {
                return c;
            }
        }
        return null; // no encontrada
    }

    // Muestra todas las cuentas registradas con su saldo y estado
    public void listarCuentas() {
        System.out.println("\n  === CUENTAS REGISTRADAS ===");
        for (CuentaBancaria c : cuentas) {
            String estado = c.isActiva() ? "ACTIVA" : "INACTIVA";
            System.out.println("  [" + c.getNumeroCuenta() + "] " +
                c.getTitular() + " - Saldo: $" +
                String.format("%,.2f", c.getSaldo()) + " - " + estado);
        }
    }

    public ArrayList<CuentaBancaria> getCuentas() {
        return cuentas;
    }
}
