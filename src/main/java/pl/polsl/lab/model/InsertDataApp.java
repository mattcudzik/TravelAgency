package pl.polsl.lab.model;

import java.sql.*;

public class InsertDataApp {

    public void insertData() {
        
        // make a connection to DB
        try (Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/baza", "app", "app")) {
            Statement statement = con.createStatement();
            statement.executeUpdate("INSERT INTO Destinations VALUES (1, 'Egypt')");
            statement.executeUpdate("INSERT INTO Destinations VALUES (2, 'Turkey')");
            statement.executeUpdate("INSERT INTO Destinations VALUES (3, 'Italy')");
            
            statement.executeUpdate("INSERT INTO Hotels VALUES (1, 'Ibis', 3.1, 100, 1)");
            statement.executeUpdate("INSERT INTO Hotels VALUES (2, 'Hilton', 5.0, 200, 2)");
            statement.executeUpdate("INSERT INTO Hotels VALUES (3, 'Odyssey', 3.0, 100, 2)");
            
            System.out.println("Data inserted");
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
        }
    }

    public static void main(String[] args) {
        InsertDataApp insertDataApp = new InsertDataApp();
        insertDataApp.insertData();
    }
}
