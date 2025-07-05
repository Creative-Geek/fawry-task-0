import java.util.HashMap;
import java.util.Map;
import exceptions.*;
import utils.ConsoleFormatter;

/**
 * Enhanced shopping cart with comprehensive validation and error handling.
 * Supports adding/removing items with proper stock validation.
 */
public class Cart {
    private Map<Product, Integer> items = new HashMap<>();

    /**
     * Adds a product to the cart with specified quantity.
     *
     * @param product Product to add
     * @param quantity Quantity to add
     * @throws InsufficientStockException if requested quantity exceeds available stock
     * @throws IllegalArgumentException if product is null or quantity is invalid
     */
    public void add(Product product, int quantity) throws InsufficientStockException {
        validateAddRequest(product, quantity);

        int currentQuantity = items.getOrDefault(product, 0);
        int totalQuantity = currentQuantity + quantity;

        // Check if total quantity would exceed available stock
        if (totalQuantity > product.getQuantity()) {
            throw new InsufficientStockException(product.getName(), totalQuantity, product.getQuantity());
        }

        items.put(product, totalQuantity);
        System.out.println(ConsoleFormatter.success("Added " + quantity + " x " + product.getName() + " to cart."));
    }

    /**
     * Removes a product from the cart or reduces its quantity.
     *
     * @param product Product to remove
     * @param quantity Quantity to remove (if 0 or greater than current, removes all)
     */
    public void remove(Product product, int quantity) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }

        if (!items.containsKey(product)) {
            System.out.println(ConsoleFormatter.warning("Product " + product.getName() + " is not in the cart."));
            return;
        }

        int currentQuantity = items.get(product);
        if (quantity <= 0 || quantity >= currentQuantity) {
            items.remove(product);
            System.out.println(ConsoleFormatter.success("Removed all " + product.getName() + " from cart."));
        } else {
            items.put(product, currentQuantity - quantity);
            System.out.println(ConsoleFormatter.success("Removed " + quantity + " x " + product.getName() + " from cart."));
        }
    }

    /**
     * Clears all items from the cart.
     */
    public void clear() {
        items.clear();
        System.out.println(ConsoleFormatter.success("Cart cleared."));
    }

    /**
     * Displays the current cart contents in a formatted way.
     */
    public void displayContents() {
        if (items.isEmpty()) {
            System.out.println(ConsoleFormatter.info("Cart is empty."));
            return;
        }

        System.out.println("\n" + ConsoleFormatter.BOLD + "Cart Contents:" + ConsoleFormatter.RESET);
        System.out.println(ConsoleFormatter.createSeparator('─', 40));

        double total = 0;
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            double itemTotal = product.getPrice() * quantity;
            total += itemTotal;

            System.out.println(ConsoleFormatter.formatLineItem(
                quantity, product.getName(), ConsoleFormatter.formatCurrency(itemTotal), 15));
        }

        System.out.println(ConsoleFormatter.createSeparator('─', 40));
        System.out.println(ConsoleFormatter.formatSummaryLine(
            "Cart Total:", ConsoleFormatter.formatCurrency(total), 20));
    }

    private void validateAddRequest(Product product, int quantity) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
    }
    public Map<Product, Integer> getItems() {
        return items;
    }
    public boolean isEmpty() {
        return items.isEmpty();
    }
}