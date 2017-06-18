/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Servlet;

import Model.DB.Sugarrate;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Hashith
 */
@WebServlet(name = "getMessageDetails", urlPatterns = {"/getMessageDetails"})
public class getMessageDetails extends HttpServlet {

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
            int messageId = Integer.parseInt(request.getParameter("msgId"));
            Sugarrate messageDetail = new Controller.actions.getDetails().getMessageDetail(messageId);
            String messageContent = "";
            String reply = "";
            if (messageDetail != null) {
                if (!messageDetail.getIsRead()) {
                    new Controller.actions.setDetails().markAsReadSugarRate(messageId);
                }
                if (new Controller.actions.getDetails().isReplyedForSugarRate(messageId)) {
                    reply += "disabled=\"\"";
                }
                messageContent = "<div id=\"msg_read_area\">\n"
                        + "            <br>\n"
                        + "            <div id=\"page-wrapper\" class=\"panel panel-default\">\n"
                        + "                <div class=\"row\">\n"
                        + "                    <div class=\"col-lg-6\">\n"
                        + "                        <h1 class=\"page-header\">Message Details</h1>\n"
                        + "                    </div>\n"
                        + "                </div>\n"
                        + "                <div class=\"row\">\n"
                        + "                    <div class=\"col-lg-6\">\n"
                        + "                        <h4 class=\"msg-content\">Patient Name : <label class=\"msg-content\">" + messageDetail.getDoctorHasPatient().getPatient().getFirstName() + "</label></h4>\n"
                        + "                    </div>\n"
                        + "                    <div class=\"col-lg-3\">\n"
                        + "                        <h4 class=\"msg-content\">Date : <label class=\"msg-content\">" + messageDetail.getDate() + "</label></h4>\n"
                        + "                    </div>\n"
                        + "                    <div class=\"col-lg-3\">\n"
                        + "                        <h4 class=\"msg-content\">Payment : <label class=\"msg-content\">Done</label></h4>\n"
                        + "                    </div>\n"
                        + "                </div>\n"
                        + "                <div class=\"row\">\n"
                        + "                    <div class=\"col-lg-12\">\n"
                        + "                        <label class=\"h3\">Blood Sugar Rate(mg/dL) : <span class=\"badge\">" + messageDetail.getSugarRate() + "</span></label>\n"
                        + "                    </div>\n"
                        + "                </div>\n"
                        + "                <div class=\"row\">\n"
                        + "                    <div class=\"col-lg-12\">\n"
                        + "                        <p>Optional Message</p>\n"
                        + "                        <textarea class=\"form-control msg-content\" rows=\"4\" disabled=\"\">" + messageDetail.getMessage() + "</textarea>\n"
                        + "                    </div>\n"
                        + "                </div>\n"
                        + "                <div class=\"row\">\n"
                        + "                    <div class=\"col-lg-12\">\n"
                        + "                        <p class=\"h4 text-primary\">Give health advise for this patient.</p>\n"
                        + "                    </div>\n"
                        + "                </div>\n"
                        + "                <div class=\"row\">\n"
                        + "                    <div class=\"col-lg-12\">\n"
                        + "                        <textarea class=\"form-control\" id=\"msg_reply\" rows=\"6\" " + reply + "></textarea>\n"
                        + "                    </div>\n"
                        + "                </div>\n"
                        + "                <input type=\"hidden\" value=\"" + messageDetail.getIdsugarrate() + "\" id=\"msg_id\">\n"
                        + "                <div class=\"row\">\n"
                        + "                    <div class=\"col-lg-offset-10 col-lg-12\">\n"
                        + "                        <p class=\"text-muted\">(max 450 characters)</p>\n"
                        + "                        <button id=\"reply\" onclick=\"reply()\" class=\"btn btn-success\" " + reply + ">Send Health Advise</button>\n"
                        + "                    </div>\n"
                        + "                </div>\n"
                        + "                <br>\n"
                        + "            </div>\n"
                        + "        </div>";
            }
            System.gc();
            out.write(messageContent);
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
