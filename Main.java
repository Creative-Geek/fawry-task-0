import java.time.LocalDate;
import exceptions.*;

/**
 * The main class demonstrating the e-commerce system.
 */
public class Main {
    public static void main(String[] args) {
        // A product that is both shippable and expirable
        Product cheese = new ShippableExpirableProduct("Cheese", 100, 10, 0.2, LocalDate.now().plusDays(30));
        // A simple non-shippable, non-expirable product (probably a digital product)
        Product scratchCard = new Product("Mobile scratch card", 50, 20);
        // A shippable, non-expirable product
        Product tv = new ShippableProduct("TV", 5000, 5, 15.0);
        // Another shippable & expirable product to match the example (Biscuits)
        Product biscuits = new ShippableExpirableProduct("Biscuits", 150, 15, 0.7, LocalDate.now().plusMonths(6));
        // --- Setup Customer ---
        Customer customer = new Customer("Ahmed", 1000.0);
        // --- Demonstrate Enhanced E-commerce System ---
        demonstrateBasicScenario(customer, cheese, biscuits, scratchCard, tv);

        System.out.println("\n" + "=".repeat(60)); // print separator
        System.out.println("ADDITIONAL TEST SCENARIOS");
        System.out.println("=".repeat(60));

        // Reset customer balance for additional tests
        customer = new Customer("Ahmed", 1000.0);

        demonstrateErrorScenarios(customer, cheese, biscuits, scratchCard, tv);
        demonstrateFreeShipping(customer, tv);
    }

    /**
     * Demo (basic scenario).
     */
    private static void demonstrateBasicScenario(Customer customer, Product cheese,
                                               Product biscuits, Product scratchCard, Product tv) {
        System.out.println("BASIC SCENARIO");
        System.out.println("-".repeat(40));

        Cart cart = new Cart();
        try {
            cart.add(cheese, 2);
            cart.add(biscuits, 1);
            // Expected output: Subtotal = 350, Shipping = 26, Total = 376

            System.out.println("\n--- Checking out ---");
            CheckoutService.checkout(customer, cart);

        } catch (InsufficientStockException e) {
            System.err.println("Stock Error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected Error: " + e.getMessage());
        }
    }

    /**
     * Demonstrates various error scenarios and edge cases.
     */
    private static void demonstrateErrorScenarios(Customer customer, Product cheese,
                                                Product biscuits, Product scratchCard, Product tv) {
        System.out.println("\nERROR HANDLING DEMONSTRATIONS");
        System.out.println("-".repeat(40));

        // Test 1: Insufficient Stock
        System.out.println("\n1. Testing Insufficient Stock:");
        Cart cart1 = new Cart();
        try {
            cart1.add(tv, 10); // Only 5 available
        } catch (InsufficientStockException e) {
            System.err.println("Caught expected error: " + e.getMessage());
        }

        // Test 2: Empty Cart Checkout
        System.out.println("\n2. Testing Empty Cart Checkout:");
        Cart emptyCart = new Cart();
        CheckoutService.checkout(customer, emptyCart);

        // Test 3: Insufficient Balance
        System.out.println("\n3. Testing Insufficient Balance:");
        Customer poorCustomer = new Customer("Poor Customer", 50.0);
        Cart expensiveCart = new Cart();
        try {
            expensiveCart.add(tv, 1); // TV costs 5000
            CheckoutService.checkout(poorCustomer, expensiveCart);
        } catch (InsufficientStockException e) {
            System.err.println("Stock Error: " + e.getMessage());
        }
    }

    /**
     * Demonstrates free shipping scenario.
     */
    private static void demonstrateFreeShipping(Customer customer, Product tv) {
        System.out.println("\nFREE SHIPPING DEMONSTRATION");
        System.out.println("-".repeat(40));

        Cart cart = new Cart();
        try {
            cart.add(tv, 1); // TV costs 5000 - qualifies for free shipping
            System.out.println("\n--- Checking out (Free Shipping) ---");
            CheckoutService.checkout(customer, cart);
        } catch (InsufficientStockException e) {
            System.err.println("Stock Error: " + e.getMessage());
        }
    }
}