/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.tags;

import Model.DB.Healthtip;
import java.io.IOException;
import java.util.List;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 *
 * @author Hashith
 */
public class getActivityHistory extends SimpleTagSupport {

    @Override
    public void doTag() throws JspException, IOException {
        Model.DB.User user = (Model.DB.User) getJspContext().getAttribute("user", PageContext.SESSION_SCOPE);
        List<Healthtip> healthTips = new Controller.actions.getDetails().getHealthTips(user.getIduser());
        String html = "";
        if (healthTips.isEmpty()) {
            html += "<p class=\"h4 text-danger\">No Record Founded<p>";
        } else {
            for (Healthtip tp : healthTips) {
                html += "<tr>";

                html += "<td>" + tp.getSugarrate().getDoctorHasPatient().getPatient().getFirstName() + "</td>";
                html += "<td>" + tp.getSugarrate().getSugarRate() + "</td>";
                html += "<td>" + tp.getSugarrate().getDate() + "</td>";
                html += "<td>" + tp.getTip() + "</td>";
                html += "<td>" + tp.getSendDate() + "</td>";
                html += "<td>Done</td>";
                html += tp.getIsRead() ? "<td class=\"text-success\" ><span class=\"glyphicon glyphicon-ok\"></span> Yes</td>" : "<td class=\"text-danger\"><span class=\"glyphicon glyphicon-remove\"></span> No</td>";
                
                html += "</tr>";
            }
        }
        getJspContext().getOut().write(html);
    }

}
