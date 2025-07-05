// The interface for shippable products, any product that implements this can be shipped.
public interface Shippable {
    String getName();
    double getWeight();
}