package exception;

// Esta excepción se lanza cuando el usuario quiere sacar más plata de la que tiene
public class SaldoInsuficienteException extends Exception {

    public SaldoInsuficienteException(String mensaje) {
        super(mensaje);
    }
}
