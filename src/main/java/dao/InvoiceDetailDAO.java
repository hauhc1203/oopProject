package dao;

import config.DatabaseConnection;
import model.ElectricDevice;
import model.Invoice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;


/**
 *
 * @author hauhc1203
 */

public class InvoiceDetailDAO {

    private static final String INSERT_SQL = "INSERT INTO invoiceDetail " +
            "(productCode , invoiceCode ,quantity ,salePrice, importPrice ) VALUES (?,?,?,?,?);";
    private static final String FIND_ALL_BY_INVOICE = "select * from invoiceDetail where invoiceCode=?;";


    /**
     * save invoice detail
     * @author hauhc1203
     * @param items
     * @param invoiceCode
     *
     */
    public static boolean save(Map<ElectricDevice,Integer> items,String invoiceCode) {
        try (Connection connection = DatabaseConnection.getConnect()) {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL);
            for (Map.Entry<ElectricDevice,Integer> entry:items.entrySet()){
                ElectricDevice electricDevice=entry.getKey();
                preparedStatement.setString(1, electricDevice.getProductCode());
                preparedStatement.setString(2, invoiceCode);
                preparedStatement.setInt(3, entry.getValue());
                preparedStatement.setDouble(4, electricDevice.getSalePrice());
                preparedStatement.setDouble(5, electricDevice.getImportPrice());
                preparedStatement.execute();
            }
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
