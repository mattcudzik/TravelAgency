/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.lab.model;
import java.time.LocalDate;
/**
 * Class representing travel booking
 * @author Mateusz Cudzik
 * @version 1.0
 */
public class Booking {
    
    private LocalDate departure;
    private LocalDate arrival;
    private int numberOfParticipants;
    private Hotel hotel;
    private float pricePerPerson;

    /**
     * Creates Booking object
     * @param dearture beggining date of the booking
     * @param arrival end date of the booking
     * @param numberOfParticipants number of participants in booking
     * @param hotel booked hotel
     * @param pricePerPerson price per person fo the booking
     */
    public Booking(LocalDate departure, LocalDate arrival, int numberOfParticipants, Hotel hotel, float pricePerPerson) {
        this.departure = departure;
        this.arrival = arrival;
        this.numberOfParticipants = numberOfParticipants;
        this.hotel = hotel;
        this.pricePerPerson = pricePerPerson;
    }
    
    /**
     * gets price of booking per person
     * @return price of booking per person
     */
    public float getPricePerPerson() {
        return pricePerPerson;
    }

    /**
     * setes price of booking per person
     * @param pricePerPerson price to set
     */
    public void setPricePerPerson(float pricePerPerson) {
        this.pricePerPerson = pricePerPerson;
    }
    
    /**
     * gets begginging date of the booking
     * @return begginging date of the booking
     */
    public LocalDate getDeparture() {
        return departure;
    }

    /**
     * sets begginging date of the booking
     * @param departure date to set
     */
    public void setDeparture(LocalDate departure) {
        this.departure = departure;
    }

    /**
     * gets ending date of the booking
     * @return ending date of the booking
     */
    public LocalDate getArrival() {
        return arrival;
    }

    /**
     * sets ending date of the booking
     * @param arrival date to set
     */
    public void setArrival(LocalDate arrival) {
        this.arrival = arrival;
    }

    /**
     * gets number of participants
     * @return number of participants
     */
    public int getNumberOfParticipants() {
        return numberOfParticipants;
    }

    /**
     * sets number of participants
     * @param numberOfParticipants number to set
     */
    public void setNumberOfParticipants(int numberOfParticipants) {
        this.numberOfParticipants = numberOfParticipants;
    }

    /**
     * gets booked hotel
     * @return booked hotel
     */
    public Hotel getHotel() {
        return hotel;
    }

    /**
     * sets booked hotel
     * @param hotel booked hotel
     */
    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }
}
