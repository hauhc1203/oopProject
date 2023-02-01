package model;


import config.Constant;
/**
 * @author hauhc1203
 */
public class ElectricDevice {
    private String productCode;
    private String name;
    private String brand;
    private String model;
    private double salePrice;
    private double importPrice;
    private int quantity;
    private int productType;

    public ElectricDevice() {
    }

    public ElectricDevice(String productCode, String name, String brand, String model, double salePrice, double importPrice, int quantity, int productType) {
        this.productCode = productCode;
        this.name = name;
        this.brand = brand;
        this.model = model;
        this.salePrice = salePrice;
        this.importPrice = importPrice;
        this.quantity = quantity;
        this.productType = productType;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    public double getImportPrice() {
        return importPrice;
    }

    public void setImportPrice(double importPrice) {
        this.importPrice = importPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getProductType() {
        return productType;
    }

    public void setProductType(int productType) {
        this.productType = productType;
    }


    public String getProductTypeName() {
        if (this.productType == Constant.SMARTPHONE)
            return "SMART PHONE";
        if (this.productType == Constant.LAPTOP)
            return "LAPTOP";
        return null;
    }


    public String getInfo() {
        return
                "productCode='" + productCode + '\'' +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", salePrice=" + salePrice +
                ", importPrice=" + importPrice +
                ", quantity=" + quantity +
                ", productType=" + productType
                ;
    }
}
