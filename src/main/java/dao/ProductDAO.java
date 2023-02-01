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
    private static String findByProductCode = "select * from product where productCode=?;";
    private static String findByName = "select * from product where name like ?;";
    private static String findByBrand = "select * from product where brand = ?;";


    private static String deleteByID = "delete from product where productCode=?";
    private static String updateQuantity = "update product set quantity=? where productCode=?;";
    private String saveSQL = "insert into product" +
            "(productCode,name,brand,model,salePrice,importPrice,quantity,productType,width,height,batteryLife,resolution,cpu,ram,hardDiskCapacity)" +
            " value (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

    private String updateSQL = "update product SET " +
            "name= ? ,brand= ?,model= ?,salePrice= ?,importPrice= ?,quantity= ?," +
            "width= ?,height= ?,batteryLife= ?,resolution= ?," +
            "cpu= ?,ram= ?,hardDiskCapacity= ? where productCode= ?";

    public List<ElectricDevice> setData(ResultSet rs) {
        List<ElectricDevice> electricDevices = new ArrayList<>();
        try {
            while (rs.next()) {
                String productCode = rs.getString("productCode");
                String name = rs.getString("name");
                String brand = rs.getString("brand");
                String model = rs.getString("model");
                double salePrice = rs.getDouble("salePrice");
                double importPrice = rs.getDouble("importPrice");
                int quantity = rs.getInt("quantity");
                int productType1 = Integer.parseInt(rs.getString("productType"));
                if (productType1 == Constant.SMARTPHONE) {
                    float width = rs.getFloat("width");
                    float height = rs.getFloat("height");
                    int batteryLife = rs.getInt("batteryLife");
                    int resolution = rs.getInt("resolution");
                    electricDevices.add(
                            new SmartPhone(productCode, name, brand, model, salePrice, importPrice, quantity, productType1, width, height, batteryLife, resolution));
                } else if (productType1 == Constant.LAPTOP) {
                    String cpu = rs.getString("cpu");
                    int ram = rs.getInt("ram");
                    int hardDiskCapacity = rs.getInt("hardDiskCapacity");
                    electricDevices.add(new Laptop(productCode, name, brand, model, salePrice, importPrice, quantity, productType1, cpu, ram, hardDiskCapacity));
                }

            }
        } catch (SQLException e) {

        }
        return electricDevices;
    }

    public List<ElectricDevice> findByNameOrBrand(String pname, String pbrand) {
        String query;

        if (pbrand != null) {
            query = findByBrand;
        } else {
            pname = "%" + pname + "%";
            query = findByName;
        }
        List<ElectricDevice> electricDevices = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnect()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            if (pbrand != null) {
                preparedStatement.setString(1, pbrand);
            } else {
                preparedStatement.setString(1, pname);
            }
            ResultSet rs = preparedStatement.executeQuery();
            electricDevices = setData(rs);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return electricDevices;
    }


    public static void updateQuantityByProductCode(String productCode, int quantity) {
        try (Connection connection = DatabaseConnection.getConnect()) {
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuantity);
            preparedStatement.setString(2, productCode);
            preparedStatement.setInt(1, quantity);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static   ElectricDevice findByProductCode(String productCode) {
        try (Connection connection = DatabaseConnection.getConnect()) {
            PreparedStatement preparedStatement = connection.prepareStatement(findByProductCode);
            preparedStatement.setString(1, productCode);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                double salePrice = rs.getDouble("salePrice");
                double importPrice = rs.getDouble("importPrice");
                int quantity = rs.getInt("quantity");
                ElectricDevice electricDevice = new ElectricDevice();
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

    public static List<String> getAllProductCode() {
        String findAllSQL = "select * from product  ";
        List<String> productCodes = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnect()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(findAllSQL);
            while (resultSet.next()) {
                productCodes.add(resultSet.getString("productCode"));
            }
        } catch (SQLException e) {
            return null;
        }
        return productCodes;
    }


    @Override
    public List<ElectricDevice> findAll(int productType) {
        String findAllSQL;
        if (productType == Constant.ALL) {
            findAllSQL = "select * from product order by quantity";
        } else {
            findAllSQL = " select * from product where productType=" + productType + " order by quantity";
        }

        List<ElectricDevice> electricDevices = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnect()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(findAllSQL);
            while (resultSet.next()) {
                String productCode = resultSet.getString("productCode");
                String name = resultSet.getString("name");
                String brand = resultSet.getString("brand");
                String model = resultSet.getString("model");
                double salePrice = resultSet.getDouble("salePrice");
                double importPrice = resultSet.getDouble("importPrice");
                int quantity = resultSet.getInt("quantity");
                int productType1 = Integer.parseInt(resultSet.getString("productType"));
                if (productType1 == Constant.SMARTPHONE) {
                    float width = resultSet.getFloat("width");
                    float height = resultSet.getFloat("height");
                    int batteryLife = resultSet.getInt("batteryLife");
                    int resolution = resultSet.getInt("resolution");
                    electricDevices.add(
                            new SmartPhone(productCode, name, brand, model, salePrice, importPrice, quantity, productType1, width, height, batteryLife, resolution));
                } else if (productType1 == Constant.LAPTOP) {
                    String cpu = resultSet.getString("cpu");
                    int ram = resultSet.getInt("ram");
                    int hardDiskCapacity = resultSet.getInt("hardDiskCapacity");
                    electricDevices.add(new Laptop(productCode, name, brand, model, salePrice, importPrice, quantity, productType1, cpu, ram, hardDiskCapacity));
                }

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return electricDevices;
    }

    @Override
    public boolean save(ElectricDevice electricDevice) {

        try (Connection connection = DatabaseConnection.getConnect()) {
            PreparedStatement preparedStatement = connection.prepareStatement(saveSQL);
            preparedStatement.setString(1, electricDevice.getProductCode());
            preparedStatement.setString(2, electricDevice.getName());
            preparedStatement.setString(3, electricDevice.getBrand());
            preparedStatement.setString(4, electricDevice.getModel());
            preparedStatement.setDouble(5, electricDevice.getSalePrice());
            preparedStatement.setDouble(6, electricDevice.getImportPrice());
            preparedStatement.setInt(7, electricDevice.getQuantity());
            preparedStatement.setInt(8, electricDevice.getProductType());

            if (electricDevice instanceof SmartPhone) {
                preparedStatement.setDouble(9, ((SmartPhone) electricDevice).getWidth());
                preparedStatement.setDouble(10, ((SmartPhone) electricDevice).getHeight());
                preparedStatement.setInt(11, ((SmartPhone) electricDevice).getBatteryLife());
                preparedStatement.setDouble(12, ((SmartPhone) electricDevice).getResolution());
                preparedStatement.setString(13, "");
                preparedStatement.setInt(14, 0);
                preparedStatement.setInt(15, 0);

            } else if (electricDevice instanceof Laptop) {
                preparedStatement.setFloat(9, 0);
                preparedStatement.setFloat(10, 0);
                preparedStatement.setInt(11, 0);
                preparedStatement.setFloat(12, 0);
                preparedStatement.setString(13, ((Laptop) electricDevice).getCpu());
                preparedStatement.setInt(14, ((Laptop) electricDevice).getRam());
                preparedStatement.setInt(15, ((Laptop) electricDevice).getHardDiskCapacity());
            } else {
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
    public boolean deleteByID(String id) {
        try (Connection connection = DatabaseConnection.getConnect()) {
            PreparedStatement preparedStatement = connection.prepareStatement(deleteByID);
            preparedStatement.setString(1, id);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public ElectricDevice findById(String productCode) {
        try (Connection connection = DatabaseConnection.getConnect()) {
            PreparedStatement preparedStatement = connection.prepareStatement(findByProductCode);
            preparedStatement.setString(1, productCode);
            ResultSet rs = preparedStatement.executeQuery();
            List<ElectricDevice> electricDevices = setData(rs);
            if (electricDevices.size() == 0)
                return null;
            return electricDevices.get(0);
        } catch (SQLException ex) {
            return null;
        }

    }

    @Override
    public boolean edit(ElectricDevice electricDevice) {
        try (Connection connection = DatabaseConnection.getConnect()) {
            PreparedStatement preparedStatement = connection.prepareStatement(updateSQL);

            preparedStatement.setString(1, electricDevice.getName());
            preparedStatement.setString(2, electricDevice.getBrand());
            preparedStatement.setString(3, electricDevice.getModel());
            preparedStatement.setDouble(4, electricDevice.getSalePrice());
            preparedStatement.setDouble(5, electricDevice.getImportPrice());
            preparedStatement.setInt(6, electricDevice.getQuantity());


            if (electricDevice instanceof SmartPhone) {
                preparedStatement.setDouble(7, ((SmartPhone) electricDevice).getWidth());
                preparedStatement.setDouble(8, ((SmartPhone) electricDevice).getHeight());
                preparedStatement.setInt(9, ((SmartPhone) electricDevice).getBatteryLife());
                preparedStatement.setDouble(10, ((SmartPhone) electricDevice).getResolution());
                preparedStatement.setString(11, "");
                preparedStatement.setInt(12, 0);
                preparedStatement.setInt(13, 0);

            } else if (electricDevice instanceof Laptop) {
                preparedStatement.setFloat(7, 0);
                preparedStatement.setFloat(8, 0);
                preparedStatement.setInt(9, 0);
                preparedStatement.setFloat(10, 0);
                preparedStatement.setString(11, ((Laptop) electricDevice).getCpu());
                preparedStatement.setInt(12, ((Laptop) electricDevice).getRam());
                preparedStatement.setInt(13, ((Laptop) electricDevice).getHardDiskCapacity());
            } else {
                return false;
            }
            preparedStatement.setString(14, electricDevice.getProductCode());
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
