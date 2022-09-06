package raiffeisen.ecom.sdk.exception;

public class MappingException extends Exception{
    public MappingException(String message, Object... objects) {
        super(String.format(message, objects));
    }
}
