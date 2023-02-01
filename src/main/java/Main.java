import config.Constant;
import dao.InvoiceDAO;
import dao.ProductDAO;
import model.Invoice;
import service.InvoiceService;
import service.ProductService;

import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        ProductDAO productDAO=new ProductDAO();
        Scanner scanner=new Scanner(System.in);
        InvoiceDAO invoiceDAO=new InvoiceDAO();

        ProductService productService = new ProductService(productDAO,scanner);
        InvoiceService invoiceService =new InvoiceService(invoiceDAO,scanner,productDAO,productService);

        int productType = Constant.ALL;
        while (true) {
            System.out.println("WELLCOME");
            System.out.println("1. Manage invoice");
            System.out.println("2. Manage product");
            System.out.println("3. Exit");
            System.out.println("Enter your choice(1-3):");
            int choice;
            while (true) {
                try {
                    choice = Integer.parseInt(scanner.nextLine());
                    if (choice >= 1 && choice <= 3)
                        break;
                    System.out.println("Invalid choice,please try again ");
                } catch (Exception e) {
                    System.out.println("Please enter number");
                }
            }
            switch (choice) {
                case 1:
                    invoiceService.menuInvoice();
                    break;
                case 2:
                    while (true) {
                        productType = productService.productMenu(productType);
                        if (productType == Constant.GO_BACK) {
                            productType = Constant.ALL;
                            break;
                        }
                    }
                    break;
                case 3:
                    System.exit(1);
            }

        }
    }
}
