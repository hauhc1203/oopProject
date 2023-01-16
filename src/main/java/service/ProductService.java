package service;

import config.Constant;
import dao.ProductDAO;
import model.ElectricDevice;
import model.Laptop;
import model.SmartPhone;
import util.ProjectUtils;
import util.StringUtils;

import java.util.List;
import java.util.Scanner;


public class ProductService {
    private ProductDAO productDAO = new ProductDAO();
    private Scanner scanner = new Scanner(System.in);

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
        System.out.println("3. View product detail");
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

    public int productMenu(int productType) {
        showAllProduct(productType);
        int choice = manageProduct();
        switch (choice) {
            case 1:
                ElectricDevice electricDevice = createProduct();
                if (electricDevice == null)
                    break;
                productDAO.save(electricDevice);
                break;
            case 2:
                break;
            case 3:
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
        String name = getInputString("Product name");
        String brand = getInputString("Brand");
        String model = getInputString("Model");
        double salePrice = getInputDouble("Sale price");
        double importPrice = getInputDouble("Import price");
        int quantity = getInputInteger("Quantity");
        switch (productType) {
            case Constant.SMARTPHONE:
                double width = getInputDouble("Width");
                double height = getInputDouble("Height");
                int batteryLife = getInputInteger("Battery life");
                double resolution = getInputInteger("Resolution");
                System.out.println("Create new product success!!!");
                return new SmartPhone(productCode, name, brand, model, salePrice, importPrice, quantity, productType, width, height, batteryLife, resolution);
            case Constant.LAPTOP:
                String cpu = getInputString("Cpu info");
                int ram = getInputInteger("RAM info");
                int hardDiskCapacity = getInputInteger("hard disk capacity");
                System.out.println("Create new product success!!!");
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

    public double getInputDouble(String property) {
        while (true) {
            System.out.println("Enter " + property + ":");
            try {
                double value = Double.parseDouble(scanner.nextLine());
                if (value < 0)
                    throw new RuntimeException();
                return value;
            } catch (Exception e) {
                System.out.println("Invalid " + property);
            }

        }
    }

    public Integer getInputInteger(String property) {
        while (true) {
            System.out.println("Enter " + property + ":");
            try {
                int value = Integer.parseInt(scanner.nextLine());
                if (value < 0)
                    throw new RuntimeException();
                return value;
            } catch (Exception e) {
                System.out.println("Invalid " + property);
            }

        }
    }

    public String getInputString(String property) {
        while (true) {
            System.out.println("Enter " + property + " (8-20 characters):");
            String value = scanner.nextLine();
            if (ProjectUtils.validate(Constant.STRING_PROPERTY_REGEX, value, 4, 20)) {
                return value;
            } else {
                System.out.println("Invalid " + property);
            }
        }
    }

//    public static void main(String[] args) {
//        ProductService productService = new ProductService();
//        int productType = Constant.ALL;
//        while (true) {
//            productType = productService.productMenu(productType);
//        }
//
//    }
}
