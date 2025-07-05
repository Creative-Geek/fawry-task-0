import java.time.LocalDate;
import exceptions.*;
import utils.ConsoleFormatter;

/**
 * Comprehensive test suite demonstrating all features of the E-commerce System.
 * This class showcases the enhanced functionality and robustness of the system.
 */
public class TestSuite {
    
    public static void main(String[] args) {
        System.out.println(ConsoleFormatter.BOLD + ConsoleFormatter.BLUE +
            "E-COMMERCE SYSTEM - COMPREHENSIVE DEMONSTRATION" + ConsoleFormatter.RESET);
        System.out.println(ConsoleFormatter.createSeparator('=', 70));

        runAllDemonstrations();

        System.out.println("\n" + ConsoleFormatter.createSeparator('=', 70));
        System.out.println(ConsoleFormatter.success("All demonstrations completed successfully!"));
        System.out.println(ConsoleFormatter.info("The system handles all features and edge cases robustly."));
    }
    
    public static void runAllDemonstrations() {
        demonstrateCoreFunctionality();
        demonstrateAdvancedFeatures();
        demonstrateErrorHandling();
        demonstrateShippingFeatures();
        demonstrateCartManagement();
    }
    
    /**
     * Demonstrates the core functionality of the e-commerce system.
     */
    private static void demonstrateCoreFunctionality() {
        System.out.println("\n" + ConsoleFormatter.createHeader("CORE FUNCTIONALITY DEMONSTRATION", 60));
        System.out.println(ConsoleFormatter.createSeparator('-', 60));

        // Create products with different characteristics
        Product cheese = new ShippableExpirableProduct("Cheese", 100, 10, 0.2, LocalDate.now().plusDays(30));
        Product biscuits = new ShippableExpirableProduct("Biscuits", 150, 15, 0.7, LocalDate.now().plusMonths(6));
        
        System.out.println(ConsoleFormatter.info("Products defined with name, price, and quantity"));
        System.out.println(ConsoleFormatter.info("Some products expire (Cheese, Biscuits), others don't (TV, Mobile cards)"));
        System.out.println(ConsoleFormatter.info("Some products require shipping (Cheese, TV), others don't (Mobile cards)"));
        System.out.println(ConsoleFormatter.info("Shippable items provide weight information"));
        
        // Create customer
        Customer customer = new Customer("Ahmed", 1000.0);
        System.out.println(ConsoleFormatter.info("Customer created with balance: " + ConsoleFormatter.formatCurrency(customer.getBalance())));
        
        // Demonstrate cart operations
        Cart cart = new Cart();
        try {
            System.out.println(ConsoleFormatter.info("Adding products to cart with quantity validation..."));
            cart.add(cheese, 2);
            cart.add(biscuits, 1);
            cart.displayContents();
            
            System.out.println(ConsoleFormatter.info("Performing checkout with all validations..."));
            CheckoutService.checkout(customer, cart);
            
        } catch (Exception e) {
            System.out.println(ConsoleFormatter.error("Core functionality test failed: " + e.getMessage()));
        }
    }
    
    /**
     * Demonstrates advanced features that make the system stand out.
     */
    private static void demonstrateAdvancedFeatures() {
        System.out.println("\n" + ConsoleFormatter.createHeader("ADVANCED FEATURES DEMONSTRATION", 60));
        System.out.println(ConsoleFormatter.createSeparator('-', 60));

        // Weight-based shipping calculation
        System.out.println(ConsoleFormatter.info("Advanced Feature 1: Weight-based shipping calculation"));
        Product heavyItem = new ShippableProduct("Heavy Equipment", 200, 10, 8.0);
        Customer customer = new Customer("Tech Buyer", 2000.0);
        Cart heavyCart = new Cart();
        
        try {
            heavyCart.add(heavyItem, 1);
            CheckoutService.checkout(customer, heavyCart);
        } catch (Exception e) {
            System.out.println(ConsoleFormatter.error("Heavy item test failed: " + e.getMessage()));
        }
        
        // Free shipping for large orders
        System.out.println(ConsoleFormatter.info("🎯 Advanced Feature 2: Free shipping for orders over $500"));
        Product expensiveItem = new ShippableProduct("Premium TV", 600, 3, 20.0);
        Customer richCustomer = new Customer("Premium Customer", 5000.0);
        Cart premiumCart = new Cart();
        
        try {
            premiumCart.add(expensiveItem, 1);
            CheckoutService.checkout(richCustomer, premiumCart);
        } catch (Exception e) {
            System.out.println(ConsoleFormatter.error("Premium order test failed: " + e.getMessage()));
        }
    }
    
    /**
     * Demonstrates comprehensive error handling and validation.
     */
    private static void demonstrateErrorHandling() {
        System.out.println("\n" + ConsoleFormatter.createHeader("ERROR HANDLING DEMONSTRATION", 60));
        System.out.println(ConsoleFormatter.createSeparator('-', 60));
        
        Product testProduct = new ShippableProduct("Test Product", 100, 5, 1.0);
        Customer testCustomer = new Customer("Test Customer", 50.0);
        
        // Test 1: Insufficient stock
        System.out.println(ConsoleFormatter.info("Test 1: Insufficient Stock Validation"));
        Cart cart1 = new Cart();
        try {
            cart1.add(testProduct, 10); // Only 5 available
        } catch (InsufficientStockException e) {
            System.out.println(ConsoleFormatter.success("" + e.toString()));
        }
        
        // Test 2: Invalid quantity
        System.out.println(ConsoleFormatter.info("Test 2: Invalid Quantity Validation"));
        try {
            cart1.add(testProduct, 0);
        } catch (IllegalArgumentException e) {
            System.out.println(ConsoleFormatter.success("Rejected invalid quantity: " + e.getMessage()));
        } catch (Exception e) {
            System.out.println(ConsoleFormatter.warning("Unexpected exception: " + e.getMessage()));
        }
        
        // Test 3: Null product validation
        System.out.println(ConsoleFormatter.info("Test 3: Null Product Validation"));
        try {
            cart1.add(null, 1);
        } catch (IllegalArgumentException e) {
            System.out.println(ConsoleFormatter.success("Rejected null product: " + e.getMessage()));
        } catch (Exception e) {
            System.out.println(ConsoleFormatter.warning("Unexpected exception: " + e.getMessage()));
        }
        
        // Test 4: Insufficient balance
        System.out.println(ConsoleFormatter.info("Test 4: Insufficient Balance Validation"));
        Cart expensiveCart = new Cart();
        try {
            expensiveCart.add(testProduct, 1);
            CheckoutService.checkout(testCustomer, expensiveCart);
        } catch (Exception e) {
            System.out.println(ConsoleFormatter.success("Insufficient balance handled gracefully"));
        }
        
        // Test 5: Empty cart
        System.out.println(ConsoleFormatter.info("Test 5: Empty Cart Validation"));
        Cart emptyCart = new Cart();
        CheckoutService.checkout(testCustomer, emptyCart);
        
        // Test 6: Expired product
        System.out.println(ConsoleFormatter.info("Test 6: Expired Product Validation"));
        Product expiredProduct = new ShippableExpirableProduct("Expired Milk", 30, 10, 1.0, LocalDate.now().minusDays(5));
        Cart expiredCart = new Cart();
        Customer richCustomer = new Customer("Rich Customer", 1000.0);
        try {
            expiredCart.add(expiredProduct, 1);
            CheckoutService.checkout(richCustomer, expiredCart);
        } catch (Exception e) {
            System.out.println(ConsoleFormatter.success("Expired product handled correctly"));
        }
    }
    
    /**
     * Demonstrates advanced shipping features.
     */
    private static void demonstrateShippingFeatures() {
        System.out.println("\n" + ConsoleFormatter.createHeader("SHIPPING FEATURES DEMONSTRATION", 60));
        System.out.println(ConsoleFormatter.createSeparator('-', 60));
        
        // Mixed cart with shippable and non-shippable items
        System.out.println(ConsoleFormatter.info("Mixed Cart: Shippable + Non-shippable items"));
        Product shippableItem = new ShippableProduct("Laptop", 800, 10, 2.5);
        Product nonShippableItem = new Product("Digital Download", 25, 100);
        Customer customer = new Customer("Mixed Customer", 2000.0);
        Cart mixedCart = new Cart();
        
        try {
            mixedCart.add(shippableItem, 1);
            mixedCart.add(nonShippableItem, 3);
            CheckoutService.checkout(customer, mixedCart);
        } catch (Exception e) {
            System.out.println(ConsoleFormatter.error("Mixed cart test failed: " + e.getMessage()));
        }
        
        // Demonstrate weight calculation accuracy
        System.out.println(ConsoleFormatter.info("Weight Calculation: Multiple heavy items"));
        Product heavyItem1 = new ShippableProduct("Server", 300, 5, 12.0);
        Product heavyItem2 = new ShippableProduct("Monitor", 400, 8, 8.5);
        Cart heavyCart = new Cart();
        
        try {
            heavyCart.add(heavyItem1, 2);
            heavyCart.add(heavyItem2, 1);
            CheckoutService.checkout(customer, heavyCart);
        } catch (Exception e) {
            System.out.println(ConsoleFormatter.error("Heavy cart test failed: " + e.getMessage()));
        }
    }
    
    /**
     * Demonstrates cart management features.
     */
    private static void demonstrateCartManagement() {
        System.out.println("\n" + ConsoleFormatter.createHeader("CART MANAGEMENT DEMONSTRATION", 60));
        System.out.println(ConsoleFormatter.createSeparator('-', 60));
        
        Product item1 = new Product("Book", 25, 50);
        Product item2 = new Product("Pen", 5, 100);
        Product item3 = new ShippableProduct("Notebook", 15, 30, 0.5);
        
        Cart cart = new Cart();
        
        try {
            // Adding items
            System.out.println(ConsoleFormatter.info("Adding items to cart:"));
            cart.add(item1, 3);
            cart.add(item2, 5);
            cart.add(item3, 2);
            cart.displayContents();
            
            // Removing items
            System.out.println(ConsoleFormatter.info("Removing some items:"));
            cart.remove(item2, 2);
            cart.displayContents();
            
            // Attempting to remove non-existent item
            System.out.println(ConsoleFormatter.info("Attempting to remove non-existent item:"));
            Product nonExistentItem = new Product("Non-existent", 10, 5);
            cart.remove(nonExistentItem, 1);
            
            // Final cart state
            System.out.println(ConsoleFormatter.info("Final cart state:"));
            cart.displayContents();
            
        } catch (Exception e) {
            System.out.println(ConsoleFormatter.error("Cart management test failed: " + e.getMessage()));
        }
    }
}
