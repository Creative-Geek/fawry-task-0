package exceptions;

/**
 * Exception thrown when customer doesn't have sufficient balance for purchase.
 */
public class InsufficientBalanceException extends ECommerceException {
    private final double requiredAmount;
    private final double availableBalance;
    
    public InsufficientBalanceException(double requiredAmount, double availableBalance) {
        super(String.format("Insufficient balance. Required: $%.2f, Available: $%.2f", 
              requiredAmount, availableBalance), "INSUFFICIENT_BALANCE");
        this.requiredAmount = requiredAmount;
        this.availableBalance = availableBalance;
    }
    
    public double getRequiredAmount() {
        return requiredAmount;
    }
    
    public double getAvailableBalance() {
        return availableBalance;
    }
}
