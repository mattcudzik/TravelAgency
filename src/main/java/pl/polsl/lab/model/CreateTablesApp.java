package pl.polsl.lab.model;

import java.sql.*;

public class CreateTablesApp {

    public void createTables() {

        // make a connection to DB
        try (Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/baza", "app", "app")) {
            Statement statement = con.createStatement();
            statement.executeUpdate("CREATE TABLE Destinations "
                    + "(id INTEGER PRIMARY KEY, name VARCHAR(50))");
            
            statement.executeUpdate("CREATE TABLE Hotels "
                    + "(id INTEGER PRIMARY KEY, name VARCHAR(50), score FLOAT, price FLOAT, destinationID INTEGER, "
                    + "FOREIGN KEY (destinationID) REFERENCES Destinations(id))");
            
            statement.executeUpdate("CREATE TABLE Bookings(id INTEGER PRIMARY KEY, dateFrom DATE, dateTo DATE, hotelID INTEGER, participants INTEGER, prciePerPerson FLOAT,"
                    + " FOREIGN KEY (hotelID) REFERENCES Hotels(id))");
            
            System.out.println("Table created");
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
        }
    }

    public static void main(String[] args) {
        CreateTablesApp createTablesApp = new CreateTablesApp();
        createTablesApp.createTables();
    }
}
