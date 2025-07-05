import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import exceptions.*;
import utils.ConsoleFormatter;

/**
 * Enhanced checkout service with improved error handling, validation,
 * and professional receipt formatting.
 */
public class CheckoutService {
    private static final ShippingService shippingService = new ShippingService();
    public static void checkout(Customer customer, Cart cart) {
        try {
            performCheckout(customer, cart);
        } catch (EmptyCartException e) {
            System.err.println(e.getMessage());
        } catch (InsufficientStockException e) {
            System.err.println(e.getMessage());
        } catch (ProductExpiredException e) {
            System.err.println(e.getMessage());
        } catch (InsufficientBalanceException e) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error during checkout: " + e.getMessage());
        }
    }

    private static void performCheckout(Customer customer, Cart cart)
            throws EmptyCartException, InsufficientStockException,
                   ProductExpiredException, InsufficientBalanceException {
        // Rule: Cart cannot be empty
        if (cart.isEmpty()) {
            throw new EmptyCartException();
        }
        double subtotal = 0;
        List<Shippable> shippableItems = new ArrayList<>();

        // Check stock, expiration, and calculate subtotal
        for (Map.Entry<Product, Integer> entry : cart.getItems().entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();

            // Rule: Check if product is out of stock (double check at checkout)
            if (product.getQuantity() < quantity) {
                throw new InsufficientStockException(product.getName(), quantity, product.getQuantity());
            }

            // Rule: Check if product is expired
            if (product.isExpired()) {
                if (product instanceof ExpirableProduct) {
                    ExpirableProduct expirable = (ExpirableProduct) product;
                    throw new ProductExpiredException(product.getName(), expirable.getExpirationDate());
                } else {
                    throw new ProductExpiredException(product.getName(), null);
                }
            }

            subtotal += product.getPrice() * quantity;

            // If the product is shippable, add it to our shipping list
            if (product instanceof Shippable) {
                shippableItems.add((Shippable) product);
            }
        }
        // Apply discounts
        double totalDiscount = calculateDiscounts(cart, customer, subtotal);
        double discountedSubtotal = subtotal - totalDiscount;

        // Calculate shipping fee using the enhanced shipping service
        double shippingFee = 0.0;
        if (!shippableItems.isEmpty()) {
            shippingFee = shippingService.calculateShippingFee(shippableItems, cart.getItems(), discountedSubtotal);
        }

        // Calculate taxes on discounted subtotal
        double taxAmount = calculateTax(cart, discountedSubtotal);

        double totalAmount = discountedSubtotal + shippingFee + taxAmount;

        // Rule: Check if customer has enough balance
        if (customer.getBalance() < totalAmount) {
            throw new InsufficientBalanceException(totalAmount, customer.getBalance());
        }

        // --- If all checks pass, proceed with payment ---
        // 1. Deduct quantities from stock
        for (Map.Entry<Product, Integer> entry : cart.getItems().entrySet()) {
            entry.getKey().decreaseQuantity(entry.getValue());
        }

        // 2. Deduct money from customer
        customer.deductBalance(totalAmount);

        // 3. Create shipment and get actual shipping fee
        if (!shippableItems.isEmpty()) {
            shippingFee = shippingService.createShipment(shippableItems, cart.getItems(), subtotal);
        }

        // 4. Print professional checkout receipt
        printCheckoutReceipt(cart, customer, subtotal, totalDiscount, shippingFee, taxAmount, totalAmount, customer.getBalance());
    }

    /**
     * Prints a professional checkout receipt with proper formatting.
     *
     * @param cart Shopping cart
     * @param customer Customer making the purchase
     * @param subtotal Order subtotal before discounts
     * @param totalDiscount Total discount amount
     * @param shippingFee Shipping fee
     * @param taxAmount Tax amount
     * @param totalAmount Total amount
     * @param remainingBalance Customer's remaining balance
     */
    private static void printCheckoutReceipt(Cart cart, Customer customer, double subtotal, double totalDiscount,
                                           double shippingFee, double taxAmount, double totalAmount,
                                           double remainingBalance) {
        System.out.println();
        System.out.println(ConsoleFormatter.BOLD + ConsoleFormatter.GREEN + "** Checkout Receipt **" + ConsoleFormatter.RESET);
        System.out.println(ConsoleFormatter.info("Customer: " + customer.getName()));
        System.out.println(ConsoleFormatter.createSeparator('═', 45));

        // Print items
        for (Map.Entry<Product, Integer> entry : cart.getItems().entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            double itemTotal = product.getPrice() * quantity;

            System.out.println(ConsoleFormatter.formatLineItem(
                quantity, product.getName(), ConsoleFormatter.formatCurrency(itemTotal), 20));
        }

        System.out.println(ConsoleFormatter.createSeparator('─', 45));
        System.out.println(ConsoleFormatter.formatSummaryLine(
            "Subtotal", ConsoleFormatter.formatCurrency(subtotal), 25));

        // Show discounts if any
        if (totalDiscount > 0) {
            System.out.println(ConsoleFormatter.formatSummaryLine(
                "Discounts", "-" + ConsoleFormatter.formatCurrency(totalDiscount), 25));
            System.out.println(ConsoleFormatter.formatSummaryLine(
                "After Discounts", ConsoleFormatter.formatCurrency(subtotal - totalDiscount), 25));
        }

        // Show shipping
        if (shippingFee > 0) {
            System.out.println(ConsoleFormatter.formatSummaryLine(
                "Shipping", ConsoleFormatter.formatCurrency(shippingFee), 25));
        } else if (shippingFee == 0 && !cart.getItems().isEmpty()) {
            // Check if there are shippable items
            boolean hasShippableItems = cart.getItems().keySet().stream()
                .anyMatch(product -> product instanceof Shippable);
            if (hasShippableItems) {
                System.out.println(ConsoleFormatter.formatSummaryLine(
                    "Shipping", ConsoleFormatter.GREEN + "FREE" + ConsoleFormatter.RESET, 25));
            }
        }

        // Show taxes
        if (taxAmount > 0) {
            System.out.println(ConsoleFormatter.formatSummaryLine(
                "Tax", ConsoleFormatter.formatCurrency(taxAmount), 25));
        }

        System.out.println(ConsoleFormatter.createSeparator('─', 45));
        System.out.println(ConsoleFormatter.BOLD + ConsoleFormatter.formatSummaryLine(
            "Total Amount", ConsoleFormatter.formatCurrency(totalAmount), 25) + ConsoleFormatter.RESET);
        System.out.println(ConsoleFormatter.createSeparator('═', 45));

        System.out.println(ConsoleFormatter.info(
            customer.getName() + "'s balance after payment: " + ConsoleFormatter.formatCurrency(remainingBalance)));
    }

    /**
     * Calculates applicable discounts for a cart.
     *
     * @param cart Shopping cart
     * @param customer Customer making the purchase
     * @param subtotal Order subtotal before discounts
     * @return Total discount amount
     */
    private static double calculateDiscounts(Cart cart, Customer customer, double subtotal) {
        double totalDiscount = 0;

        System.out.println("\n" + ConsoleFormatter.BOLD + ConsoleFormatter.GREEN +
            "Applying Discounts:" + ConsoleFormatter.RESET);
        System.out.println(ConsoleFormatter.createSeparator('─', 40));

        // Bulk discount for orders over $300
        if (subtotal >= 300) {
            double discount = subtotal * 0.10; // 10% off
            totalDiscount += discount;
            System.out.println(ConsoleFormatter.success(
                "Large Order Discount (10% off): -" + ConsoleFormatter.formatCurrency(discount)));
        }

        // First-time customer discount (simulated by checking if balance is exactly 1000)
        if (customer.getBalance() == 1000.0) {
            double discount = 25.0;
            totalDiscount += Math.min(discount, subtotal);
            System.out.println(ConsoleFormatter.success(
                "Welcome Discount: -" + ConsoleFormatter.formatCurrency(Math.min(discount, subtotal))));
        }

        // High-value customer discount
        if (subtotal >= 1000) {
            double discount = subtotal * 0.15; // 15% off
            totalDiscount += discount;
            System.out.println(ConsoleFormatter.success(
                "VIP Discount (15% off): -" + ConsoleFormatter.formatCurrency(discount)));
        }

        // Quantity-based discount
        int totalItems = cart.getItems().values().stream().mapToInt(Integer::intValue).sum();
        if (totalItems >= 5) {
            double discount = subtotal * 0.05; // 5% off
            totalDiscount += discount;
            System.out.println(ConsoleFormatter.success(
                "Multi-Item Discount (5% off): -" + ConsoleFormatter.formatCurrency(discount)));
        }

        if (totalDiscount > 0) {
            System.out.println(ConsoleFormatter.createSeparator('─', 40));
            System.out.println(ConsoleFormatter.BOLD + ConsoleFormatter.formatSummaryLine(
                "Total Savings:", ConsoleFormatter.formatCurrency(totalDiscount), 25) + ConsoleFormatter.RESET);
        } else {
            System.out.println(ConsoleFormatter.info("No discounts applicable"));
        }

        return totalDiscount;
    }

    /**
     * Calculates tax for a shopping cart based on product categories.
     *
     * @param cart Shopping cart
     * @param subtotal Subtotal after discounts
     * @return Total tax amount
     */
    private static double calculateTax(Cart cart, double subtotal) {
        double totalTax = 0;

        System.out.println("\n" + ConsoleFormatter.BOLD + ConsoleFormatter.YELLOW +
            "Tax Calculation:" + ConsoleFormatter.RESET);
        System.out.println(ConsoleFormatter.createSeparator('─', 40));

        for (Map.Entry<Product, Integer> entry : cart.getItems().entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            double itemTotal = product.getPrice() * quantity;

            double taxRate = getTaxRateForProduct(product);
            double itemTax = itemTotal * taxRate;
            totalTax += itemTax;

            System.out.println(ConsoleFormatter.formatSummaryLine(
                product.getName() + " (" + (taxRate * 100) + "%)",
                ConsoleFormatter.formatCurrency(itemTax), 25));
        }

        System.out.println(ConsoleFormatter.createSeparator('─', 40));
        System.out.println(ConsoleFormatter.BOLD + ConsoleFormatter.formatSummaryLine(
            "Total Tax:", ConsoleFormatter.formatCurrency(totalTax), 25) + ConsoleFormatter.RESET);

        return totalTax;
    }

    /**
     * Determines the tax rate for a specific product based on its characteristics.
     */
    private static double getTaxRateForProduct(Product product) {
        String productName = product.getName().toLowerCase();

        // Food items (5% tax)
        if (productName.contains("cheese") || productName.contains("biscuit") ||
            productName.contains("milk") || productName.contains("food")) {
            return 0.05;
        }

        // Electronics (10% tax)
        if (productName.contains("tv") || productName.contains("laptop") ||
            productName.contains("monitor") || productName.contains("server") ||
            productName.contains("equipment")) {
            return 0.10;
        }

        // Digital products (12% tax)
        if (productName.contains("digital") || productName.contains("download") ||
            productName.contains("scratch card") || productName.contains("mobile")) {
            return 0.12;
        }

        // General items (8% tax)
        return 0.08;
    }
}