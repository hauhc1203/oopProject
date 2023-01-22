package util;

import config.Constant;
import dao.InvoiceDAO;
import dao.ProductDAO;
import model.ElectricDevice;
import model.Invoice;
import org.w3c.dom.CDATASection;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProjectUtils {
    private static Scanner scanner = new Scanner(System.in);
    static String[] upper = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    static String[] lower = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
    static int[] number = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    static int size1 = upper.length - 1;
    static int size2 = number.length - 1;

    public static int makeRandom(int min, int max) {
        return (int) ((Math.random()) * ((max - min) + 1) + min);
    }

    public static String createCode() {
        String newCode = "";
        for (int i = 0; i < 8; i++) {
            int from = makeRandom(1, 3);
            switch (from) {
                case 1:
                    newCode += upper[makeRandom(0, size1)];
                    break;
                case 2:
                    newCode += lower[makeRandom(0, size1)];
                    break;
                case 3:
                    newCode += number[makeRandom(0, size2)];
                    break;
            }
        }

        return newCode;
    }

    public static String generateInvoiceCode() {
        while (true) {
            String invoiceCode = createCode();
            if (!InvoiceDAO.isExistedInvoiceCode(invoiceCode))
                return invoiceCode;
        }
    }

    public static boolean validate(String regex, String str, int minlen, int maxlen) {
        if (str.length() > maxlen || str.length() < minlen)
            return false;
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    public static boolean isExistedProductCode(String productCode) {
        List<String> productCodes = ProductDAO.getAllProductCode();

        if (productCodes.contains(productCode))
            return true;
        return false;
    }

    public static double getInputDouble(String property) {
        int c = 0;
        while (c < 3) {
            System.out.println("Enter " + property + ":");
            try {
                double value = Double.parseDouble(scanner.nextLine());
                if (value >= 0)
                    return value;
                c++;
                System.out.println("Invalid " + property + ", you have " + (3 - c) + " chances left");
            } catch (Exception e) {
                c++;
                System.out.println("Invalid " + property + ", you have " + (3 - c) + " chances left");
            }

        }
        return -1;
    }

    public static void addProductToItems(ElectricDevice electricDevice, int quantity, Map<ElectricDevice, Integer> items) {
        for (Map.Entry<ElectricDevice, Integer> entry : items.entrySet()) {
            if (entry.getKey().getProductCode().equals(electricDevice.getProductCode())) {
                entry.setValue(entry.getValue() + quantity);
                return;
            }
        }
        items.put(electricDevice, quantity);

    }

    public static Integer getValueByProductCode(String productCode, Map<ElectricDevice, Integer> items) {
        for (Map.Entry<ElectricDevice, Integer> entry : items.entrySet()) {
            if (entry.getKey().getProductCode().equals(productCode)) {
                return entry.getValue();
            }
        }
        return 0;
    }

    public static double[] getTotalInvoice(Map<ElectricDevice, Integer> items) {
        double totalSalePrice = 0;

        double totalImportPrice = 0;
        for (Map.Entry<ElectricDevice, Integer> entry : items.entrySet()) {
            double salePrice = entry.getKey().getSalePrice();
            double importPrice = entry.getKey().getImportPrice();

            totalSalePrice += salePrice * entry.getValue();
            totalImportPrice += importPrice * entry.getValue();

        }
        double data[] = new double[3];
        data[0] = totalSalePrice;
        data[1] = totalImportPrice;
        data[2] = totalSalePrice - totalImportPrice;
        return data;

    }


    public static int getInputInteger(String property, List<Integer> choices) {
        int c = 0;
        while (c < 3) {
            System.out.println("Enter " + property + ":");
            try {
                int value = Integer.parseInt(scanner.nextLine());
                if (choices != null && choices.contains(value) || value >= 0)
                    return value;
                c++;
                System.out.println("Invalid " + property + ", you have " + (3 - c) + " chances left");
            } catch (Exception e) {
                c++;
                System.out.println("Invalid " + property + ", you have " + (3 - c) + " chances left");
            }

        }
        return Constant.ERROR_3_TIMES;
    }

    public static String getInputDateString(String property) {
        int c = 0;
        while (c < 3) {
            System.out.println("Enter " + property + " :");
            String value = scanner.nextLine();
            if (ProjectUtils.validate(Constant.DATE_REGEX, value, 10, 10)) {
                return value;
            } else {
                c++;
                System.out.println("Invalid " + property + ", you have " + (3 - c) + " chances left");
            }
        }
        return null;
    }

    public static String getInputString(String property, int min, int max) {
        int c = 0;
        while (c < 3) {
            System.out.println("Enter " + property + " (" + min + "-" + max + " characters):");
            String value = scanner.nextLine();
            if (ProjectUtils.validate(Constant.STRING_PROPERTY_REGEX, value, min, max)) {
                return value;
            } else {
                c++;
                System.out.println("Invalid " + property + ", you have " + (3 - c) + " chances left");
            }
        }
        return null;
    }
}
