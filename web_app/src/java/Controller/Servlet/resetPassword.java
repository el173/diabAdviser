/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Servlet;

import Controller.actions.user;
import Controller.emailSender;
import Controller.encrypter;
import Model.DB.User;
import java.io.IOException;
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
@WebServlet(name = "resetPassword", urlPatterns = {"/resetPassword"})
public class resetPassword extends HttpServlet {

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
        String email = request.getParameter("email");
        String outout = "";
        Controller.actions.user userActions = new user();
        User checkedUser = userActions.checkUserAvailabilitybyEmail(email);
        if (checkedUser != null) {
            try {
                Controller.emailSender sender = new emailSender();
                Controller.encrypter en = new encrypter();
                String encryptedUserId = en.encrypt(String.valueOf(checkedUser.getIduser()));
                String link = "http://localhost:8080/diabAdviser/passwordReset.jsp?value=" + encryptedUserId;
                boolean send = sender.send(checkedUser.getEmal(), "diabAdviser PASSWORD RESET", link, emailSender.PASSWORD_RECOVERY);
                if (send == true) {
                    outout = "0";
                } else {
                    outout = "1";
                }
            } catch (MessagingException ex) {
                outout = "1";
            } catch (Exception ex) {
            }
        } else {
            outout = "2";
        }
        response.sendRedirect("forget_password.jsp?msg=" + outout);
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
