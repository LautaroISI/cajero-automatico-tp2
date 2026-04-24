package model;

import java.time.LocalDateTime;


public class Transaccion {

    private TipoTransaccion tipo;
    private double monto;
    private LocalDateTime fechaHora;
    private String descripcion;

    // Constructor: recibe todos los datos de la transacción
    public Transaccion(TipoTransaccion tipo, double monto, String descripcion) {
        this.tipo = tipo;
        this.monto = monto;
        this.fechaHora = LocalDateTime.now(); // se registra automáticamente la hora actual
        this.descripcion = descripcion;
    }

    // Getters simples para acceder a los datos
    public TipoTransaccion getTipo() {
        return tipo;
    }

    public double getMonto() {
        return monto;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public String getDescripcion() {
        return descripcion;
    }

        /*
        * Este método convierte la transacción a texto para el historial.
        * Usamos StringBuilder porque el TP lo pide explícitamente.
        * El formato es: [fecha hora] TIPO: $monto | descripcion
        */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[")
          .append(fechaHora)
          .append("] ")
          .append(tipo)
          .append(": $")
          .append(String.format("%,.2f", monto))
          .append(" | ")
          .append(descripcion);
        return sb.toString();
    }
}
