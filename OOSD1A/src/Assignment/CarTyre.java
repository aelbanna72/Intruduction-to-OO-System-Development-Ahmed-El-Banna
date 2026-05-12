package Assignment;


public class CarTyre extends StockItem {

    // Additional private instance variables
    private String tyreSize;     
    private String tyreBrand;    


    public CarTyre(String stockCode, int quantity, double price, String tyreSize, String tyreBrand) {
        super(stockCode, quantity, price);
        this.tyreSize = tyreSize;
        this.tyreBrand = tyreBrand;
    }

    // Getters and setters for new fields

    public String getTyreSize() {
        return tyreSize;
    }

    public void setTyreSize(String tyreSize) {
        this.tyreSize = tyreSize;
    }

    public String getTyreBrand() {
        return tyreBrand;
    }

    public void setTyreBrand(String tyreBrand) {
        this.tyreBrand = tyreBrand;
    }

    @Override
    public String getStockName() {
        return "Car Tyre";
    }

    @Override
    public String getStockDescription() {
        return tyreBrand + " " + tyreSize;
    }


    @Override
    public String toString() {
        return super.toString() + "\n" +
                "Tyre Size: " + tyreSize + "\n" +
                "Tyre Brand: " + tyreBrand;
    }
}
