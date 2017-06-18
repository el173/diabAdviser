/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Hashith
 */
@WebServlet(name = "contactSuperUser", urlPatterns = {"/contactSuperUser"})
public class contactSuperUser extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String subject = request.getParameter("msg_subject");
            String message = request.getParameter("msg_content");
            Model.DB.User user = (Model.DB.User) request.getSession().getAttribute("user");
            String output = "";
            String email_content = "<p>";
            email_content += "From : <br>User ID " + user.getIduser() + "<br>";
            email_content += "Name : " + user.getDoctor().getFirstName() + " " + user.getDoctor().getLastName() + "<br>";
            email_content += user.getEmal() + "<br><br>";
            email_content += "<b><h3>" + subject + "</h3></b>";
            email_content += "<p>" + message + "</p> <br><br>";
            email_content += "On : " + new Date();
            email_content += "</p>";
            try {
                boolean send = new Controller.emailSender().send(getServletContext().getInitParameter("email"), "diabAdviser Administrator Contact", email_content, Controller.emailSender.CONTACT_ADMIN);
                output = send ? "1" : "0";
            } catch (MessagingException ex) {
                output = "0";
            }
            System.gc();
            out.write(output);
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
