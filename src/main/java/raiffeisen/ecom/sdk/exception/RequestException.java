package raiffeisen.ecom.sdk.exception;

public class RequestException extends Exception {
    public RequestException(String message, Object... objects) {
        super(String.format(message, objects));
    }
}
