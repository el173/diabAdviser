/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.tags;

import Model.DB.Sugarrate;
import java.io.IOException;
import java.util.List;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 *
 * @author Hashith
 */
public class getPatientMessages extends SimpleTagSupport {

    @Override
    public void doTag() throws JspException, IOException {
        Model.DB.User user = (Model.DB.User) getJspContext().getAttribute("user", PageContext.SESSION_SCOPE);
        List<Sugarrate> doctorsMessages = new Controller.actions.getDetails().getDoctorsMessages(user.getIduser());
        String html = "";
        if (doctorsMessages.isEmpty()) {
            html += "<p class=\"h4 text-danger\">No Record Founded<p>";
        } else {
            for (Sugarrate doctorsMessage : doctorsMessages) {
                if (!doctorsMessage.getIsRead()) {
                    html += "<tr class=\"text-primary gradeA\">";
                } else {
                    html += "<tr class=\"gradeA\">";
                }
                html += "<td >" + doctorsMessage.getDoctorHasPatient().getPatient().getFirstName() + "</td>";
                html += "<td>" + doctorsMessage.getSugarRate() + "</td>";
                html += "<td>" + doctorsMessage.getMessage() + "</td>";
                html += "<td>" + doctorsMessage.getDate() + "</td>";
                html += "<td><a id=\"msg_read\" href=\"#\" onclick=\"viewMessage('" + doctorsMessage.getIdsugarrate() + "')\"><b>Read</b></a></td>";
                html += new Controller.actions.getDetails().isReplyedForSugarRate(doctorsMessage.getIdsugarrate()) ? "<td class=\"text-success\" ><span class=\"glyphicon glyphicon-ok\"></span> Yes</td>" : "<td class=\"text-danger\"><span class=\"glyphicon glyphicon-remove\"></span> No</td>";
                html += "</tr>";
            }
        }
        getJspContext().getOut().write(html);
    }
}
