/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.lab.model;

import java.rmi.ServerException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.time.temporal.ChronoUnit;

/**
 *
 * @author Mateusz Cudzik
 * @version 2.0
 */
public class Model {

    private static Model instance;
    
    public static Model getInstance(){
        if (instance == null) {
            instance = new Model();
            
            //DEBUG
            //instance.initializeWithExample();
        }
        return instance;
    }
    public static final int MAX_NUM_OF_PARTICIPANTS = 14;
    
    private ArrayList<Hotel> hotels;
    private ArrayList<Destination> destinations;
    private ArrayList<Booking> bookings;
    
    private Connection connection;

    /**
     * Initializes all properties with empty lists
     */
    private Model() {
        this.hotels = new ArrayList<>();
        this.destinations = new ArrayList<>();
        this.bookings = new ArrayList<>();
        
        try {
            this.connection = DriverManager.getConnection("jdbc:derby://localhost:1527/baza", "app", "app");
            
            //Create tables
            Statement statement = this.connection.createStatement();
            
            if(!tableExists("DESTINATIONS")){
                statement.executeUpdate("CREATE TABLE Destinations "
                    + "(id INT GENERATED ALWAYS AS IDENTITY NOT NULL PRIMARY KEY, name VARCHAR(50))");
            }
            
            if(!tableExists("HOTELS")){
                statement.executeUpdate("CREATE TABLE Hotels "
                        + "(id INT GENERATED ALWAYS AS IDENTITY NOT NULL PRIMARY KEY, name VARCHAR(50), score FLOAT, price FLOAT, destinationID INTEGER, "
                        + "FOREIGN KEY (destinationID) REFERENCES Destinations(id))");
            }
            if(!tableExists("BOOKINGS")){
                statement.executeUpdate("CREATE TABLE Bookings "
                        + "(id INT GENERATED ALWAYS AS IDENTITY NOT NULL PRIMARY KEY, dateFrom DATE, dateTo DATE, hotelID INTEGER, participants INTEGER, prciePerPerson FLOAT,"
                        + " FOREIGN KEY (hotelID) REFERENCES Hotels(id))");
            }
            
       
            ResultSet rs = statement.executeQuery("SELECT * FROM Destinations");
            while (rs.next()) {
                var id = rs.getInt("id");
                var name = rs.getString("name");
                this.destinations.add(new Destination(name, id)); 
            }
            
            rs = statement.executeQuery("SELECT * FROM Hotels");
            while (rs.next()) {
                var id = rs.getInt("id");
                var name = rs.getString("name");
                var score = rs.getFloat("score");
                var price = rs.getFloat("price");
                var destId = rs.getInt("destinationID");
                
                this.hotels.add(new Hotel(name,this.getDestinationByID(destId),score,price,id)); 
            }
            
            rs.close();
            
        } 
        catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
        }
    }

    public ArrayList<Booking> getBookings() {
        return bookings;
    }
    
    public void initializeWithExample(){
        destinations.add(new Destination("Egipt", 1));
        destinations.add(new Destination("Turcja", 2));
        destinations.add(new Destination("Włochy", 3));
        hotels.add(new Hotel("Ibis", destinations.get(0), 3.1f, 100f, 1));
        hotels.add(new Hotel("Hilton", destinations.get(1), 5.0f, 200f, 2));
        hotels.add(new Hotel("Odysej", destinations.get(1), 3.0f, 100f, 3));
    }
    /**
     * gets all hotels in model
     *
     * @return list of all hotels
     */
    public ArrayList<Hotel> getHotels() {
        return hotels;
    }

    /**
     * gets all destinations in the model
     *
     * @return list of destinations
     */
    public ArrayList<Destination> getDestinations() {
        return destinations;
    }

    /**
     * adds Hotel to the list in model
     *
     * @param hotel hotel to add
     */
    public void addHotel(Hotel hotel) {
        var destination = hotel.getLocalization();
        boolean found = false;
        for(Destination dest : this.destinations){
            if(dest == destination){
                found = true;
                break;
            }
        }
        if(!found)
            throw new IllegalArgumentException("Destination doesn't exists");
        try{
            this.findhotelsByDestination(hotel.getLocalization());
            throw new IllegalArgumentException("Hotel already exists");
        }
        catch(NoHotelsInDestinationException exception){
            
        }
        if(hotel.getName() == null || hotel.getName().isBlank()){
            throw new IllegalArgumentException("Added hotel name null, empty or blank");
        }
        if(hotel.getPrice() < 0){
            throw new IllegalArgumentException("Added hotel price incorrect");
        }
        if(hotel.getScore() < 0 || hotel.getScore() > 10){
            throw new IllegalArgumentException("Added hotel score incorrect");
        }
        
        try {
            Statement statement = this.connection.createStatement();
            String tmp = "INSERT INTO Hotels(name, score, price, destinationID) VALUES (";
            tmp += hotel.getName() + ", ";
            tmp += String.valueOf(hotel.getScore()) + ", ";
            tmp += String.valueOf(hotel.getPrice()) + ", ";
            tmp += String.valueOf(hotel.getPrice()) + ", ";
            tmp += String.valueOf(hotel.getLocalization().getId());
            tmp += ")";
            statement.executeUpdate(tmp);
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
            throw new IllegalArgumentException("Server error couldn't add to database");
        }
        
        this.hotels.add(hotel);
    }

    /**
     * adds Destination to the list in model
     *
     * @param destination destination to add
     */
    public void addDestination(Destination destination) {
        for(Destination dest : this.destinations){
            if(dest == destination)
                throw new IllegalArgumentException("Destination already exists");
        }
        if(destination.getName() == null || destination.getName().isBlank())
            throw new IllegalArgumentException("Incorrect name");
        if(destination.getId() < 0)
            throw new IllegalArgumentException("Incorrect id");
        
        try {
            Statement statement = this.connection.createStatement();
            String tmp = "INSERT INTO Destinations(name) VALUES (";
            tmp += destination.getName() + ")";
            statement.executeUpdate(tmp);
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
            throw new IllegalArgumentException("Server error couldn't add to database");
        }
        this.destinations.add(destination);
    }

    /**
     * adds booking to the list in model
     * @param booking booking to add
     */
    public void addBooking(Booking booking) throws ServerException {
        this.bookings.add(booking);
        try {
            Statement statement = this.connection.createStatement();
            String tmp = "INSERT INTO Bookings(dateFrom, dateTo, hotelID, participants, prciePerPerson) VALUES (";
            tmp += "'" + booking.getDeparture().toString() + "', '";
            tmp += booking.getArrival() + "', ";
            tmp += String.valueOf(booking.getHotel().getId()) + ", ";
            tmp += String.valueOf(booking.getNumberOfParticipants()) + ", ";
            tmp += String.valueOf(booking.getPricePerPerson());
            tmp += ")";
            statement.executeUpdate(tmp);
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
            throw new ServerException("Server error couldn't add to database");
        }
    }

    /**
     * creates a booking object from parameters and adds it to the list
     * @param from beggining date of booking
     * @param to end date of booking
     * @param hotel hotel
     * @param numOfParticipants number of participants
     * @return created booking object
     */
    public Booking createBooking(Hotel hotel, int numOfParticipants, LocalDate from, LocalDate to) throws IllegalArgumentException, ServerException{
        if(from == null || to == null)
            throw new IllegalArgumentException("Dates are required");
        
        long daysApart = ChronoUnit.DAYS.between(from, to);
        if(daysApart > 14 || daysApart < 2)
            throw new IllegalArgumentException("Vacations can't be longer than 14 days and shorter than 2");
        
        if(from.isAfter(to) || from.isBefore(LocalDate.now()))
            throw new IllegalArgumentException("Dates are incorrect");
        
        if(numOfParticipants > MAX_NUM_OF_PARTICIPANTS || numOfParticipants < 1)
            throw new IllegalArgumentException("Number of participants can't be higher than " + MAX_NUM_OF_PARTICIPANTS + " and lower than 1" );
        
        Boolean hotelFound = false;
        for(Hotel h : this.hotels){
            if(hotel == h){
                hotelFound = true;
                break;
            }   
        }
        
        if(!hotelFound)
            throw new IllegalArgumentException("Hotel doesn't exists");
        
        Booking booking = new Booking(from, to, numOfParticipants, hotel, daysApart * hotel.getPrice());
        this.addBooking(booking);
        return booking;
    }
    
    /**
     * searches list of hotels that are in the destination
     *
     * @param dest destination in which to search
     * @return list of found hotels
     * @throws NoHotelsInDestinationException exception thath is thrown when no
     * hotels were found
     */
    public ArrayList<Hotel> findhotelsByDestination(Destination dest) throws NoHotelsInDestinationException {
        ArrayList<Hotel> found = new ArrayList<>();
        
        for(Hotel hotel : hotels){
            if(hotel.getLocalization().equals(dest))
                found.add(hotel);
        }
        
        if (found.size() == 0) {
            throw new NoHotelsInDestinationException("No hotels found");
        }

        return found;
    }
    
    public Destination getDestinationByID(int ID){
        for(Destination dest : this.destinations){
            if(dest.getId() == ID){
                return dest;
            }
        }
        
         throw new IllegalArgumentException("Destination doesn't exists");
    }
    
    public Hotel getHotelByID(int ID){
        for(Hotel hotel : this.hotels){
            if(hotel.getId() == ID){
                return hotel;
            }
        }
        
         throw new IllegalArgumentException("Hotel doesn't exists");
    }
    
    private boolean tableExists(String tableName) throws SQLException {
        DatabaseMetaData meta = this.connection.getMetaData();
        ResultSet resultSet = meta.getTables(null, null, tableName, new String[] {"TABLE"});

        return resultSet.next();
    }
}
