package model;

public class Laptop extends ElectricDevice {
    private String cpu;
    private int ram;
    private int hardDiskCapacity;

    public Laptop() {
    }

    public Laptop(String productCode, String name, String brand, String model, double salePrice, double importPrice, int quantity, int productType, String cpu, int ram, int hardDiskCapacity) {
        super(productCode, name, brand, model, salePrice, importPrice, quantity, productType);
        this.cpu = cpu;
        this.ram = ram;
        this.hardDiskCapacity = hardDiskCapacity;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public int getRam() {
        return ram;
    }

    public void setRam(int ram) {
        this.ram = ram;
    }

    public int getHardDiskCapacity() {
        return hardDiskCapacity;
    }

    public void setHardDiskCapacity(int hardDiskCapacity) {
        this.hardDiskCapacity = hardDiskCapacity;
    }


    @Override
    public String getInfo() {
        return "Laptop{" +
                super.getInfo()+
                "cpu='" + cpu + '\'' +
                ", ram=" + ram +
                ", hardDiskCapacity=" + hardDiskCapacity +
                '}';
    }

}
