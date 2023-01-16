package dao;

import config.DatabaseConnection;
import model.Invoice;
import model.Laptop;
import model.SmartPhone;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InvoiceDAO implements IDAO<Invoice> {
    private static final String INSERT_SQL = "INSERT INTO invoice " +
            "( invoiceCode  ,total, type ) VALUES (?,?,?);";
    private static final String FIND_ALL_BY_DATE = "S INTO invoice " +
            "( invoiceCode ,date ,total, type ) VALUES (?,?,?,?);";
    private static final String FIND_ALL_INVOICE_CODE = "SELECT invoiceCode FROM invoice;";

    public static boolean isExistedInvoiceCode(String invoiceCode){
        List<String> invoiceCodes=new ArrayList<>();
        try (Connection connection= DatabaseConnection.getConnect(); PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_INVOICE_CODE)){
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
               invoiceCodes.add(rs.getString("invoiceCode"));
            }
        } catch (SQLException e) {
            return false;
        }
        if (invoiceCodes.contains(invoiceCode))
            return true;
        return false;

    }


    @Override
    public List<Invoice> findAll(int productType) {
        return null;
    }

    @Override
    public boolean save(Invoice invoice) {
        try (Connection connection = DatabaseConnection.getConnect()) {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL);
            preparedStatement.setString(1, invoice.getInvoiceCode());
//            preparedStatement.setString(2, invoice.getDate().toString());
            preparedStatement.setDouble(2, invoice.getTotal());
            preparedStatement.setInt(3, invoice.getType());
            preparedStatement.execute();
            if (!InvoiceDetailDAO.save(invoice.getItems(),invoice.getInvoiceCode())){
                return false;
            };
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean deleteByID(int id) {
        return false;
    }

    @Override
    public Invoice findById(int id) {
        return null;
    }

    @Override
    public boolean edit(Invoice invoice) {
        return false;
    }
}
