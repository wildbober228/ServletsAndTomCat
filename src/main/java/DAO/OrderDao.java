package DAO;

import models.Customer;
import models.Good;
import models.Order;
import utils.ConnectionData;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderDao {

    public void addOrder(Date orderDate, int FK_customer, int FK_good) throws SQLException {
        try{
            Connection con = DriverManager.getConnection(ConnectionData.URL, ConnectionData.USER, ConnectionData.PASS);
            String sql = "INSERT INTO shop_bd1.orders (id, customerId, goodId, createdAt) VALUES (?, ?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, 0);
            pst.setInt(2, FK_customer);
            pst.setInt(3, FK_good);
            java.sql.Date sqlDate = new java.sql.Date(orderDate.getTime());
            pst.setDate(4, sqlDate);
            pst.executeUpdate();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void updateOrder(Date orderDate, int FK_customer, int FK_good,int idOrder){
        try{
            Connection con = DriverManager.getConnection(ConnectionData.URL, ConnectionData.USER, ConnectionData.PASS);
            String sql = "UPDATE shop_bd1.orders SET customerId = ?, goodId = ?, createdAt = ? WHERE id = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, FK_customer);
            pst.setInt(2, FK_good);
            pst.setDate(3, (java.sql.Date) orderDate);
            pst.setInt(4, idOrder);
            pst.executeUpdate();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public List<Order> list() throws SQLException {
        List<Order> listOrders = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(ConnectionData.URL, ConnectionData.USER, ConnectionData.PASS)) {
            String sql = "SELECT * FROM shop_bd1.orders";

            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery(sql);
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int column_count = rsmd.getColumnCount();
            while (resultSet.next()) {
                Order order = new Order(resultSet.getInt(1),resultSet.getInt(2),resultSet.getInt(3),resultSet.getDate(4));
                listOrders.add(order);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }

        return listOrders;
    }

    public Order getById(int _id) throws SQLException {
        Order order = null;
        try (Connection connection = DriverManager.getConnection(ConnectionData.URL, ConnectionData.USER, ConnectionData.PASS)) {
            String sql = "SELECT * FROM shop_bd1.customers WHERE id = " + _id;
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {
                int id = result.getInt("id");
                int FK_customer = result.getInt(2);
                int FK_good = result.getInt(3);
                java.sql.Date date = result.getDate(4);
                order = new Order(id, FK_customer, FK_good, date);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
        return order;
    }

    public Customer getCustomer(int idOrder) throws SQLException {
        Customer customer = null;
        try (Connection connection = DriverManager.getConnection(ConnectionData.URL, ConnectionData.USER, ConnectionData.PASS)) {
            String sql = " SELECT CreatedAt,shop_bd1.customers.id,nameCustomer,surname FROM shop_bd1.orders  " +
                          " JOIN customers ON shop_bd1.customers.id = shop_bd1.orders.customerId";
            //String sql = "SELECT nameCustomer, surname, discountId FROM shop_bd1.orders ON JOIN customers ON shop_bd1.customers.id = customerId WHERE shop_bd1.orders.id =" + idOrder;
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {
                int id = result.getInt(2);
                String name = result.getString(3);
                String surName = result.getString(4);
                //int FK_discount = result.getInt(4);
                customer = new Customer(id, name,surName,1);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
        return customer;
    }

    public Good getGood(int idOrder) throws SQLException {
        Good good = null;
        try (Connection connection = DriverManager.getConnection(ConnectionData.URL, ConnectionData.USER, ConnectionData.PASS)) {
            String sql = " SELECT shop_bd1.goods.id,goodName,goodPrice FROM shop_bd1.orders  " +
                    " JOIN goods ON shop_bd1.goods.id = shop_bd1.orders.goodId";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {
                int id = result.getInt(1);
                String name = result.getString(2);
                float price = result.getFloat(3);
                good = new Good(id, name,1,4);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
        return good;
    }



}
