/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.lab.model;

/**
 * class representing a hotel
 * @author Mateusz Cudzik
 * @version 1.0
 */
public class Hotel {
    private String name;
    private Destination localization;
    private float score;
    private float price;
    private int id;

    public int getId() {
        return id;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    /**
     * creates object of the hotel
     * @param name name of the hotel
     * @param localization destination in which hotel is localized
     * @param score score of the hotel
     * @param price price per night per person
     * @parm id id of the Hotel
     */
    public Hotel(String name, Destination localization, float score, float price, int id) {
        this.name = name;
        this.localization = localization;
        this.score = score;
        this.price = price;
        this.id = id;
    }

    /**
     * gets name of the hotel
     * @return name of the hotel
     */
    public String getName() {
        return name;
    }

    /**
     * sets name of the hotel
     * @param name name of the hotel
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * gets localization of the hotel
     * @return localization of the hotel
     */
    public Destination getLocalization() {
        return localization;
    }

    /**
     * sets localization of the hotel
     * @param localization localization of the hotel
     */
    public void setLocalization(Destination localization) {
        this.localization = localization;
    }

    /**
     * gets score of the hotel
     * @return score of the hotel
     */
    public float getScore() {
        return score;
    }

    /**
     * sets score of the hotel
     * @param score score of the hotel
     */
    public void setScore(float score) {
        this.score = score;
    }
    
}
