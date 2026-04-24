package exception;

// Esta excepcion se da cuando se intenta operar sobre una cuenta que esta desactivada
public class CuentaInactivaException extends Exception {

    public CuentaInactivaException(String mensaje) {
        super(mensaje);
    }
}
