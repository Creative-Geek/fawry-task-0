package utils;

import java.text.DecimalFormat;

/**
 * Utility class for consistent console formatting throughout the application.
 * Provides methods for creating professional-looking output with proper alignment and styling.
 * This is NOT my code.
 */
public class ConsoleFormatter {
    private static final DecimalFormat CURRENCY_FORMAT = new DecimalFormat("#,##0.00");
    private static final DecimalFormat WEIGHT_FORMAT = new DecimalFormat("#,##0.##");
    
    // ANSI Color codes for enhanced output
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String BOLD = "\u001B[1m";
    
    /**
     * Formats currency values consistently.
     */
    public static String formatCurrency(double amount) {
        return "$" + CURRENCY_FORMAT.format(amount);
    }
    
    /**
     * Formats weight values consistently.
     */
    public static String formatWeight(double weightInKg) {
        if (weightInKg < 1.0) {
            return WEIGHT_FORMAT.format(weightInKg * 1000) + "g";
        } else {
            return WEIGHT_FORMAT.format(weightInKg) + "kg";
        }
    }
    
    /**
     * Creates a horizontal line separator.
     */
    public static String createSeparator(char character, int length) {
        return String.valueOf(character).repeat(length);
    }
    
    /**
     * Creates a section header with proper formatting.
     */
    public static String createHeader(String title, int width) {
        int padding = (width - title.length()) / 2;
        String leftPad = " ".repeat(Math.max(0, padding));
        String rightPad = " ".repeat(Math.max(0, width - title.length() - padding));
        return BOLD + BLUE + leftPad + title + rightPad + RESET;
    }
    
    /**
     * Formats a line item with proper alignment.
     */
    public static String formatLineItem(int quantity, String name, String value, int nameWidth) {
        return String.format("%-3dx %-" + nameWidth + "s %10s", quantity, name, value);
    }
    
    /**
     * Formats a summary line with proper alignment.
     */
    public static String formatSummaryLine(String label, String value, int labelWidth) {
        return String.format("%-" + labelWidth + "s %10s", label, value);
    }
    
    /**
     * Creates a success message with green color.
     */
    public static String success(String message) {
        return GREEN + "[SUCCESS] " + message + RESET;
    }
    
    /**
     * Creates an error message with red color.
     */
    public static String error(String message) {
        return RED + "[ERROR] " + message + RESET;
    }
    
    /**
     * Creates a warning message with yellow color.
     */
    public static String warning(String message) {
        return YELLOW + "[WARNING] " + message + RESET;
    }
    
    /**
     * Creates an info message with cyan color.
     */
    public static String info(String message) {
        return CYAN + "[INFO] " + message + RESET;
    }
}
