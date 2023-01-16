package service;

import config.Constant;
import dao.InvoiceDAO;
import dao.ProductDAO;
import model.ElectricDevice;
import model.Invoice;
import util.ProjectUtils;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class InvoiceService {
    private InvoiceDAO invoiceDAO = new InvoiceDAO();
    private Scanner scanner = new Scanner(System.in);

    ProductService productService = new ProductService();

    public int chooseInvoiceType() {
        System.out.println("1. Import");
        System.out.println("2. Export");
        System.out.println("3. Back");
        while (true) {
            int choie = ProjectUtils.getInputInteger("your choice");

            if (choie == Constant.IMPORT_INVOICE || choie == Constant.EXPORT_INVOICE || choie == Constant.GO_BACK) {
                return choie;
            }
            System.out.println("Invalid choice , please try again ");

        }
    }

    public Invoice create() {
        int choice = chooseInvoiceType();
        if (choice == Constant.GO_BACK) return null;
        productService.showAllProduct(Constant.ALL);
        System.out.println("Enter product code and quantity to continue");
        boolean isDone = false;
        Map<ElectricDevice, Integer> items = new HashMap<>();
        while (!isDone) {
            ElectricDevice electricDevice = getProduct();
            int quantity = getQuantity(electricDevice.getQuantity()-ProjectUtils.getValueByProductCode(electricDevice.getProductCode(),items),choice);
            ProjectUtils.addProductToItems(electricDevice,quantity,items);
            System.out.println("Add product success!!!");
            System.out.println("1. Add new");
            System.out.println("2. Done");
            System.out.println("3. Cancel");
            boolean isValidChoice = false;
            while (!isValidChoice) {
                int choice1=ProjectUtils.getInputInteger("your choice");
                switch (choice1){
                    case 1:
                        isValidChoice=true;
                        break;
                    case 2:
                        isValidChoice=true;
                        isDone=true;
                        break;
                    case 3:
                        return null;
                    default:
                        System.out.println("invalid choice , try again");
                }

            }

        }
        String invoiceCode=ProjectUtils.generateInvoiceCode();
        double total=ProjectUtils.getTotalInvoice(items,choice);
        return new Invoice(invoiceCode,items,total,choice);
    }

    public int getQuantity(int max ,int invoiceType) {
        while (true) {
            int quantity = ProjectUtils.getInputInteger("quantity , max "+max);
            if (invoiceType==Constant.IMPORT_INVOICE){
                if (quantity >  0)
                    return quantity;
            }
            if (invoiceType==Constant.EXPORT_INVOICE){
                if (quantity >  0&&quantity<=max)
                    return quantity;
            }

            System.out.println("Invalid quantity, please try again!");

        }
    }

    public ElectricDevice getProduct() {
        while (true) {
            System.out.println("Enter product code");
            String productCode = scanner.nextLine();
            if (ProjectUtils.validate(Constant.PRODUCT_CODE_REGEX, productCode, 8, 8)) {
                ElectricDevice electricDevice = ProductDAO.findByProductCode(productCode);
                if (electricDevice != null)
                    return electricDevice;
                System.out.println("Product code not existed, please try again!");
            } else {
                System.out.println("Product code invalid, please try again!");
            }
        }
    }

    public void menuInvoice() {
        while (true) {
            System.out.println("LIST INVOICES");
        }
    }

    public static void main(String[] args) {
        InvoiceService invoiceService=new InvoiceService();
        InvoiceDAO invoiceDAO1=new InvoiceDAO();
        invoiceDAO1.save(invoiceService.create());
    }
}
