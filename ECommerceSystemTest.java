import java.time.LocalDate;
import exceptions.*;
import utils.ConsoleFormatter;

/**
 * Comprehensive test suite for the E-commerce System.
 * Tests all major functionality, edge cases, and error scenarios.
 */
public class ECommerceSystemTest {
    
    public static void main(String[] args) {
        System.out.println(ConsoleFormatter.BOLD + ConsoleFormatter.BLUE + 
            "E-COMMERCE SYSTEM COMPREHENSIVE TEST SUITE" + ConsoleFormatter.RESET);
        System.out.println(ConsoleFormatter.createSeparator('═', 60));
        
        runAllTests();
        
        System.out.println("\n" + ConsoleFormatter.createSeparator('═', 60));
        System.out.println(ConsoleFormatter.success("All tests completed successfully!"));
    }
    
    private static void runAllTests() {
        testBasicFunctionality();
        testErrorHandling();
        testEdgeCases();
        testShippingCalculations();
        testProductExpiration();
        testCartOperations();
    }
    
    private static void testBasicFunctionality() {
        System.out.println("\n" + ConsoleFormatter.createHeader("BASIC FUNCTIONALITY TESTS", 50));
        System.out.println(ConsoleFormatter.createSeparator('-', 50));
        
        // Create test products
        Product cheese = new ShippableExpirableProduct("Cheese", 100, 10, 0.2, LocalDate.now().plusDays(30));
        Product tv = new ShippableProduct("TV", 5000, 5, 15.0);
        // Note: scratchCard could be used for additional testing if needed
        // Product scratchCard = new Product("Mobile scratch card", 50, 20);
        
        // Create customer
        Customer customer = new Customer("Test Customer", 1000.0);
        
        // Test cart operations
        Cart cart = new Cart();
        try {
            cart.add(cheese, 2);
            cart.add(tv, 1);
            cart.displayContents();
            
            System.out.println(ConsoleFormatter.info("Testing checkout..."));
            CheckoutService.checkout(customer, cart);
            
        } catch (Exception e) {
            System.out.println(ConsoleFormatter.error("Test failed: " + e.getMessage()));
        }
    }
    
    private static void testErrorHandling() {
        System.out.println("\n" + ConsoleFormatter.createHeader("ERROR HANDLING TESTS", 50));
        System.out.println(ConsoleFormatter.createSeparator('-', 50));
        
        Product tv = new ShippableProduct("TV", 5000, 5, 15.0);
        Customer poorCustomer = new Customer("Poor Customer", 100.0);
        Cart cart = new Cart();
        
        // Test 1: Insufficient stock
        System.out.println(ConsoleFormatter.info("Test 1: Insufficient Stock"));
        try {
            cart.add(tv, 10); // Only 5 available
            System.out.println(ConsoleFormatter.error("Test failed - should have thrown exception"));
        } catch (InsufficientStockException e) {
            System.out.println(ConsoleFormatter.success("Correctly caught: " + e.getMessage()));
        }
        
        // Test 2: Insufficient balance
        System.out.println(ConsoleFormatter.info("Test 2: Insufficient Balance"));
        cart.clear();
        try {
            cart.add(tv, 1);
            CheckoutService.checkout(poorCustomer, cart);
        } catch (Exception e) {
            System.out.println(ConsoleFormatter.success("Correctly handled insufficient balance"));
        }
        
        // Test 3: Empty cart
        System.out.println(ConsoleFormatter.info("Test 3: Empty Cart"));
        Cart emptyCart = new Cart();
        CheckoutService.checkout(poorCustomer, emptyCart);
    }
    
    private static void testEdgeCases() {
        System.out.println("\n" + ConsoleFormatter.createHeader("EDGE CASE TESTS", 50));
        System.out.println(ConsoleFormatter.createSeparator('-', 50));
        
        // Test with zero quantity
        System.out.println(ConsoleFormatter.info("Test: Invalid quantity"));
        Product product = new Product("Test Product", 10.0, 5);
        Cart cart = new Cart();
        try {
            cart.add(product, 0);
            System.out.println(ConsoleFormatter.error("Should have thrown exception for zero quantity"));
        } catch (IllegalArgumentException e) {
            System.out.println(ConsoleFormatter.success("Correctly rejected zero quantity"));
        } catch (Exception e) {
            System.out.println(ConsoleFormatter.warning("Unexpected exception: " + e.getMessage()));
        }
        
        // Test with null product
        System.out.println(ConsoleFormatter.info("Test: Null product"));
        try {
            cart.add(null, 1);
            System.out.println(ConsoleFormatter.error("Should have thrown exception for null product"));
        } catch (IllegalArgumentException e) {
            System.out.println(ConsoleFormatter.success("Correctly rejected null product"));
        } catch (Exception e) {
            System.out.println(ConsoleFormatter.warning("Unexpected exception: " + e.getMessage()));
        }
    }
    
    private static void testShippingCalculations() {
        System.out.println("\n" + ConsoleFormatter.createHeader("SHIPPING CALCULATION TESTS", 50));
        System.out.println(ConsoleFormatter.createSeparator('-', 50));
        
        // Test free shipping threshold
        System.out.println(ConsoleFormatter.info("Test: Free shipping for large orders"));
        Product expensiveProduct = new ShippableProduct("Expensive Item", 600, 10, 2.0);
        Customer richCustomer = new Customer("Rich Customer", 10000.0);
        Cart cart = new Cart();
        
        try {
            cart.add(expensiveProduct, 1);
            CheckoutService.checkout(richCustomer, cart);
        } catch (Exception e) {
            System.out.println(ConsoleFormatter.error("Free shipping test failed: " + e.getMessage()));
        }
        
        // Test weight-based shipping
        System.out.println(ConsoleFormatter.info("Test: Weight-based shipping calculation"));
        Product heavyProduct = new ShippableProduct("Heavy Item", 100, 10, 5.0);
        Customer customer = new Customer("Customer", 1000.0);
        Cart heavyCart = new Cart();
        
        try {
            heavyCart.add(heavyProduct, 2);
            CheckoutService.checkout(customer, heavyCart);
        } catch (Exception e) {
            System.out.println(ConsoleFormatter.error("Weight-based shipping test failed: " + e.getMessage()));
        }
    }
    
    private static void testProductExpiration() {
        System.out.println("\n" + ConsoleFormatter.createHeader("PRODUCT EXPIRATION TESTS", 50));
        System.out.println(ConsoleFormatter.createSeparator('-', 50));
        
        // Test expired product
        System.out.println(ConsoleFormatter.info("Test: Expired product checkout"));
        Product expiredProduct = new ShippableExpirableProduct("Expired Milk", 50, 10, 1.0, LocalDate.now().minusDays(1));
        Customer customer = new Customer("Customer", 1000.0);
        Cart cart = new Cart();
        
        try {
            cart.add(expiredProduct, 1);
            CheckoutService.checkout(customer, cart);
        } catch (Exception e) {
            System.out.println(ConsoleFormatter.success("Correctly handled expired product"));
        }
        
        // Test product about to expire
        System.out.println(ConsoleFormatter.info("Test: Product expiring soon"));
        Product soonToExpire = new ShippableExpirableProduct("Fresh Milk", 50, 10, 1.0, LocalDate.now().plusDays(1));
        Cart freshCart = new Cart();
        
        try {
            freshCart.add(soonToExpire, 1);
            CheckoutService.checkout(customer, freshCart);
        } catch (Exception e) {
            System.out.println(ConsoleFormatter.error("Fresh product test failed: " + e.getMessage()));
        }
    }
    
    private static void testCartOperations() {
        System.out.println("\n" + ConsoleFormatter.createHeader("CART OPERATION TESTS", 50));
        System.out.println(ConsoleFormatter.createSeparator('-', 50));
        
        Product product1 = new Product("Product 1", 10.0, 20);
        Product product2 = new Product("Product 2", 15.0, 15);
        Cart cart = new Cart();
        
        try {
            // Test adding items
            System.out.println(ConsoleFormatter.info("Test: Adding items to cart"));
            cart.add(product1, 3);
            cart.add(product2, 2);
            cart.displayContents();
            
            // Test removing items
            System.out.println(ConsoleFormatter.info("Test: Removing items from cart"));
            cart.remove(product1, 1);
            cart.displayContents();
            
            // Test clearing cart
            System.out.println(ConsoleFormatter.info("Test: Clearing cart"));
            cart.clear();
            cart.displayContents();
            
        } catch (Exception e) {
            System.out.println(ConsoleFormatter.error("Cart operations test failed: " + e.getMessage()));
        }
    }
}
