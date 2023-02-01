package model;

/**
 * @author hauhc1203
 */
public class SmartPhone extends ElectricDevice {
    private double width;
    private double height;
    // don vi milisecond
    private int batteryLife;
    // don vi mega pixel
    private double resolution;

    public SmartPhone() {
    }

    public SmartPhone(String productCode, String name, String brand, String model, double salePrice, double importPrice, int quantity, int productType, double width, double height, int batteryLife, double resolution) {
        super(productCode, name, brand, model, salePrice, importPrice, quantity, productType);
        this.width = width;
        this.height = height;
        this.batteryLife = batteryLife;
        this.resolution = resolution;
    }


    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public int getBatteryLife() {
        return batteryLife;
    }

    public void setBatteryLife(int batteryLife) {
        this.batteryLife = batteryLife;
    }

    public double getResolution() {
        return resolution;
    }

    public void setResolution(double resolution) {
        this.resolution = resolution;
    }

    @Override
    public String getInfo() {
        return "SmartPhone{" +
                super.getInfo() +
                "width=" + width +
                ", height=" + height +
                ", batteryLife=" + batteryLife +
                ", resolution=" + resolution +
                '}';

    }
}

