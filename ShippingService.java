import java.util.List;
import java.util.Map;
import utils.ConsoleFormatter;

/**
 * Enhanced shipping service that handles shipment creation and fee calculation
 * with weight-based pricing and professional formatting.
 */
public class ShippingService {
    private static final double BASE_SHIPPING_FEE = 15.0;
    private static final double WEIGHT_RATE_PER_KG = 10.0;
    private static final double FREE_SHIPPING_THRESHOLD = 500.0;

    /**
     * Creates a shipment for the given items and returns the shipping fee.
     * This method only accepts a list of objects that fulfill the "Shippable" contract.
     *
     * @param items List of shippable items
     * @param quantities Map of products to their quantities
     * @param subtotal Order subtotal for free shipping calculation
     * @return Calculated shipping fee
     */
    public double createShipment(List<Shippable> items, Map<Product, Integer> quantities, double subtotal) {
        if (items.isEmpty()) {
            return 0.0; // No shipment needed
        }

        double totalWeight = 0;
        StringBuilder shipmentDetails = new StringBuilder();

        // Header
        shipmentDetails.append("\n").append(ConsoleFormatter.BOLD).append(ConsoleFormatter.BLUE);
        shipmentDetails.append("** Shipment Notice **").append(ConsoleFormatter.RESET).append("\n");
        shipmentDetails.append(ConsoleFormatter.createSeparator('═', 45)).append("\n");

        // Items
        for (Shippable item : items) {
            int quantity = quantities.get((Product) item);
            double itemTotalWeight = item.getWeight() * quantity;
            totalWeight += itemTotalWeight;

            String weightDisplay = ConsoleFormatter.formatWeight(itemTotalWeight);
            shipmentDetails.append(ConsoleFormatter.formatLineItem(
                quantity, item.getName(), weightDisplay, 20)).append("\n");
        }

        // Summary
        shipmentDetails.append(ConsoleFormatter.createSeparator('─', 45)).append("\n");
        shipmentDetails.append(ConsoleFormatter.formatSummaryLine(
            "Total package weight:", ConsoleFormatter.formatWeight(totalWeight), 25)).append("\n");

        double shippingFee = calculateShippingFee(totalWeight, subtotal);

        if (shippingFee == 0) {
            shipmentDetails.append(ConsoleFormatter.success(
                "FREE SHIPPING (Order over " + ConsoleFormatter.formatCurrency(FREE_SHIPPING_THRESHOLD) + ")")).append("\n");
        } else {
            shipmentDetails.append(ConsoleFormatter.formatSummaryLine(
                "Shipping fee:", ConsoleFormatter.formatCurrency(shippingFee), 25)).append("\n");
        }

        shipmentDetails.append(ConsoleFormatter.createSeparator('═', 45)).append("\n");

        System.out.print(shipmentDetails.toString());
        return shippingFee;
    }

    /**
     * Calculates shipping fee for a list of shippable items.
     *
     * @param items List of shippable items
     * @param quantities Map of products to their quantities
     * @param subtotal Order subtotal
     * @return Calculated shipping fee
     */
    public double calculateShippingFee(List<Shippable> items, Map<Product, Integer> quantities, double subtotal) {
        if (items.isEmpty()) {
            return 0.0;
        }

        double totalWeight = 0;
        for (Shippable item : items) {
            int quantity = quantities.get((Product) item);
            totalWeight += item.getWeight() * quantity;
        }

        return calculateShippingFee(totalWeight, subtotal);
    }

    /**
     * Calculates shipping fee based on weight and order value.
     *
     * @param totalWeight Total weight in kg
     * @param subtotal Order subtotal
     * @return Calculated shipping fee
     */
    private double calculateShippingFee(double totalWeight, double subtotal) {
        // Free shipping for orders over threshold
        if (subtotal >= FREE_SHIPPING_THRESHOLD) {
            return 0.0;
        }

        // Base fee + weight-based fee
        return BASE_SHIPPING_FEE + (totalWeight * WEIGHT_RATE_PER_KG);
    }
}