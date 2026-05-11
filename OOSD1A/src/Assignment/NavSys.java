package Assignment;


public class NavSys extends StockItem {


    public NavSys(String stockCode, int quantity, double price) {
        super(stockCode, quantity, price);
    }


    @Override
    public String getStockName() {
        return "Navigation system";
    }


    @Override
    public String getStockDescription() {
        return "GeoVision Sat Nav";
    }


    @Override
    public String toString() {
        return super.toString();
    }
}