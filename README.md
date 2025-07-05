# Fawry Task: Simple E-Commerce System

This is a task done during preparations for Fawry's Rise Journey program.

## What This Project Does

This is a comprehensive e-commerce system demo that showcases:

- Support for different product types

  - Basic products (digital items like mobile scratch cards)
  - Shippable products (physical items like TVs)
  - Expirable products (food items like cheese and biscuits)

- **Shopping Cart**
- **Customer Management**: Balance tracking and transaction processing
- **Checkout System**

## Additional Features

- **Smart Shipping Discount**: Weight-based shipping costs with free shipping for orders over $500
- **Tax Calculation**: Different tax rates for food (5%) vs digital items (12%)
- **Discount System For Multiple Items**: (5% for 5+ items, 10% for 10+ items)
- **Error Handling**: Comprehensive validation for expired products, insufficient stock, and insufficient balance
- **Professional Receipts**: Detailed checkout summaries with all calculations

## How to Run the Tests

### Prerequisites

- Java 8 or higher installed
- Java compiler (javac) available in PATH

### Quick Start

#### Option 1: Interactive Test Runner (Highly Recommended)

```bash
./run-tests.sh
```

On windows, you can use git bash terminal [get it with 'git' from here.](https://git-scm.com/downloads/win)

This launches an interactive menu where you can choose specific test cases to run.

#### Option 2: Manual Compilation and Execution

```bash
# Compile all Java files
javac *.java exceptions/*.java utils/*.java

# Run different test suites
java Main                    # Basic functionality demo
java TestSuite              # Comprehensive test suite
java FinalDemo              # Advanced showcase scenarios

# Run specific test cases
java TestRunner basicRequirementsDemo
java TestRunner advancedFeatures
java TestRunner errorHandling
```

### Available Test Categories

**Basic Tests:**

- Basic Requirements Demo
- Error Scenarios
- Free Shipping Demo

**Comprehensive Tests:**

- Advanced Features (discounts, taxes)
- Shipping Calculations
- Cart Management
- Product Expiration Handling

**Showcase Scenarios:**

- VIP Customer with Multiple Discounts
- Mixed Cart (Physical + Digital Products)
- Complete Error Handling
- Free Shipping Showcase
