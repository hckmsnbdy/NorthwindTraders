package com.plurasight;

import com.sun.jdi.connect.Connector;

import java.sql.*;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws SQLException {

        try (Scanner scanner = new Scanner(System.in);

        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/northwind",
                args[0],
                args[1])) {

            boolean run = true;

            while (run) {
                System.out.println();
                System.out.print("What do you want to do?\n" +
                        "1) Display all products\n" +
                        "2) Display all customers\n" +
                        "3) Display all categories\n" +
                        "0) Exit\n" +
                        "Select an option:");

                String input = scanner.nextLine();

                switch (input) {
                    case "1":
                        displayAllProducts(connection);
                        break;
                    case "2":
                        displayAllCustomers(connection);
                        break;
                    case "3":
                        displayAllCategories(connection,scanner);
                        break;
                    case "0":
                        System.out.println("Goodbye");
                        run = false;
                        connection.close();
                        break;
                    default:
                        System.out.println("Please Enter 0, 1, 2 or 3, Thank You!");
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    private static void displayAllProducts( Connection connection) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String query = "SELECT ProductID, ProductName, UnitPrice, UnitsInStock FROM products";

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet results = statement.executeQuery()){

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
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) resultSet.close();
            if (preparedStatement != null) preparedStatement.close();
        }
    }

    private static void displayAllCustomers( Connection connection ) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String query = "SELECT ContactName, CompanyName, City, Country, Phone FROM customers";

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet results = statement.executeQuery()){

            while (results.next()) {
                System.out.print("ContactName: ");
                System.out.println(results.getString("ContactName"));
                System.out.print("CompanyName: ");
                System.out.println(results.getString("CompanyName"));
                System.out.print("City:        ");
                System.out.println(results.getString("City"));
                System.out.print("Country:     ");
                System.out.println(results.getString("Country"));
                System.out.print("Phone:       ");
                System.out.println(results.getString("Phone"));
                System.out.println("_______________________________________________");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) resultSet.close();
            if (preparedStatement != null) preparedStatement.close();
        }
    }
    private static void displayAllCategories( Connection connection,Scanner scanner) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String query = "SELECT CategoryID, CategoryName FROM categories ORDER BY CategoryID";

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet results = statement.executeQuery()){

            while (results.next()) {
                System.out.println();
                System.out.print("CategoryID:     ");
                System.out.println(results.getString("CategoryID"));
                System.out.print("CategoryName:   ");
                System.out.println(results.getString("CategoryName"));
                System.out.println("_______________________________________________");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.print("Enter a CategoryID to see its products: ");
        String input = scanner.nextLine();
        try {
            int categoryId = Integer.parseInt(input);
            displayProductsByCategory(connection, categoryId);
        }catch (NumberFormatException e){
            System.out.println("Please enter a valid number");
        }
        finally {
            if (resultSet != null) resultSet.close();
            if (preparedStatement != null) preparedStatement.close();
        }
    }
    private static void displayProductsByCategory(Connection connection, int categoryId) {
        String query = "SELECT ProductID, ProductName, UnitPrice, UnitsInStock " + "FROM products " + "WHERE CategoryID = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, categoryId);

            try (ResultSet results = statement.executeQuery()) {

                boolean any = false;
                while (results.next()) {
                    any = true;
                    System.out.print("ProductID:     ");
                    System.out.println(results.getInt("ProductID"));
                    System.out.print("ProductName:   ");
                    System.out.println(results.getString("ProductName"));
                    System.out.print("UnitPrice:     ");
                    System.out.println(results.getBigDecimal("UnitPrice"));
                    System.out.print("UnitsInStock:  ");
                    System.out.println(results.getInt("UnitsInStock"));
                    System.out.println("_______________________________________________");
                }

                if (!any) {
                    System.out.println("No products found for this category.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
