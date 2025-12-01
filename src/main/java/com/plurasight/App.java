package com.plurasight;

import java.sql.*;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws SQLException {

        Scanner scanner = new Scanner(System.in);

        boolean run = true;

        while (run) {
            System.out.print("What do you want to do?\n" +
                    "1) Display all products\n" +
                    "2) Display all customers\n" +
                    "0) Exit\n" +
                    "Select an option:");
            scanner.nextLine();

            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    displayAllProducts();
                    break;
                case "2":
                    displayAllCustomers();
                    break;
                case "0":
                    System.out.println("Goodbye");
                    run = false;
                    break;
                default:
                    System.out.println("Please Enter 0, 1 or 2, Thank You!");
            }
        }
    }
    private static void displayAllProducts() throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

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
                System.out.println(results.getString("ProductID"));
                System.out.print("ProductName:   ");
                System.out.println(results.getString("ProductName"));
                System.out.print("UnitPrice:     ");
                System.out.println(results.getString("UnitPrice"));
                System.out.print("UnitsInStock:  ");
                System.out.println(results.getString("UnitsInStock"));
                System.out.println("_______________________________________________");
            }
            results.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) resultSet.close();
            if (preparedStatement != null) preparedStatement.close();
            if (connection != null) connection.close();
        }
    }

    private static void displayAllCustomers() throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/northwind",
                "root",
                "yearup");

        String query = "SELECT ContactName, CompanyName, City, Country, Phone FROM customers";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet results = statement.executeQuery();

            while (results.next()) {
                System.out.print("ContactName:     ");
                System.out.println(results.getString("ContactName"));
                System.out.print("CompanyName:   ");
                System.out.println(results.getString("CompanyName"));
                System.out.print("City:     ");
                System.out.println(results.getString("City"));
                System.out.print("Country:  ");
                System.out.println(results.getString("Country"));
                System.out.print("Phone:  ");
                System.out.println(results.getString("Phone"));
                System.out.println("_______________________________________________");
            }

            results.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) resultSet.close();
            if (preparedStatement != null) preparedStatement.close();
            if (connection != null) connection.close();
        }
    }
}
