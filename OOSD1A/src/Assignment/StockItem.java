package Assignment;


public class StockItem {

    // Constants
    private static final double VAT_RATE = 17.5;

    // Instance variables
    private final String stockCode;  // fixed 
    private int quantity;
    private double price;


    public StockItem(String stockCode, int quantity, double price) {
        this.stockCode = stockCode;
        this.quantity = quantity;
        this.price = price;
    }

    // -------------------------
    // Getters
    // -------------------------

    public String getStockCode() {
        return stockCode;
    }

    public int getQuantity() {
        return quantity;
    }


    public double getPrice() {
        return price;
    }


    public double getPriceWithVAT() {
        return price + (price * VAT_RATE / 100);
    }


    public double getVAT() {
        return VAT_RATE;
    }


    public String getStockName() {
        return "Unknown Stock Name";
    }


    public String getStockDescription() {
        return "Unknown Stock Description";
    }

    // -------------------------
    // Setters
    // -------------------------

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    public void setPrice(double price) {
        this.price = price;
    }

    // -------------------------
    // Stock management methods
    // -------------------------


    public void addStock(int amount) {
        if (amount < 1) {
            System.out.println("Error: Increased item must be greater than or equal to one.");
        } else if (quantity + amount > 100) {
            System.out.println("Error: Stock cannot exceed 100 units.");
        } else {
            quantity += amount;
        }
    }


    public boolean sellStock(int amount) {
        if (amount < 1) {
            System.out.println("Error: Sold amount must be greater than or equal to one.");
            return false;
        } else if (amount <= quantity) {
            quantity -= amount;
            return true;
        } else {
            System.out.println("Error: Not enough stock to sell.");
            return false;
        }
    }

    // -------------------------
    // toString
    // -------------------------


    @Override
    public String toString() {
        return "Stock Type: " + getStockName() + "\n" +
                "Description: " + getStockDescription() + "\n" +
                "Stock Code: " + getStockCode() + "\n" +
                "Price Without VAT: " + getPrice() + "\n" +
                "Price With VAT: " + getPriceWithVAT() + "\n" +
                "Total unit in stock: " + getQuantity();
    }
}
