package service;

import config.Constant;
import dao.ProductDAO;
import model.ElectricDevice;
import model.Laptop;
import model.SmartPhone;
import util.ProjectUtils;
import util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class ProductService {
    private ProductDAO productDAO = new ProductDAO();
    private Scanner scanner = new Scanner(System.in);

    public void showProductDetail(List<ElectricDevice> electricDeviceList) {
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println(StringUtils.center("LIST PRODUCT", 167));
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");


        String title = StringUtils.center("Product code", 15);
        title += "|";
        title += StringUtils.center("Product name", 20);
        title += "|";
        title += StringUtils.center("Brand", 12);
        title += "|";
        title += StringUtils.center("Sale price", 12);
        title += "|";
        title += StringUtils.center("Import price", 12);
        title += "|";
        title += StringUtils.center("Product type", 15);
        title += "|";
        title += StringUtils.center("Quantity", 10);
        title += "|";
        title += StringUtils.center("Width", 10);
        title += "|";
        title += StringUtils.center("Height", 10);
        title += "|";
        title += StringUtils.center("Resolution", 10);
        title += "|";
        title += StringUtils.center("Battery Life", 15);
        title += "|";
        title += StringUtils.center("CPU", 8);
        title += "|";
        title += StringUtils.center("RAM", 8);
        title += "|";
        title += StringUtils.center("Hard Disk", 10);



        System.out.println(title);
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        electricDeviceList.forEach(electricDevice -> {
            String value = StringUtils.center(electricDevice.getProductCode(), 15);
            value += "|";
            value += StringUtils.center(electricDevice.getName(), 20);
            value += "|";
            value += StringUtils.center(electricDevice.getBrand(), 12);
            value += "|";
            value += StringUtils.center(electricDevice.getSalePrice() + "", 12);
            value += "|";
            value += StringUtils.center(electricDevice.getImportPrice() + "", 12);
            value += "|";
            value += StringUtils.center(electricDevice.getProductTypeName(), 15);
            value += "|";
            value += StringUtils.center(electricDevice.getQuantity() + "", 10);
            if (electricDevice instanceof SmartPhone){
                value += "|";
                value += StringUtils.center(((SmartPhone) electricDevice).getWidth()+"", 10);
                value += "|";
                value += StringUtils.center(((SmartPhone) electricDevice).getHeight()+"", 10);
                value += "|";
                value += StringUtils.center(((SmartPhone) electricDevice).getResolution()+"", 10);
                value += "|";
                value += StringUtils.center(((SmartPhone) electricDevice).getBatteryLife()+"", 15);
                value += "|";
                value += StringUtils.center("-", 8);
                value += "|";
                value += StringUtils.center("-", 8);
                value += "|";
                value += StringUtils.center("-", 10);
            }else if (electricDevice instanceof Laptop){
                value += "|";
                value += StringUtils.center("-", 10);
                value += "|";
                value += StringUtils.center("-", 10);
                value += "|";
                value += StringUtils.center("-", 10);
                value += "|";
                value += StringUtils.center("-", 15);
                value += "|";
                value += StringUtils.center(((Laptop) electricDevice).getCpu(), 8);
                value += "|";
                value += StringUtils.center(((Laptop) electricDevice).getRam()+"", 8);
                value += "|";
                value += StringUtils.center(((Laptop) electricDevice).getHardDiskCapacity()+"", 10);
            }
            System.out.println(value);

        });
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

    }

    public void showAllProduct(int productType) {
        List<ElectricDevice> electricDeviceList = productDAO.findAll(productType);
        System.out.println("------------------------------------------------------------------------------------------------------");
        System.out.println(StringUtils.center("LIST PRODUCT", 96));
        System.out.println("------------------------------------------------------------------------------------------------------");
        String title = StringUtils.center("Product code", 15);
        title += "|";
        title += StringUtils.center("Product name", 20);
        title += "|";
        title += StringUtils.center("Brand", 12);
        title += "|";
        title += StringUtils.center("Sale price", 12);
        title += "|";
        title += StringUtils.center("Import price", 12);
        title += "|";
        title += StringUtils.center("Product type", 15);
        title += "|";
        title += StringUtils.center("Quantity", 10);
        System.out.println(title);
        System.out.println("------------------------------------------------------------------------------------------------------");
        electricDeviceList.forEach(electricDevice -> {
            String value = StringUtils.center(electricDevice.getProductCode(), 15);
            value += "|";
            value += StringUtils.center(electricDevice.getName(), 20);
            value += "|";
            value += StringUtils.center(electricDevice.getBrand(), 12);
            value += "|";
            value += StringUtils.center(electricDevice.getSalePrice() + "", 12);
            value += "|";
            value += StringUtils.center(electricDevice.getImportPrice() + "", 12);
            value += "|";
            value += StringUtils.center(electricDevice.getProductTypeName(), 15);
            value += "|";
            value += StringUtils.center(electricDevice.getQuantity() + "", 10);
            System.out.println(value);

        });
        System.out.println("------------------------------------------------------------------------------------------------------");

    }


    public int manageProduct() {
        System.out.println("1. Create product");
        System.out.println("2. Update product");
        System.out.println("3. Search product");
        System.out.println("4. Delete product");
        System.out.println("5. List product by type");
        System.out.println("6. Go back");
        while (true) {
            System.out.println("Enter your choice: ");
            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
                if (choice > 6 || choice < 1) {
                    System.out.println("Invalid choice!!!");
                } else {
                    return choice;
                }
            } catch (Exception e) {
                System.out.println("Please enter number!!!");
            }
        }
    }

    public void search() {
        List<ElectricDevice> electricDevices;
        List<Integer> choices = new ArrayList<>();
        choices.add(1);
        choices.add(2);
        choices.add(Constant.GO_BACK);
        while (true) {
            System.out.println("1. Search by name");
            System.out.println("2. Search by brand");
            System.out.println("3. Go back");

            int choice = ProjectUtils.getInputInteger("choice", choices);
            switch (choice) {
                case 1:
                    String name = ProjectUtils.getInputString("product name", 1, 20);
                    electricDevices = productDAO.findByNameOrBrand(name, null);
                    break;
                case 2:
                    String brand = ProjectUtils.getInputString("brand", 4, 20);
                    electricDevices = productDAO.findByNameOrBrand(null, brand);
                    break;
                default:
                    return;


            }
            showProductDetail(electricDevices);
        }

    }


    public int productMenu(int productType) {
        showAllProduct(productType);
        int choice = manageProduct();
        switch (choice) {
            case 1:
                ElectricDevice electricDevice = createProduct();
                if (electricDevice == null)
                    break;
                productDAO.save(electricDevice);
                System.out.println("Create new product success!!!");
                break;
            case 2:

                break;
            case 3:
                search();
                break;
            case 4:
                break;
            case 5:
                int pT = selectProductTypeToShow();
                if (pT == Constant.GO_BACK)
                    break;
                return pT;
            case 6:
                return Constant.GO_BACK;
        }
        return productType;
    }


    public ElectricDevice createProduct() {
        System.out.println("Create product: ");
        int productType = chooseProductType();
        if (productType == Constant.GO_BACK) return null;
        String productCode = getInputProductCode();
        String name = ProjectUtils.getInputString("Product name", 8, 20);
        if (name == null)
            return null;
        String brand = ProjectUtils.getInputString("Brand", 4, 20);
        if (brand == null)
            return null;
        String model = ProjectUtils.getInputString("Model", 4, 20);
        if (model == null)
            return null;

        double salePrice = ProjectUtils.getInputDouble("Sale price");
        if (salePrice < 0)
            return null;
        double importPrice = ProjectUtils.getInputDouble("Import price");

        if (importPrice < 0)
            return null;
        int quantity = ProjectUtils.getInputInteger("Quantity", null);
        if (quantity == Constant.ERROR_3_TIMES)
            return null;
        switch (productType) {
            case Constant.SMARTPHONE:
                double width = ProjectUtils.getInputDouble("Width");
                if (width < 0)
                    return null;
                double height = ProjectUtils.getInputDouble("Height");
                if (height < 0)
                    return null;
                int batteryLife = ProjectUtils.getInputInteger("Battery life", null);
                if (batteryLife == Constant.ERROR_3_TIMES)
                    return null;
                double resolution = ProjectUtils.getInputInteger("Resolution", null);
                if (resolution < 0)
                    return null;

                return new SmartPhone(productCode, name, brand, model, salePrice, importPrice, quantity, productType, width, height, batteryLife, resolution);
            case Constant.LAPTOP:
                String cpu = ProjectUtils.getInputString("Cpu info", 8, 20);
                if (cpu == null)
                    return null;
                int ram = ProjectUtils.getInputInteger("RAM info", null);
                if (ram == Constant.ERROR_3_TIMES)
                    return null;
                int hardDiskCapacity = ProjectUtils.getInputInteger("hard disk capacity", null);
                if (hardDiskCapacity == Constant.ERROR_3_TIMES)
                    return null;
                return new Laptop(productCode, name, brand, model, salePrice, importPrice, quantity, productType, cpu, ram, hardDiskCapacity);
        }

        return null;
    }


    public int chooseProductType() {

        System.out.println("1. Smartphone");
        System.out.println("2. Laptop");
        System.out.println("3. Back");
        while (true) {
            System.out.print("Enter your choice(1,2 or 3): ");
            try {
                int choie = Integer.parseInt(scanner.nextLine());

                if (choie == Constant.SMARTPHONE || choie == Constant.LAPTOP || choie == Constant.GO_BACK) {
                    return choie;
                }
                System.out.println("Invalid choice , please try again ");
            } catch (Exception e) {
                System.out.println("Please enter number");
            }
        }
    }

    public int selectProductTypeToShow() {
        System.out.println("0. All");
        System.out.println("1. Smartphone");
        System.out.println("2. Laptop");
        System.out.println("3. Back");
        while (true) {
            System.out.print("Enter your choice(0-3): ");
            try {
                int choie = Integer.parseInt(scanner.nextLine());

                if (choie == Constant.SMARTPHONE || choie == Constant.LAPTOP || choie == Constant.ALL || choie == Constant.GO_BACK) {
                    return choie;
                }
                System.out.println("Invalid choice , please try again ");
            } catch (Exception e) {
                System.out.println("Please enter number");
            }
        }
    }

    public String getInputProductCode() {
        while (true) {
            System.out.println("Enter product code(8 characters, letters or numbers)");
            String productCode = scanner.nextLine();
            if (ProjectUtils.validate(Constant.PRODUCT_CODE_REGEX, productCode, 8, 8)) {
                if (!ProjectUtils.isExistedProductCode(productCode))
                    return productCode;
                System.out.println("Product code existed, please try again!");
            } else {
                System.out.println("Product code invalid, please try again!");
            }
        }
    }
}
