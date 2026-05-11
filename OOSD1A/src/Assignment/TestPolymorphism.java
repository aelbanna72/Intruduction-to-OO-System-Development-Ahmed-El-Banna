package Assignment;

import java.util.Scanner;


public class TestPolymorphism {


    public static void itemInstance(StockItem s) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n--- Processing: " + s.getStockName() + " [" + s.getStockCode() + "] ---");
        System.out.println("Printing item stock information:");
        System.out.println(s);

        // Ask how many units to add
        System.out.print("\nEnter number of units to add: ");
        int addAmount = scanner.nextInt();
        s.addStock(addAmount);
        System.out.println("After adding stock:");
        System.out.println(s);

        // Ask how many units to sell
        System.out.print("\nEnter number of units to sell: ");
        int sellAmount = scanner.nextInt();
        boolean sold = s.sellStock(sellAmount);
        if (sold) {
            System.out.println("Stock sold successfully.");
        }
        System.out.println("After selling stock:");
        System.out.println(s);

        // Ask for a new price
        System.out.print("\nEnter new price (without VAT): ");
        double newPrice = scanner.nextDouble();
        s.setPrice(newPrice);
        System.out.println("After price change:");
        System.out.println(s);
    }

    public static void main(String[] args) {

        System.out.println("=== Step 3: Polymorphism Test ===\n");

        // Array of StockItem holding 3 different subclass instances (+ NavSys = 4 slots)
        StockItem[] s = new StockItem[4];

        s[0] = new NavSys("NS101", 10, 99.99);
        s[1] = new CarTyre("CT201", 15, 79.99, "205/55R16", "Michelin");
        s[2] = new EngineOil("EO301", 20, 24.99, "5W-30", 5);
        s[3] = new CarBattery("CB401", 8, 89.99, 60, "AGM");

        // Loop through each item and call itemInstance (polymorphic dispatch)
        for (StockItem item : s) {
            itemInstance(item);
        }
    }
}
