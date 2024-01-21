package com.jdbcproject.thursdaylab;

import java.sql.*;
import java.util.Scanner;

public class App{
    public static void main( String[] args ){
     try{
         //load and register the driver
			Class.forName("com.mysql.cj.jdbc.Driver");
    				
    	  //Establish the connection
    	  //URL,username,password
    	  String url = "jdbc:mysql://localhost:3306/factory";
    	  String username = "root";
    	  String password = "root";
    	  // Create a connection to the database
    	  Connection con = DriverManager.getConnection(url, username, password);
    	  //Create query
    	  String q = "create table Employee(eid int primary key AUTO_INCREMENT, ename varchar(50) not null, city varchar(50),salary int)";
    	  //Create a statement
    	  Statement stmt = con.createStatement();
         //execute statement
    	  stmt.executeUpdate(q);
         //Create a Scanner for user input
    	 Scanner scanner = new Scanner(System.in);

    	 while (true) {
    	 System.out.println("\nEmployee Management System");
    	 System.out.println("1. Insert Employee");
         System.out.println("2. Update Employee");
    	 System.out.println("3. Delete Employee");
    	 System.out.println("4. View All Employees");
    	 System.out.println("5. Exit");
    	 System.out.print("Enter your choice: ");
    	 int choice = scanner.nextInt();

    	   switch (choice) {
    	   case 1:
    	   insertEmployee(con, scanner);
    	   break;
    	   case 2:
           updateEmployee(con, scanner);
    	   break;
    	   case 3:
           deleteEmployee(con, scanner);
    	   break;
    	   case 4:
    	   viewAllEmployees(con);
    	   break;
    	   case 5:
    	   con.close();
    	   scanner.close();
    	   System.exit(0);
    	   default:
           System.out.println("Invalid choice. Please enter a valid option.");
    	   }
    	   }
    	   } catch (Exception e) {
    	     e.printStackTrace();
    	     }
}

    	    private static void insertEmployee(Connection con, Scanner scanner) throws SQLException {
    	        System.out.print("Enter Employee Name: ");
    	        String ename = scanner.next();
    	        System.out.print("Enter City: ");
    	        String city = scanner.next();
    	        System.out.print("Enter Salary: ");
    	        double salary = scanner.nextDouble();

    	        String insert = "INSERT INTO Employee (ename, city, salary) VALUES (?, ?, ?)";
    	        PreparedStatement pstmt = con.prepareStatement(insert);
    	        pstmt.setString(1, ename);
    	        pstmt.setString(2, city);
    	        pstmt.setDouble(3, salary);
    	        pstmt.executeUpdate();
    	        System.out.println("Employee inserted successfully.");
    	    }

    	    private static void updateEmployee(Connection con, Scanner scanner) throws SQLException {
    	        System.out.print("Enter Employee ID to update: ");
    	        int eid = scanner.nextInt();
    	        System.out.print("Enter New Employee Name: ");
    	        String ename = scanner.next();
    	        System.out.print("Enter New City: ");
    	        String city = scanner.next();
    	        System.out.print("Enter New Salary: ");
    	        double salary = scanner.nextDouble();

    	        String update = "UPDATE Employee SET ename=?, city=?, salary=? WHERE eid=?";
    	        PreparedStatement pstmt = con.prepareStatement(update);
    	        pstmt.setString(1, ename);
    	        pstmt.setString(2, city);
    	        pstmt.setDouble(3, salary);
    	        pstmt.setInt(4, eid);
    	        int rowsAffected = pstmt.executeUpdate();
    	        
    	        if (rowsAffected > 0) {
    	            System.out.println("Employee updated successfully.");
    	        } else {
    	            System.out.println("Employee not found with the given ID.");
    	        }
    	    }

    	    private static void deleteEmployee(Connection con, Scanner scanner) throws SQLException {
    	        System.out.print("Enter Employee ID to delete: ");
    	        int eid = scanner.nextInt();

    	        String delete = "DELETE FROM Employee WHERE eid=?";
    	        PreparedStatement pstmt = con.prepareStatement(delete);
    	        pstmt.setInt(1, eid);
    	        int rowsAffected = pstmt.executeUpdate();

    	        if (rowsAffected > 0) {
    	            System.out.println("Employee deleted successfully.");
    	        } else {
    	            System.out.println("Employee not found with the given ID.");
    	        }
    	    }

    	    private static void viewAllEmployees(Connection con) throws SQLException {
    	        String select = "SELECT * FROM Employee";
    	        Statement stmt = con.createStatement();
    	        ResultSet resultSet = stmt.executeQuery(select);

    	        System.out.println("Employee List:");
    	        while (resultSet.next()) {
    	            int eid = resultSet.getInt("eid");
    	            String ename = resultSet.getString("ename");
    	            String city = resultSet.getString("city");
    	            double salary = resultSet.getDouble("salary");
    	            System.out.println("Employee ID: " + eid + ", Name: " + ename + ", City: " + city + ", Salary: " + salary);
    	        }
    	    }
    	}
