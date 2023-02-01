package model;

import java.sql.Date;
import java.util.Map;

public class Invoice {
    private String invoiceCode;
    private Date date;
    private Map<ElectricDevice,Integer> items;
    private double totalSalePrice;
    private double totalImportPrice;
    private double totalProfit;
    private int invoiceType;

    public Invoice(String invoiceCode, Map<ElectricDevice, Integer> items, double totalSalePrice, double totalImportPrice, double totalProfit, int type) {
        this.invoiceCode = invoiceCode;
        this.items = items;
        this.totalSalePrice = totalSalePrice;
        this.totalImportPrice = totalImportPrice;
        this.totalProfit = totalProfit;
        this.invoiceType = type;
    }

    public double getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(double totalProfit) {
        this.totalProfit = totalProfit;
    }

    public String getInvoiceCode() {
        return invoiceCode;
    }

    public double getTotalSalePrice() {
        return totalSalePrice;
    }

    public void setTotalSalePrice(double totalSalePrice) {
        this.totalSalePrice = totalSalePrice;
    }

    public double getTotalImportPrice() {
        return totalImportPrice;
    }

    public void setTotalImportPrice(double totalImportPrice) {
        this.totalImportPrice = totalImportPrice;
    }

    public Invoice() {
    }



    public void setInvoiceCode(String invoiceCode) {
        this.invoiceCode = invoiceCode;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Map<ElectricDevice, Integer> getItems() {
        return items;
    }

    public void setItems(Map<ElectricDevice, Integer> items) {
        this.items = items;
    }


    public int getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(int invoiceType) {
        this.invoiceType = invoiceType;
    }


}
