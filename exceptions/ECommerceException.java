package exceptions;

/**
 * Base exception class for all e-commerce related exceptions.
 * Provides a foundation for specific business logic exceptions.
 */
public class ECommerceException extends Exception {
    private final String errorCode;
    
    public ECommerceException(String message) {
        super(message);
        this.errorCode = "GENERAL_ERROR";
    }
    
    public ECommerceException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
    
    public ECommerceException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = "GENERAL_ERROR";
    }
    
    public ECommerceException(String message, String errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }
    
    public String getErrorCode() {
        return errorCode;
    }
    
    @Override
    public String toString() {
        return String.format("[%s] %s", errorCode, getMessage());
    }
}
