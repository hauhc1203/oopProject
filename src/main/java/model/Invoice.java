package model;

import java.sql.Date;
import java.util.Map;

public class Invoice {
    private String invoiceCode;
    private Date date;
    private Map<ElectricDevice,Integer> items;
    private double total;
    private int type;

    public Invoice(String invoiceCode, Map<ElectricDevice, Integer> items, double total, int type) {
        this.invoiceCode = invoiceCode;
        this.items = items;
        this.total = total;
        this.type = type;
    }

    public Invoice(String invoiceCode, Date date, Map<ElectricDevice, Integer> items, double total, int type) {
        this.invoiceCode = invoiceCode;
        this.date = date;
        this.items = items;
        this.total = total;
        this.type = type;
    }

    public Invoice() {
    }

    public String getInvoiceCode() {
        return invoiceCode;
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

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
