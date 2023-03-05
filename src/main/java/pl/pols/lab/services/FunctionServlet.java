/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.pols.lab.services;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import pl.polsl.lab.model.Booking;
import pl.polsl.lab.model.Hotel;
import pl.polsl.lab.model.Model;
        
@WebServlet(name = "functionServlet", urlPatterns = {"/function"})
public class FunctionServlet extends HttpServlet {
    
    private Model model;
    
    @Override
    public void init() {
        model = Model.getInstance();
      //  super.init();
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try{
            String dateFromPar = request.getParameter("dateFrom");
            if(dateFromPar.isBlank() || dateFromPar == null){
                throw new IOException("empty or incorrect data");
            }
            LocalDate fromDate = LocalDate.parse(dateFromPar);
            
            String dateToPar = request.getParameter("dateTo");
            if(dateToPar.isBlank() || dateToPar == null){
                throw new IOException("empty or incorrect data");
            }
            LocalDate toDate = LocalDate.parse(dateToPar);
            
            String numPar = request.getParameter("numOfParticipants");
            if(numPar.isBlank() || numPar == null){
                throw new IOException("empty or incorrect data");
            }
            int numOfParticipants = Integer.valueOf(numPar);
            
            int hotelID = Integer.valueOf(request.getParameter("hotelID"));
            
            PrintWriter out = response.getWriter();
            String htmlResponse = "";

            try{
                Hotel hotel = model.getHotelByID(hotelID);
                Booking booking = model.createBooking(hotel, numOfParticipants, fromDate, toDate);
                
                
                response.addCookie(new Cookie("hotelName", hotel.getName()));
                response.addCookie(new Cookie("from", String.valueOf(fromDate)));
                response.addCookie(new Cookie("to", String.valueOf(toDate)));
                response.addCookie(new Cookie("numberOfParticipants", String.valueOf(numOfParticipants)));
                response.addCookie(new Cookie("price", String.valueOf(booking.getPricePerPerson()* booking.getNumberOfParticipants())));
                
                htmlResponse += "<h1>Success!</h1><br>Hotel: " + hotel.getName() + "<br>" +
                        "From: " + fromDate + "<br> To: " + toDate + 
                        "<br> Price: " + booking.getPricePerPerson()* booking.getNumberOfParticipants();
                htmlResponse += "<br><input type='button' value='back' onclick=\"location.href='index.html';\"/>";
            }
            catch(Exception ex){
                htmlResponse += ex.getMessage();
                htmlResponse += "<br><input type='button' value='back' onclick=\"location.href='index.html';\"/>";
            }
            out.println(htmlResponse);
        }
        catch(IOException ex){
            PrintWriter out = response.getWriter();
            out.println(ex.getMessage() + "<br><input type='button' value='back' onclick=\"location.href='index.html';\"/>");
        }
        
        
        
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
