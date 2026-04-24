package exception;

// Esta excepción se da cuando se intenta extraer más de $10.000 
public class LimiteExtraccionException extends Exception {

    public LimiteExtraccionException(String mensaje) {
        super(mensaje);
    }
}