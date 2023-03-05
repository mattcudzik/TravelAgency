/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.lab.model;

/**
 * class representing exception that is thrown when no hotels were found in particular destination
 * @author Mateusz Cudzik
 * @version 1.0
 */
public class NoHotelsInDestinationException extends Exception{
    public NoHotelsInDestinationException(String message) {
        super(message);
    }
}
