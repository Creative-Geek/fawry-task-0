package exceptions;

/**
 * Exception thrown when attempting to purchase more items than available in stock.
 */
public class InsufficientStockException extends ECommerceException {
    private final String productName;
    private final int requestedQuantity;
    private final int availableQuantity;
    
    public InsufficientStockException(String productName, int requestedQuantity, int availableQuantity) {
        super(String.format("Insufficient stock for %s. Requested: %d, Available: %d", 
              productName, requestedQuantity, availableQuantity), "INSUFFICIENT_STOCK");
        this.productName = productName;
        this.requestedQuantity = requestedQuantity;
        this.availableQuantity = availableQuantity;
    }
    
    public String getProductName() {
        return productName;
    }
    
    public int getRequestedQuantity() {
        return requestedQuantity;
    }
    
    public int getAvailableQuantity() {
        return availableQuantity;
    }
}
