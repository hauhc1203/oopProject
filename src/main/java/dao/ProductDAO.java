package dao;

import config.Constant;
import config.DatabaseConnection;
import model.ElectricDevice;
import model.Laptop;
import model.SmartPhone;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO implements IDAO<ElectricDevice> {
    private static  String findByProductCode = "select * from product where productCode=?;";
    private String saveSQL = "insert into product" +
            "(productCode,name,brand,model,salePrice,importPrice,quantity,productType,width,height,batteryLife,resolution,cpu,ram,hardDiskCapacity)" +
            " value (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

    private String updateSQL = "update product SET " +
            "name= ? ,brand= ?,model= ?,salePrice= ?,importPrice= ?,quantity= ?,productType= ?," +
            "width= ?,height= ?,batteryLife= ?,resolution= ?," +
            "cpu= ?,ram= ?,hardDiskCapacity= ? where productCode= ?" ;

    public static ElectricDevice findByProductCode(String productCode){
        try (Connection connection = DatabaseConnection.getConnect()) {
            PreparedStatement preparedStatement=connection.prepareStatement(findByProductCode);
            preparedStatement.setString(1,productCode);
            ResultSet rs=preparedStatement.executeQuery();
            while (rs.next()) {
                double salePrice= rs.getDouble("salePrice");
                double importPrice= rs.getDouble("importPrice");
                int quantity= rs.getInt("quantity");
                ElectricDevice electricDevice=new ElectricDevice();
                electricDevice.setProductCode(productCode);
                electricDevice.setQuantity(quantity);
                electricDevice.setSalePrice(salePrice);
                electricDevice.setImportPrice(importPrice);
                return electricDevice;
            }
        } catch (SQLException e) {
            return null;
        }
        return null;
    }

    public static List<String> getAllProductCode(){
        String findAllSQL = "select * from product  ";
        List<String> productCodes = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnect()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(findAllSQL);
            while (resultSet.next()) {
                productCodes.add(resultSet.getString("productCode"));
            }
        }catch (SQLException e) {
           return null;
        }
        return productCodes;
    }

//    public static void main(String[] args) {
//        ProductDAO productDAO=new ProductDAO();
//        Laptop laptop=new Laptop("123as123d","asus " ,"asus", "model laptop" ,100.2,99.2,10,Constant.LAPTOP,"Cpu anc",8,500);
//        SmartPhone smartPhone=new SmartPhone("hasd1","asus vivobook" ,"asus", "model laptop" ,100.2,99.2,10,Constant.SMARTPHONE,12,11,12,12);
//        System.out.println(productDAO.save(laptop));
//    }

    @Override
    public List<ElectricDevice> findAll(int productType) {
        String findAllSQL = "select * from product  ";
        String where = "where productType=" + productType;
        if (productType == Constant.ALL)
            where = "";
        findAllSQL += where;
        List<ElectricDevice> electricDevices = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnect()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(findAllSQL);
            while (resultSet.next()) {
                String productCode = resultSet.getString("productCode");
                String name = resultSet.getString("name");
                String brand = resultSet.getString("brand");
                String model = resultSet.getString("model");
                double salePrice =resultSet.getDouble("salePrice");
                double importPrice =resultSet.getDouble("importPrice");
                int quantity = resultSet.getInt("quantity");
                int productType1=Integer.parseInt(resultSet.getString("productType"));
                if (productType1 == Constant.SMARTPHONE) {
                    float width = resultSet.getFloat("width");
                    float height = resultSet.getFloat("height");
                    int batteryLife = resultSet.getInt("batteryLife");
                    int resolution = resultSet.getInt("resolution");
                    electricDevices.add(
                            new SmartPhone(productCode, name, brand, model, salePrice, importPrice, quantity,productType1, width, height, batteryLife, resolution));
                } else if (productType1==Constant.LAPTOP){
                    String cpu = resultSet.getString("cpu");
                    int ram = resultSet.getInt("ram");
                    int hardDiskCapacity = resultSet.getInt("hardDiskCapacity");
                    electricDevices.add(new Laptop(productCode, name, brand, model, salePrice, importPrice, quantity, productType1,cpu,ram,hardDiskCapacity));
                }

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return electricDevices;
    }

    @Override
    public boolean save(ElectricDevice electricDevice) {

        try (Connection connection = DatabaseConnection.getConnect()){
            PreparedStatement preparedStatement = connection.prepareStatement(saveSQL);
            preparedStatement.setString(1, electricDevice.getProductCode());
            preparedStatement.setString(2, electricDevice.getName());
            preparedStatement.setString(3, electricDevice.getBrand());
            preparedStatement.setString(4, electricDevice.getModel());
            preparedStatement.setDouble(5, electricDevice.getSalePrice());
            preparedStatement.setDouble(6, electricDevice.getImportPrice());
            preparedStatement.setInt(7, electricDevice.getQuantity());
            preparedStatement.setInt(8, electricDevice.getProductType());

            if (electricDevice instanceof SmartPhone){
                preparedStatement.setDouble(9, ((SmartPhone) electricDevice).getWidth());
                preparedStatement.setDouble(10, ((SmartPhone) electricDevice).getHeight());
                preparedStatement.setInt(11, ((SmartPhone) electricDevice).getBatteryLife());
                preparedStatement.setDouble(12, ((SmartPhone) electricDevice).getResolution());
                preparedStatement.setString(13, "");
                preparedStatement.setInt(14, 0);
                preparedStatement.setInt(15, 0);

            }else if (electricDevice instanceof Laptop){
                preparedStatement.setFloat(9, 0);
                preparedStatement.setFloat(10, 0);
                preparedStatement.setInt(11, 0);
                preparedStatement.setFloat(12,0);
                preparedStatement.setString(13, ((Laptop) electricDevice).getCpu());
                preparedStatement.setInt(14, ((Laptop) electricDevice).getRam());
                preparedStatement.setInt(15, ((Laptop) electricDevice).getHardDiskCapacity());
            }else {
                return false;
            }
            preparedStatement.execute();
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
    public ElectricDevice findById(int id) {
        return null;
    }

    @Override
    public boolean edit(ElectricDevice electricDevice) {
        try (Connection connection = DatabaseConnection.getConnect()){
            PreparedStatement preparedStatement = connection.prepareStatement(updateSQL);

            preparedStatement.setString(1, electricDevice.getName());
            preparedStatement.setString(2, electricDevice.getBrand());
            preparedStatement.setString(3, electricDevice.getModel());
            preparedStatement.setDouble(4, electricDevice.getSalePrice());
            preparedStatement.setDouble(5, electricDevice.getImportPrice());
            preparedStatement.setInt(6, electricDevice.getQuantity());
            preparedStatement.setInt(7, electricDevice.getProductType());

            if (electricDevice instanceof SmartPhone){
                preparedStatement.setDouble(8, ((SmartPhone) electricDevice).getWidth());
                preparedStatement.setDouble(9, ((SmartPhone) electricDevice).getHeight());
                preparedStatement.setInt(10, ((SmartPhone) electricDevice).getBatteryLife());
                preparedStatement.setDouble(11, ((SmartPhone) electricDevice).getResolution());
                preparedStatement.setString(12, "");
                preparedStatement.setInt(13, 0);
                preparedStatement.setInt(14, 0);

            }else if (electricDevice instanceof Laptop){
                preparedStatement.setFloat(8, 0);
                preparedStatement.setFloat(9, 0);
                preparedStatement.setInt(10, 0);
                preparedStatement.setFloat(11,0);
                preparedStatement.setString(12, ((Laptop) electricDevice).getCpu());
                preparedStatement.setInt(13, ((Laptop) electricDevice).getRam());
                preparedStatement.setInt(14, ((Laptop) electricDevice).getHardDiskCapacity());
            }else {
                return false;
            }
            preparedStatement.setString(15,electricDevice.getProductCode());
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
