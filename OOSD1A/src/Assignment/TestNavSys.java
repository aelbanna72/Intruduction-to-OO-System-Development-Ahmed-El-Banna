package Assignment;


public class TestNavSys {

    public static void main(String[] args) {

        System.out.println("=== Step 2: NavSys Test ===\n");

        // Task 1: Create a NavSys stock item
        NavSys navItem = new NavSys("NS101", 10, 99.99);
        System.out.println("Task 1. Creating a stock with 10 units Navigation system, price 99.99, item code NS101");
        System.out.println(navItem);
        System.out.println();

        // Task 2: Add 10 more units
        navItem.addStock(10);
        System.out.println("Task 2. Increasing 10 more units");
        System.out.println(navItem);
        System.out.println();

        // Task 3: Sell 2 units
        navItem.sellStock(2);
        System.out.println("Task 3. Sold 2 units");
        System.out.println(navItem);
        System.out.println();

        // Task 4: Set new price
        navItem.setPrice(100.99);
        System.out.println("Task 4. Set new price 100.99 per unit");
        System.out.println(navItem);
        System.out.println();

        // Task 5: Try to add 0 units (error case)
        System.out.println("Task 5. Increasing 0 more units");
        navItem.addStock(0);
    }
}