import java.time.LocalDate;
// This product has two roles: it's expirable and shippable.
public class ShippableExpirableProduct extends ExpirableProduct implements Shippable {
    public ShippableExpirableProduct(String name, double price, int quantity, double weight, LocalDate expiryDate) {
        super(name, price, quantity, weight, expiryDate);
    }
    // We must implement the method from the Shippable interface
    @Override
    public double getWeight() {
        return this.weight;
    }
}