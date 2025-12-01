package com.plurasight;

import java.sql.*;

public class App {
    public static void main(String[] args) throws SQLException {

        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/northwind",
                "root",
                "yearup");

        Statement statement = connection.createStatement();

        String query = "SELECT*FROM products WHERE UnitsOnOrder > '0' ";

        ResultSet results = statement.executeQuery(query);

        while (results.next()) {
            String name = results.getString("ProductName");
            System.out.println(name);
        }

        connection.close();


    }
}
