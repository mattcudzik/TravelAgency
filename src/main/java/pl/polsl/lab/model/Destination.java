/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.lab.model;

/**
 * class representing travel destination
 * @author Mateusz Cudzik
 * @version 1.0
 */
public class Destination {
    private String name;
    private int id;

    /**
     * creates destination object
     * @param name name of the destination
     * @param id id of the destination
     */
    public Destination(String name, int id) {
        this.name = name;
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        
        if (!(obj instanceof Destination))
            return false;
        
        Destination d = (Destination) obj;

        return this.id == d.id;
    }

    @Override
    public int hashCode() {
        return super.hashCode(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    /**
     * gets ID of the destination
     * @return ID of the destination
     */
    public int getId() {
        return id;
    }

    /**
     * sets ID of the destination
     * @param id ID of the destination to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * gets name of the destination
     * @return name of the destination
     */
    public String getName() {
        return name;
    }

    /**
     * sets name of the destination
     * @param name name of the destination to set
     */
    public void setName(String name) {
        this.name = name;
    }
    
    
}
