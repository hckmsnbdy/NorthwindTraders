package com.plurasight;

import java.sql.*;

public class App {
    public static void main(String[] args) throws SQLException {

        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/northwind",
                "root",
                "yearup");

        String query = "SELECT ProductID, ProductName, UnitPrice, UnitsInStock FROM products";

        try {
            PreparedStatement statement = connection.prepareStatement(query);

            ResultSet results = statement.executeQuery();

            while (results.next()) {
                System.out.print("ProductID:     ");
                System.out.println(results.getString( "ProductID"));
                System.out.print("ProductName:   ");
                System.out.println(results.getString( "ProductName"));
                System.out.print("UnitPrice:     ");
                System.out.println(results.getString( "UnitPrice"));
                System.out.print("UnitsInStock:  ");
                System.out.println(results.getString( "UnitsInStock"));
                System.out.println("-----------------------------");
            }

            results.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
