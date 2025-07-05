// A shippable product that is not expirable, like a TV.
// It "is-a" Product and it "can-do" shipping.
public class ShippableProduct extends Product implements Shippable {
    public ShippableProduct(String name, double price, int quantity, double weight) {
        super(name, price, quantity, weight);
    }
    // We must implement the methods from the Shippable interface
    @Override
    public double getWeight() {
        return this.weight;
    }
}