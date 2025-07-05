/**
 * Represents a customer in the e-commerce system.
 * Manages customer information and balance operations.
 */
public class Customer {
    private String name;
    private double balance;

    /**
     * Creates a new customer with the specified name and initial balance.
     *
     * @param name Customer's name
     * @param balance Initial account balance
     */
    public Customer(String name, double balance) {
        this.name = name;
        this.balance = balance;
    }

    /**
     * Gets the customer's name.
     *
     * @return Customer's name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the customer's current balance.
     *
     * @return Current balance
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Deducts the specified amount from the customer's balance.
     * Only deducts if sufficient balance is available.
     *
     * @param amount Amount to deduct
     */
    public void deductBalance(double amount) {
        if (amount <= this.balance) {
            this.balance -= amount;
        }
    }

    /**
     * Adds the specified amount to the customer's balance.
     *
     * @param amount Amount to add
     */
    public void addBalance(double amount) {
        if (amount > 0) {
            this.balance += amount;
        }
    }

    @Override
    public String toString() {
        return String.format("Customer{name='%s', balance=%.2f}", name, balance);
    }
}