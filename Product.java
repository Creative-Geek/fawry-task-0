
public class Product {
    protected String name;
    protected double price;
    protected int quantity;
    protected double weight;
    public Product(String name, double price, int quantity, double weight) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.weight = weight;
    }
    // constructor for non-shippable items (have 0 weight)
    public Product(String name, double price, int quantity) {
        this(name, price, quantity, 0.0);
    }
    public String getName() {
        return name;
    }
    public double getPrice() {
        return price;
    }
    public int getQuantity() {
        return quantity;
    }
    // Override this method to check if the product is expired. default products don't expire.
    public boolean isExpired() {
        return false;
    }
    public void decreaseQuantity(int amount) {
        if (amount <= this.quantity) {
            this.quantity -= amount;
        }
    }
}