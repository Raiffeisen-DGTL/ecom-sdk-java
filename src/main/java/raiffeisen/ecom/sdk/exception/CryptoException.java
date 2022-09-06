package raiffeisen.ecom.sdk.exception;

public class CryptoException extends Exception {
    public CryptoException(String message, Object... objects) {
        super(String.format(message, objects));
    }
}
