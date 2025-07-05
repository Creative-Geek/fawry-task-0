import java.time.LocalDate;

/**
 * Product that can expire, extending the Product class.
 */
public class ExpirableProduct extends Product {
    private LocalDate expiryDate;

    public ExpirableProduct(String name, double price, int quantity, double weight, LocalDate expiryDate) {
        super(name, price, quantity, weight);
        this.expiryDate = expiryDate;
    }

    /**
     * Override the parent's method to provide specific expiration logic.
     */
    @Override
    public boolean isExpired() {
        return LocalDate.now().isAfter(expiryDate);
    }

    /**
     * Gets the expiration date of this product.
     *
     * @return The expiration date
     */
    public LocalDate getExpirationDate() {
        return expiryDate;
    }

    /**
     * Sets a new expiration date for this product.
     *
     * @param expiryDate New expiration date
     */
    public void setExpirationDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }
}