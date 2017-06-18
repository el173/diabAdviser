/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.tags.admin;

import Model.DB.Tippayment;
import java.io.IOException;
import java.util.List;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 *
 * @author Hashith
 */
public class getAllPayment extends SimpleTagSupport {

    @Override
    public void doTag() throws JspException, IOException {
        List<Tippayment> allPayment = new Controller.actions.admin.getDetails().getAllPayment();
        String html = "";
        if (allPayment.isEmpty()) {
            html += "<p class=\"h4 text-danger\">No Record Founded<p>";
        } else {
            for (Tippayment p : allPayment) {
                html += "<tr>";

                html += "<td>" + p.getSugarrate().getDoctorHasPatient().getPatient().getUserIduser() + "-" + p.getSugarrate().getDoctorHasPatient().getPatient().getFirstName() + "</td>";
                html += "<td>" + p.getSugarrate().getDoctorHasPatient().getDoctor().getFirstName() + " " + p.getSugarrate().getDoctorHasPatient().getDoctor().getLastName() + "</td>";
                html += "<td>" + p.getAmount() + "</td>";
                html += "<td>" + p.getPaidDate() + "</td>";
                html += "<td>" + p.getMaintaincharge().getAdminCharge() + "%</td>";
                html += "<td>" + Controller.Tools.getNetAmount(p.getAmount(), p.getMaintaincharge().getAdminCharge()) + "</td>";
                html += "<td>" + (p.getClaimedDate() != null ? "Yes" : "No") + "</td>";
                html += "<td>" + (p.getClaimedDate() != null ? p.getClaimedDate() : "N/A") + "</td>";
                html += "<td>" + (p.getClaimedDate() != null ? ""
                        + "<button class=\"btn btn-warning btn-sm disabled\">Claimed</button>" : ""
                        + "<button class=\"btn btn-success btn-sm\" onclick=\"claim(" + p.getIdtipPayment() + ")\">Claim</button>")
                        + "</td>";

                html += "</tr>";
            }
        }
        System.gc();
        getJspContext().getOut().write(html);
    }

}
