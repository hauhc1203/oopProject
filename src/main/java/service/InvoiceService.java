package service;

import config.Constant;
import dao.InvoiceDAO;
import dao.ProductDAO;
import model.ElectricDevice;
import model.Invoice;
import util.ProjectUtils;
import java.util.*;

/**
 * @author hauhc1203
 */
public class InvoiceService {
    private InvoiceDAO invoiceDAO ;
    private Scanner scanner ;

    private ProductDAO productDAO;

    ProductService productService ;


    public InvoiceService(InvoiceDAO invoiceDAO, Scanner scanner, ProductDAO productDAO, ProductService productService) {
        this.invoiceDAO = invoiceDAO;
        this.scanner = scanner;
        this.productDAO = productDAO;
        this.productService = productService;
    }

    public InvoiceService() {
    }

    public int chooseInvoiceType() {
        System.out.println("1. Import");
        System.out.println("2. Export");
        System.out.println("3. Back");
        List<Integer> choices = new ArrayList<>();
        choices.add(1);
        choices.add(2);
        choices.add(3);
        while (true) {
            int choie = ProjectUtils.getInputInteger("choice", choices);
            if (choie==Constant.ERROR_3_TIMES)
                return choie;
            if (choie == Constant.IMPORT_INVOICE || choie == Constant.EXPORT_INVOICE || choie == Constant.GO_BACK) {
                return choie;
            }
            System.out.println("Invalid choice , please try again ");

        }
    }

    public Invoice create() {
        int choice = chooseInvoiceType();
        if (choice == Constant.GO_BACK||choice==Constant.ERROR_3_TIMES) return null;
        productService.showAllProduct(Constant.ALL);
        System.out.println("Enter product code and quantity to continue");
        System.out.println("If the product does not exist yet. Press 'b' to go back");
        System.out.println("Enter any character except 'b' to continue");
        String s = scanner.nextLine();
        if (s.equalsIgnoreCase("b"))
            return null;
        boolean isDone = false;
        Map<ElectricDevice, Integer> items = new HashMap<>();
        while (!isDone) {
            ElectricDevice electricDevice =ProjectUtils.getProduct();
            if (electricDevice==null)
                return null;
            int qtyExported=ProjectUtils.getValueByProductCode(electricDevice.getProductCode(), items);
            if (choice==Constant.EXPORT_INVOICE&&electricDevice.getQuantity()==qtyExported){
                System.out.println("The maximum amount has been reached");
                continue;
            }
            int quantity = getQuantity(electricDevice.getQuantity() - qtyExported, choice);
            if (quantity==Constant.ERROR_3_TIMES)
                return null;
            ProjectUtils.addProductToItems(electricDevice, quantity, items);
            System.out.println("Add product success!!!");
            System.out.println("1. Add new");
            System.out.println("2. Done");
            System.out.println("3. Cancel");
            List<Integer> choices = new ArrayList<>();
            choices.add(1);
            choices.add(2);
            choices.add(3);
            boolean isValidChoice = false;
            while (!isValidChoice) {
                int choice1 = ProjectUtils.getInputInteger("choice", choices);
                switch (choice1) {
                    case 1:
                        isValidChoice = true;
                        break;
                    case 2:
                        isValidChoice = true;
                        isDone = true;
                        break;
                    case 3:
                        return null;
                    default:
                        System.out.println("invalid choice , try again");
                }

            }

        }
        String invoiceCode = ProjectUtils.generateInvoiceCode();
        double[] data = ProjectUtils.getTotalInvoice(items);
        return new Invoice(invoiceCode, items, data[0],data[1],data[2], choice);
    }

    public int getQuantity(int max, int invoiceType) {
        while (true) {

            if (invoiceType == Constant.IMPORT_INVOICE) {
                int quantity = ProjectUtils.getInputInteger("quantity",null);
                if (quantity > 0)
                    return quantity;

            }
            if (invoiceType == Constant.EXPORT_INVOICE) {
                int quantity = ProjectUtils.getInputInteger( max);
                if (quantity > 0 && quantity <= max)
                    return quantity;
                if (quantity==Constant.ERROR_3_TIMES)
                    return quantity;
            }



        }
    }





    public void menuInvoice() {
        List<Integer> choices = new ArrayList<>();
        choices.add(1);
        choices.add(2);
        choices.add(3);
        while (true) {
            System.out.println("INVOICE MANAGER");
            System.out.println("1. Create Invoice");
            System.out.println("2. Revenue and profit");
            System.out.println("3. Go back");

            int choice = ProjectUtils.getInputInteger("choice", choices);

            switch (choice) {
                case 1:
                    Invoice invoice = create();
                    if (invoice == null)
                        break;
                    invoiceDAO.save(invoice);
                    updateQuantity(invoice);
                    System.out.println("create invoice success!!!");
                    break;
                case 2:
                    String dateStart=ProjectUtils.getInputDateString("date start");
                    if (dateStart==null)
                        break;
                    String dateEnd=ProjectUtils.getInputDateString("date end");
                    if (dateEnd==null)
                        break;
                    double[] data=invoiceDAO.getRevenueAndProfit(dateStart,dateEnd);
                    System.out.println(String.format(Constant.PARTTEN_OVERVIEW,"Revenue: ",data[0]));
                    System.out.println(String.format(Constant.PARTTEN_OVERVIEW,"Profit: ",data[1]));
                    break;
                case 3:
                    return;
                default:
                    System.out.println("No choice!!!");;
            }

        }
    }
    public void  updateQuantity(Invoice invoice){
        Map<ElectricDevice,Integer> items=invoice.getItems();
        for (Map.Entry<ElectricDevice,Integer> entry:items.entrySet()
             ) {
            ElectricDevice electricDevice=entry.getKey();
            int quantity=entry.getValue();
            ElectricDevice electricDevice1=ProductDAO.findByProductCode(electricDevice.getProductCode());

            if (invoice.getInvoiceType()==Constant.IMPORT_INVOICE){
                quantity+=electricDevice1.getQuantity();
            }else {
                quantity=electricDevice1.getQuantity()-quantity;
            }

            ProductDAO.updateQuantityByProductCode(electricDevice.getProductCode(),quantity);

        }

    }

}
