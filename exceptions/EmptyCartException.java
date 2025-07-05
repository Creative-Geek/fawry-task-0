package exceptions;

/**
 * Exception thrown when attempting to checkout with an empty cart.
 */
public class EmptyCartException extends ECommerceException {
    public EmptyCartException() {
        super("Cannot checkout with an empty cart", "EMPTY_CART");
    }
}
