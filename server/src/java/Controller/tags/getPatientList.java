/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.tags;

import Model.DB.DoctorHasPatient;
import java.io.IOException;
import java.util.List;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 *
 * @author Hashith
 */
public class getPatientList extends SimpleTagSupport {

    @Override
    public void doTag() throws JspException, IOException {
        Model.DB.User user = (Model.DB.User) getJspContext().getAttribute("user", PageContext.SESSION_SCOPE);
        List<Model.DB.DoctorHasPatient> doctorHasPatient = new Controller.actions.getDetails().getPatientByDoctor(user.getIduser());
        String html = "";
        if (doctorHasPatient.isEmpty()) {
            html += "<p class=\"h4 text-danger\">No Record Founded<p>";
        } else {
            for (DoctorHasPatient dhp : doctorHasPatient) {
                html += "<tr>";
                html += "<td>" + dhp.getPatient().getFirstName() + "</td>";
                html += "<td>" + dhp.getPatient().getMobile() + "</td>";
                html += "<td>" + dhp.getPatient().getUser().getEmal() + "</td>";
                html += "<td>" + dhp.getStartDate() + "</td>";
                String span = dhp.getStatus().getIduserStatus() == 1 ? "<span class=\"label label-success\">" : "<span class=\"label label-warning\">";
                html += "<td>" + span + dhp.getStatus().getUserStatus() + "</span></td>";
                html += "</tr>";
            }
        }
        getJspContext().getOut().write(html);
    }
}
