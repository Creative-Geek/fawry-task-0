#!/bin/bash

# Colors for output (using ASCII-only approach)
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
PURPLE='\033[0;35m'
CYAN='\033[0;36m'
NC='\033[0m' # No Color

echo -e "${BLUE}========================================"
echo -e " Fawry E-Commerce System Test Runner"
echo -e "========================================${NC}"
echo

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo -e "${RED}ERROR: Java is not installed or not in PATH${NC}"
    echo "Please install Java 8 or higher and try again"
    exit 1
fi

if ! command -v javac &> /dev/null; then
    echo -e "${RED}ERROR: Java compiler (javac) is not installed or not in PATH${NC}"
    echo "Please install Java JDK 8 or higher and try again"
    exit 1
fi

echo -e "${YELLOW}Compiling Java files...${NC}"
javac *.java exceptions/*.java utils/*.java
if [ $? -ne 0 ]; then
    echo -e "${RED}ERROR: Compilation failed${NC}"
    exit 1
fi

echo -e "${GREEN}Compilation successful!${NC}"
echo

while true; do
    echo -e "${CYAN}Choose a test case to run:${NC}"
    echo
    echo "BASIC FUNCTIONALITY TESTS:"
    echo "  1. Core Functionality Demo"
    echo "  2. Basic Error Scenarios"
    echo "  3. Free Shipping Demo"
    echo
    echo "COMPREHENSIVE TESTS:"
    echo "  4. Core Features Validation"
    echo "  5. Advanced Features Demo"
    echo "  6. Error Handling Tests"
    echo "  7. Shipping Features Tests"
    echo "  8. Cart Management Tests"
    echo
    echo "SHOWCASE SCENARIOS:"
    echo "  9. VIP Customer with Multiple Discounts"
    echo " 10. Mixed Cart (Physical + Digital Products)"
    echo " 11. Complete Error Handling Showcase"
    echo " 12. Free Shipping Showcase"
    echo
    echo "COMPLETE TEST SUITES:"
    echo " 13. Run All Basic Tests (Main.java)"
    echo " 14. Run All Comprehensive Tests (TestSuite.java)"
    echo " 15. Run All Showcase Scenarios (FinalDemo.java)"
    echo
    echo " 16. Exit"
    echo
    read -p "Enter your choice (1-16): " choice

    case $choice in
        1)
            echo
            echo -e "${BLUE}========================================"
            echo -e " Core Functionality Demo"
            echo -e "========================================${NC}"
            echo
            java TestRunner coreFunctionalityDemo
            ;;
        2)
            echo
            echo -e "${BLUE}========================================"
            echo -e " Basic Error Scenarios"
            echo -e "========================================${NC}"
            echo
            java TestRunner basicErrorScenarios
            ;;
        3)
            echo
            echo -e "${BLUE}========================================"
            echo -e " Free Shipping Demo"
            echo -e "========================================${NC}"
            echo
            java TestRunner freeShippingDemo
            ;;
        4)
            echo
            echo -e "${BLUE}========================================"
            echo -e " Core Features Validation"
            echo -e "========================================${NC}"
            echo
            java TestRunner coreFeatures
            ;;
        5)
            echo
            echo -e "${BLUE}========================================"
            echo -e " Advanced Features Demo"
            echo -e "========================================${NC}"
            echo
            java TestRunner advancedFeatures
            ;;
        6)
            echo
            echo -e "${BLUE}========================================"
            echo -e " Error Handling Tests"
            echo -e "========================================${NC}"
            echo
            java TestRunner errorHandling
            ;;
        7)
            echo
            echo -e "${BLUE}========================================"
            echo -e " Shipping Features Tests"
            echo -e "========================================${NC}"
            echo
            java TestRunner shippingFeatures
            ;;
        8)
            echo
            echo -e "${BLUE}========================================"
            echo -e " Cart Management Tests"
            echo -e "========================================${NC}"
            echo
            java TestRunner cartManagement
            ;;
        9)
            echo
            echo -e "${BLUE}========================================"
            echo -e " VIP Customer with Multiple Discounts"
            echo -e "========================================${NC}"
            echo
            java TestRunner vipScenario
            ;;
        10)
            echo
            echo -e "${BLUE}========================================"
            echo -e " Mixed Cart (Physical + Digital)"
            echo -e "========================================${NC}"
            echo
            java TestRunner mixedCart
            ;;
        11)
            echo
            echo -e "${BLUE}========================================"
            echo -e " Complete Error Handling Showcase"
            echo -e "========================================${NC}"
            echo
            java TestRunner errorShowcase
            ;;
        12)
            echo
            echo -e "${BLUE}========================================"
            echo -e " Free Shipping Showcase"
            echo -e "========================================${NC}"
            echo
            java TestRunner freeShippingShowcase
            ;;
        13)
            echo
            echo -e "${BLUE}========================================"
            echo -e " Running All Basic Tests"
            echo -e "========================================${NC}"
            echo
            java Main
            ;;
        14)
            echo
            echo -e "${BLUE}========================================"
            echo -e " Running All Comprehensive Tests"
            echo -e "========================================${NC}"
            echo
            java TestSuite
            ;;
        15)
            echo
            echo -e "${BLUE}========================================"
            echo -e " Running All Showcase Scenarios"
            echo -e "========================================${NC}"
            echo
            java FinalDemo
            ;;
        16)
            echo
            echo -e "${GREEN}Thank you!${NC}"
            echo
            exit 0
            ;;
        *)
            echo -e "${RED}Invalid choice. Please try again.${NC}"
            continue
            ;;
    esac

    echo
    echo -e "${BLUE}========================================"
    echo -e "Test completed!"
    echo -e "========================================${NC}"
    echo
    read -p "Run another test? (y/n): " again
    case $again in
        [Yy]* ) continue;;
        [Nn]* ) break;;
        * ) echo "Please answer yes or no.";;
    esac
done

echo
echo -e "${GREEN}Thank you!${NC}"
echo
