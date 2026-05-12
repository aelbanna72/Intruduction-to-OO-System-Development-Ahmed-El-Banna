package Assignment;


public class EngineOil extends StockItem {

    // Additional private instance variables
    private String viscosity;    
    private int volumeLitres;    


    public EngineOil(String stockCode, int quantity, double price, String viscosity, int volumeLitres) {
        super(stockCode, quantity, price);
        this.viscosity = viscosity;
        this.volumeLitres = volumeLitres;
    }

    // Getters and setters for new fields

    public String getViscosity() {
        return viscosity;
    }

    public void setViscosity(String viscosity) {
        this.viscosity = viscosity;
    }

    public int getVolumeLitres() {
        return volumeLitres;
    }

    public void setVolumeLitres(int volumeLitres) {
        this.volumeLitres = volumeLitres;
    }

    @Override
    public String getStockName() {
        return "Engine Oil";
    }

    @Override
    public String getStockDescription() {
        return viscosity + " " + volumeLitres + "L";
    }


    @Override
    public String toString() {
        return super.toString() + "\n" +
                "Viscosity: " + viscosity + "\n" +
                "Volume: " + volumeLitres + "L";
    }
}
