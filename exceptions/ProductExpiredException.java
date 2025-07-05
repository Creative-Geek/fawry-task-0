package exceptions;

import java.time.LocalDate;

/**
 * Exception thrown when attempting to purchase an expired product.
 */
public class ProductExpiredException extends ECommerceException {
    private final String productName;
    private final LocalDate expirationDate;
    
    public ProductExpiredException(String productName, LocalDate expirationDate) {
        super(String.format("Product %s has expired on %s", productName, expirationDate), 
              "PRODUCT_EXPIRED");
        this.productName = productName;
        this.expirationDate = expirationDate;
    }
    
    public String getProductName() {
        return productName;
    }
    
    public LocalDate getExpirationDate() {
        return expirationDate;
    }
}
