package Assignment;


public class CarBattery extends StockItem {

    // Additional private instance variables
    private int capacityAh;       // Ampere-hour rating, e.g. 60
    private String batteryType;   // e.g. "AGM", "Lead-Acid"


    public CarBattery(String stockCode, int quantity, double price, int capacityAh, String batteryType) {
        super(stockCode, quantity, price);
        this.capacityAh = capacityAh;
        this.batteryType = batteryType;
    }

    // Getters and setters for new fields

    public int getCapacityAh() {
        return capacityAh;
    }

    public void setCapacityAh(int capacityAh) {
        this.capacityAh = capacityAh;
    }

    public String getBatteryType() {
        return batteryType;
    }

    public void setBatteryType(String batteryType) {
        this.batteryType = batteryType;
    }

    @Override
    public String getStockName() {
        return "Car Battery";
    }

    @Override
    public String getStockDescription() {
        return batteryType + " " + capacityAh + "Ah";
    }


    @Override
    public String toString() {
        return super.toString() + "\n" +
                "Battery Type: " + batteryType + "\n" +
                "Capacity: " + capacityAh + "Ah";
    }
}
