/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Servlet;

import Controller.actions.setDetails;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Hashith
 */
@WebServlet(name = "signup", urlPatterns = {"/signup"})
public class signup extends HttpServlet {

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

        String firstName = request.getParameter("first_name");
        String lastName = request.getParameter("last_name");
        String mobile = request.getParameter("mobile");
        String email = request.getParameter("email");
        if (new Controller.actions.user().checkUserAvailabilitybyEmail(email) != null) {
            response.sendRedirect("signup.jsp?msg=email");
        } else {
            int hospitalId = Integer.parseInt(request.getParameter("hospital"));
            int bankId = Integer.parseInt(request.getParameter("bank"));
            if (hospitalId == 0) {
//            response.sendRedirect(request.getHeader("referer"));
                response.sendRedirect("signup.jsp?msg=hospital");
            } else {
                if (bankId == 0) {
                    response.sendRedirect("signup.jsp?msg=bank");
                } else {
                    String accName = request.getParameter("account_name");
                    long accNumuber = Long.valueOf(request.getParameter("account_number"));
                    double tipAmount = Double.parseDouble(request.getParameter("tip_amount"));
                    String password = request.getParameter("password");
                    String passwordConfrm = request.getParameter("password_confirmation");
                    if (!password.equals(passwordConfrm)) {
                        response.sendRedirect("signup.jsp?msg=pass");
                    } else {
                        Controller.actions.setDetails setDetails = new setDetails();
                        if (setDetails.saveNewDoctor(email, password, firstName, lastName, mobile, hospitalId, tipAmount, accNumuber, accName, bankId)) {
                            request.getSession().setAttribute("user", setDetails.user);
                            response.sendRedirect("EmailConfrm.jsp");
                        } else {
                            response.sendRedirect("signup.jsp?msg=log");
                        }
                    }
                }
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
