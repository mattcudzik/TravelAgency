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
import pl.polsl.lab.model.Model;
import pl.polsl.lab.model.NoHotelsInDestinationException;

/**
 *
 * @author Artur
 */
@WebServlet(name = "dataServlet", urlPatterns = {"/data"})
public class DataServlet extends HttpServlet {

    private Model model;
    
    @Override
    public void init() {
        model = Model.getInstance();
      //  super.init();
    }
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            
            throws ServletException, IOException {
          PrintWriter out = response.getWriter();
          
          String type = request.getParameter("type");
          String context = request.getParameter("context");
          var destinations = this.model.getDestinations();
          if(type.equals("destination")){
            
            for(var dest : destinations){
                out.println("<tr>");
                out.println("<td>");
                out.println(dest.getName());
                out.println("</td>");
                out.println("<td>");
                out.println("<input type='button' value='pick', id='" +dest.getId() +"' onclick=\"getHotels('table'," + dest.getId()+ ", '" + context + "');\"/>");
                out.println("</td>");
                out.println("</tr>");
            }
          }
          else if(type.equals("hotel")){
            int id = Integer.valueOf(request.getParameter("destID"));
            var dest = destinations.get(id-1);
            
            try{
                var hotels = model.findhotelsByDestination(dest);
                for(var hotel : hotels){
                    out.println("<tr>");
                    out.println("<td>");
                    out.println(hotel.getName());
                    out.println("</td>");
                    out.println("<td>");
                    out.println(hotel.getPrice());
                    out.println("</td>");
                    out.println("<td>");
                    out.println(hotel.getScore());
                    out.println("</td>");
                    out.println("<td>");
                    if(context.equals("browsing"))
                        out.println("<input type='button' value='pick' id='" + hotel.getId() +"' onclick=\"location.href='index.html';\"/>");
                    else
                        out.println("<input type='button' value='pick' id='" + hotel.getId() +"' onclick=\"showForm("+ hotel.getId() +")\"/>");
                    out.println("</td>");
                    out.println("</tr>");
                }
            }
            catch(NoHotelsInDestinationException ex){
                out.println(ex.getMessage());
            }
          }
          else if(type.equals("lastBooking")){
            String htmlResponse = "";
            
            htmlResponse += "(Data read from cookies) <br>";
            if (request.getCookies() != null) {
                for (Cookie cookie : request.getCookies()) {
                    htmlResponse += cookie.getName() + ": " + cookie.getValue() + "<br>";
                }
                htmlResponse += "<input type='button' value='back' onclick=\"location.href='index.html';\"/>";
                out.println(htmlResponse);
            }
            else{
                out.println("no bookings made yet." + "<input type='button' value='back' onclick=\"location.href='index.html';\"/>");
            }
          }
           
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
