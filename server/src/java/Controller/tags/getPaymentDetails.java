/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.tags;

import Model.DB.Tippayment;
import java.io.IOException;
import java.util.List;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 *
 * @author Hashith
 */
public class getPaymentDetails extends SimpleTagSupport {

    @Override
    public void doTag() throws JspException, IOException {
        Model.DB.User user = (Model.DB.User) getJspContext().getAttribute("user", PageContext.SESSION_SCOPE);
        List<Tippayment> payments = new Controller.actions.getDetails().getPaymentdetailByDoctorId(user.getIduser());
        String html = "";
        if (payments.isEmpty()) {
            html += "<p class=\"h4 text-danger\">No Record Founded<p>";
        } else {

            html += "<p class=\"text-danger\"><span class=\"glyphicon glyphicon-hand-right\"></span> Note : Net Amount = (Amount - (Maintain Charge%))</p>\n";
            for (Tippayment p : payments) {
                html += "<tr>";

                html += "<td>" + p.getSugarrate().getDoctorHasPatient().getPatient().getFirstName() + "</td>";
                html += "<td>" + p.getPaidDate() + "</td>";
                html += "<td>" + p.getAmount() + "</td>";
                html += "<td>" + p.getMaintaincharge().getAdminCharge() + "% </td>";
                html += "<td>" + Controller.Tools.getNetAmount(p.getAmount(), p.getMaintaincharge().getAdminCharge()) + " </td>";
                html += p.getClaimedDate() != null ? "<td class=\"text-success\" ><span class=\"glyphicon glyphicon-ok\"></span> Yes</td>" : "<td class=\"text-danger\"><span class=\"glyphicon glyphicon-remove\"></span> No</td>";
                html += p.getClaimedDate() != null ? "<td>" + p.getClaimedDate() + "</td>" : "<td>N/A</td>";

                html += "</tr>";
            }
        }
        System.gc();
        getJspContext().getOut().write(html);
    }
}
