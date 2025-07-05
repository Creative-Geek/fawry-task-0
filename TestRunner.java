import java.time.LocalDate;
import java.util.Map;
import exceptions.InsufficientStockException;

/**
 * Individual test case runner for the E-commerce System.
 * Allows running specific test cases instead of full test suites.
 * Uses only ASCII characters throughout.
 */
public class TestRunner {
    
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: java TestRunner <testCase>");
            System.out.println("Available test cases:");
            System.out.println("  coreFunctionalityDemo");
            System.out.println("  basicErrorScenarios");
            System.out.println("  freeShippingDemo");
            System.out.println("  coreFeatures");
            System.out.println("  advancedFeatures");
            System.out.println("  errorHandling");
            System.out.println("  shippingFeatures");
            System.out.println("  cartManagement");
            System.out.println("  vipScenario");
            System.out.println("  mixedCart");
            System.out.println("  errorShowcase");
            System.out.println("  freeShippingShowcase");
            return;
        }
        
        String testCase = args[0];
        
        switch (testCase) {
            case "coreFunctionalityDemo":
            case "basicRequirementsDemo": // Keep for backward compatibility
                runCoreFunctionalityDemo();
                break;
            case "basicErrorScenarios":
                runBasicErrorScenarios();
                break;
            case "freeShippingDemo":
                runFreeShippingDemo();
                break;
            case "coreFeatures":
            case "basicRequirements": // Keep for backward compatibility
                runCoreFeatures();
                break;
            case "advancedFeatures":
                runAdvancedFeatures();
                break;
            case "errorHandling":
                runErrorHandling();
                break;
            case "shippingFeatures":
                runShippingFeatures();
                break;
            case "cartManagement":
                runCartManagement();
                break;
            case "vipScenario":
                runVIPScenario();
                break;
            case "mixedCart":
                runMixedCart();
                break;
            case "errorShowcase":
                runErrorShowcase();
                break;
            case "freeShippingShowcase":
                runFreeShippingShowcase();
                break;
            default:
                System.out.println("Unknown test case: " + testCase);
                break;
        }
    }
    
    // Basic functionality tests from Main.java
    private static void runCoreFunctionalityDemo() {
        System.out.println("CORE FUNCTIONALITY DEMO");
        System.out.println("=====================================");
        System.out.println("Testing core functionality with cheese + biscuits example");
        System.out.println();
        
        // Create products
        Product cheese = new ShippableExpirableProduct("Cheese", 100, 10, 0.2, LocalDate.now().plusDays(30));
        Product biscuits = new ShippableExpirableProduct("Biscuits", 150, 15, 0.7, LocalDate.now().plusMonths(6));
        Customer customer = new Customer("Ahmed", 1000.0);
        
        Cart cart = new Cart();
        try {
            cart.add(cheese, 2);    // 2 * 100 = 200
            cart.add(biscuits, 1);  // 1 * 150 = 150
            // Expected: Subtotal = 350, Shipping = 26, Total = 376
            
            System.out.println("--- Checking out ---");
            CheckoutService.checkout(customer, cart);
            
        } catch (InsufficientStockException e) {
            System.err.println("Stock Error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected Error: " + e.getMessage());
        }
    }
    
    private static void runBasicErrorScenarios() {
        System.out.println("BASIC ERROR SCENARIOS");
        System.out.println("====================");
        System.out.println("Testing error handling and edge cases");
        System.out.println();
        
        // Create products and customer
        Product tv = new ShippableProduct("TV", 5000, 5, 15.0);
        Customer customer = new Customer("Ahmed", 1000.0);
        
        // Test 1: Insufficient Stock
        System.out.println("1. Testing Insufficient Stock:");
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
    
    private static void runFreeShippingDemo() {
        System.out.println("FREE SHIPPING DEMO");
        System.out.println("==================");
        System.out.println("Testing free shipping for orders over $1000");
        System.out.println();
        
        Product tv = new ShippableProduct("TV", 5000, 5, 15.0);
        Customer customer = new Customer("Ahmed", 6000.0);
        
        Cart cart = new Cart();
        try {
            cart.add(tv, 1); // TV costs 5000 - qualifies for free shipping
            System.out.println("--- Checking out (Free Shipping) ---");
            CheckoutService.checkout(customer, cart);
        } catch (InsufficientStockException e) {
            System.err.println("Stock Error: " + e.getMessage());
        }
    }
    
    // Comprehensive tests from TestSuite.java
    private static void runCoreFeatures() {
        System.out.println("CORE FEATURES VALIDATION");
        System.out.println("=============================");
        System.out.println("Comprehensive validation of all core features");
        System.out.println();
        
        // Create products with different characteristics
        Product cheese = new ShippableExpirableProduct("Cheese", 100, 10, 0.2, LocalDate.now().plusDays(30));
        Product biscuits = new ShippableExpirableProduct("Biscuits", 150, 15, 0.7, LocalDate.now().plusMonths(6));

        System.out.println("Products defined with name, price, and quantity");
        System.out.println("Products have expiration dates and shipping characteristics");
        System.out.println("Shippable items provide weight information for shipping calculations");
        
        // Create customer
        Customer customer = new Customer("Ahmed", 1000.0);
        
        // Test basic cart operations
        Cart cart = new Cart();
        try {
            cart.add(cheese, 2);
            cart.add(biscuits, 1);
            
            System.out.println("\nCart contents:");
            cart.displayContents();
            
            System.out.println("\nProcessing checkout...");
            CheckoutService.checkout(customer, cart);
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
    
    private static void runAdvancedFeatures() {
        System.out.println("ADVANCED FEATURES DEMO");
        System.out.println("======================");
        System.out.println("Testing discounts, taxes, and advanced calculations");
        System.out.println();
        
        // Create products with different tax rates
        Product laptop = new ShippableProduct("Gaming Laptop", 1200, 8, 2.5);
        Product ebook = new Product("Programming E-book", 30, 50);
        
        // Create VIP customer for discount testing
        Customer vipCustomer = new Customer("VIP Customer", 5000.0);
        
        Cart cart = new Cart();
        try {
            cart.add(laptop, 3);  // High value for bulk discount
            cart.add(ebook, 5);   // Digital product with different tax
            
            System.out.println("Cart with mixed products (physical + digital):");
            cart.displayContents();
            
            System.out.println("\nProcessing checkout with advanced features...");
            CheckoutService.checkout(vipCustomer, cart);
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
    
    private static void runErrorHandling() {
        System.out.println("ERROR HANDLING TESTS");
        System.out.println("===================");
        System.out.println("Comprehensive error handling validation");
        System.out.println();
        
        Product testProduct = new ShippableProduct("Test Product", 100, 5, 1.0);
        Customer testCustomer = new Customer("Test Customer", 50.0);
        
        // Test 1: Insufficient stock
        System.out.println("Test 1: Insufficient Stock Validation");
        Cart cart1 = new Cart();
        try {
            cart1.add(testProduct, 10); // Only 5 available
        } catch (InsufficientStockException e) {
            System.out.println("SUCCESS: " + e.toString());
        }
        
        // Test 2: Invalid quantity
        System.out.println("\nTest 2: Invalid Quantity Validation");
        Cart cart2 = new Cart();
        try {
            cart2.add(testProduct, 0);
        } catch (IllegalArgumentException e) {
            System.out.println("SUCCESS: " + e.getMessage());
        } catch (InsufficientStockException e) {
            System.out.println("Unexpected stock exception: " + e.getMessage());
        }
        
        // Test 3: Expired product
        System.out.println("\nTest 3: Expired Product Validation");
        Product expiredProduct = new ShippableExpirableProduct("Expired Milk", 40, 5, 1.0, LocalDate.now().minusDays(3));
        Cart cart3 = new Cart();
        try {
            cart3.add(expiredProduct, 1);
            CheckoutService.checkout(testCustomer, cart3);
        } catch (Exception e) {
            System.out.println("SUCCESS: Correctly handled expired product");
        }
        
        // Test 4: Empty cart
        System.out.println("\nTest 4: Empty Cart Validation");
        Cart emptyCart = new Cart();
        CheckoutService.checkout(testCustomer, emptyCart);
        
        // Test 5: Insufficient balance
        System.out.println("\nTest 5: Insufficient Balance Validation");
        Product expensiveProduct = new Product("Expensive Item", 1000, 10);
        Cart expensiveCart = new Cart();
        try {
            expensiveCart.add(expensiveProduct, 1);
            CheckoutService.checkout(testCustomer, expensiveCart);
        } catch (Exception e) {
            System.out.println("SUCCESS: " + e.getMessage());
        }
    }

    private static void runShippingFeatures() {
        System.out.println("SHIPPING FEATURES TESTS");
        System.out.println("=======================");
        System.out.println("Testing shipping calculations and features");
        System.out.println();

        // Mixed cart with shippable and non-shippable items
        System.out.println("Mixed Cart: Shippable + Non-shippable items");
        Product shippableItem = new ShippableProduct("Laptop", 800, 10, 2.5);
        Product nonShippableItem = new Product("Digital Download", 25, 100);
        Customer customer = new Customer("Mixed Customer", 2000.0);
        Cart mixedCart = new Cart();

        try {
            mixedCart.add(shippableItem, 1);
            mixedCart.add(nonShippableItem, 3);

            System.out.println("Cart contents:");
            mixedCart.displayContents();

            System.out.println("\nProcessing checkout...");
            CheckoutService.checkout(customer, mixedCart);

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

        // Test weight-based shipping
        System.out.println("\n" + "=".repeat(50));
        System.out.println("Weight-based Shipping Test");
        Product heavyItem = new ShippableProduct("Heavy Equipment", 500, 5, 10.0);
        Product lightItem = new ShippableProduct("Light Accessory", 50, 20, 0.1);

        Cart heavyCart = new Cart();
        Cart lightCart = new Cart();

        try {
            heavyCart.add(heavyItem, 1);
            lightCart.add(lightItem, 1);

            System.out.println("\nHeavy item checkout:");
            CheckoutService.checkout(new Customer("Heavy Customer", 1000.0), heavyCart);

            System.out.println("\nLight item checkout:");
            CheckoutService.checkout(new Customer("Light Customer", 1000.0), lightCart);

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static void runCartManagement() {
        System.out.println("CART MANAGEMENT TESTS");
        System.out.println("====================");
        System.out.println("Testing cart operations and management");
        System.out.println();

        Product item1 = new Product("Book", 25, 50);
        Product item2 = new Product("Pen", 5, 100);
        Product item3 = new ShippableProduct("Notebook", 15, 30, 0.5);

        Cart cart = new Cart();

        // Test adding items
        System.out.println("1. Adding items to cart:");
        try {
            cart.add(item1, 2);
            cart.add(item2, 5);
            cart.add(item3, 3);

            System.out.println("Cart after adding items:");
            cart.displayContents();

        } catch (Exception e) {
            System.err.println("Error adding items: " + e.getMessage());
        }

        // Test cart calculations
        System.out.println("\n2. Cart calculations:");
        int totalItems = 0;
        double subtotal = 0;
        double totalWeight = 0;

        for (Map.Entry<Product, Integer> entry : cart.getItems().entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            totalItems += quantity;
            subtotal += product.getPrice() * quantity;

            if (product instanceof Shippable) {
                totalWeight += ((Shippable) product).getWeight() * quantity;
            }
        }

        System.out.println("Total items: " + totalItems);
        System.out.println("Subtotal: $" + String.format("%.2f", subtotal));
        System.out.println("Total weight: " + String.format("%.2f", totalWeight) + " kg");

        // Test checkout
        System.out.println("\n3. Processing checkout:");
        Customer customer = new Customer("Cart Test Customer", 500.0);
        CheckoutService.checkout(customer, cart);
    }

    // Showcase scenarios from FinalDemo.java
    private static void runVIPScenario() {
        System.out.println("VIP CUSTOMER SCENARIO");
        System.out.println("====================");
        System.out.println("Testing VIP customer with multiple discounts");
        System.out.println();

        Product laptop = new ShippableProduct("Gaming Laptop", 1200, 8, 2.5);
        Product tv = new ShippableProduct("TV", 5000, 5, 15.0);
        Customer vipCustomer = new Customer("Sarah (VIP)", 10000.0);

        Cart vipCart = new Cart();
        try {
            vipCart.add(laptop, 2);  // $2400
            vipCart.add(tv, 1);      // $5000
            // Total: $7400 - qualifies for bulk (10%) + VIP (15%) discounts

            System.out.println("VIP customer cart:");
            vipCart.displayContents();

            System.out.println("\nProcessing VIP checkout with multiple discounts...");
            CheckoutService.checkout(vipCustomer, vipCart);

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static void runMixedCart() {
        System.out.println("MIXED CART SCENARIO");
        System.out.println("==================");
        System.out.println("Testing cart with physical + digital products");
        System.out.println();

        Product cheese = new ShippableExpirableProduct("Cheese", 100, 10, 0.2, LocalDate.now().plusDays(30));
        Product ebook = new Product("Programming E-book", 30, 50);
        Product scratchCard = new Product("Mobile scratch card", 50, 20);
        Customer customer = new Customer("John (New)", 1000.0);

        Cart mixedCart = new Cart();
        try {
            mixedCart.add(cheese, 3);      // Food item (5% tax, shippable)
            mixedCart.add(ebook, 2);       // Digital item (12% tax, non-shippable)
            mixedCart.add(scratchCard, 4); // Digital item (12% tax, non-shippable)
            // Total items: 9 - qualifies for multi-item discount (5%)

            System.out.println("Mixed product cart:");
            mixedCart.displayContents();

            System.out.println("\nProcessing mixed cart with different tax rates...");
            CheckoutService.checkout(customer, mixedCart);

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static void runErrorShowcase() {
        System.out.println("ERROR HANDLING SHOWCASE");
        System.out.println("======================");
        System.out.println("Complete demonstration of error handling");
        System.out.println();

        Product tv = new ShippableProduct("TV", 5000, 5, 15.0);
        Customer customer = new Customer("Ahmed (Regular)", 1000.0);

        // Test 1: Insufficient stock
        System.out.println("Test 1: Attempting to buy more than available stock");
        Cart cart1 = new Cart();
        try {
            cart1.add(tv, 10); // Only 5 available
            CheckoutService.checkout(customer, cart1);
        } catch (Exception e) {
            System.out.println("SUCCESS: Correctly handled insufficient stock");
        }

        // Test 2: Insufficient balance
        System.out.println("\nTest 2: Attempting purchase with insufficient balance");
        Cart cart2 = new Cart();
        try {
            cart2.add(tv, 1); // TV costs 5000, customer has 1000
            CheckoutService.checkout(customer, cart2);
        } catch (Exception e) {
            System.out.println("SUCCESS: Correctly handled insufficient balance");
        }

        // Test 3: Expired product
        System.out.println("\nTest 3: Attempting to purchase expired product");
        Product expiredMilk = new ShippableExpirableProduct("Expired Milk", 40, 5, 1.0, LocalDate.now().minusDays(3));
        Cart expiredCart = new Cart();
        try {
            expiredCart.add(expiredMilk, 1);
            CheckoutService.checkout(customer, expiredCart);
        } catch (Exception e) {
            System.out.println("SUCCESS: Correctly handled expired product");
        }

        // Test 4: Empty cart
        System.out.println("\nTest 4: Attempting to checkout empty cart");
        Cart emptyCart = new Cart();
        CheckoutService.checkout(customer, emptyCart);
    }

    private static void runFreeShippingShowcase() {
        System.out.println("FREE SHIPPING SHOWCASE");
        System.out.println("=====================");
        System.out.println("Demonstrating free shipping for high-value orders");
        System.out.println();

        Product tv = new ShippableProduct("TV", 5000, 5, 15.0);
        Customer vipCustomer = new Customer("Sarah (VIP)", 10000.0);

        Cart cart = new Cart();
        try {
            cart.add(tv, 1); // TV costs 5000 - qualifies for free shipping

            System.out.println("High-value cart (qualifies for free shipping):");
            cart.displayContents();

            System.out.println("\nProcessing checkout with free shipping...");
            CheckoutService.checkout(vipCustomer, cart);

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
