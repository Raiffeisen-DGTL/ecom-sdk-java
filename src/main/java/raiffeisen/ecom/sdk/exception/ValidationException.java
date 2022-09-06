package raiffeisen.ecom.sdk.exception;

public class ValidationException extends Exception {
    public ValidationException(String s) {
        super(s);
    }

    public ValidationException(String message, Object... objects) {
        super(String.format(message, objects));
    }
}
