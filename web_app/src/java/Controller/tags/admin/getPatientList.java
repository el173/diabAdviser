/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.tags.admin;

import Model.DB.DoctorHasPatient;
import Model.DB.Patient;
import java.io.IOException;
import java.util.List;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 *
 * @author Hashith
 */
public class getPatientList extends SimpleTagSupport {

    @Override
    public void doTag() throws JspException, IOException {
        List<Patient> allPatients = new Controller.actions.admin.getDetails().getAllPatients();
        String html = "";
        if (allPatients.isEmpty()) {
            html += "<p class=\"h4 text-danger\">No Record Founded<p>";
        } else {
            for (Patient p : allPatients) {
                html += "<tr>";

                html += "<td>" + p.getUserIduser() + "-" + p.getFirstName() + "</td>";
                html += "<td>" + p.getUser().getEmal() + "</td>";
                html += "<td>" + p.getMobile() + "</td>";
                DoctorHasPatient doctorNameByPatient = new Controller.actions.admin.getDetails().getDoctorNameByPatient(p.getUserIduser());
                html += "<td>" + (doctorNameByPatient == null ? "N/A"
                        : doctorNameByPatient.getDoctor().getUserIduser() + "-" + doctorNameByPatient.getDoctor().getFirstName() + " " + doctorNameByPatient.getDoctor().getLastName())
                        + "</td>";
                html += "<td>" + p.getUser().getRegDate() + "</td>";
                html += "<td>" + (p.getUser().getStatus().getIduserStatus() == 1 ? ""
                        + "<label class=\"label label-success\">Active</label>" : ""
                        + "<label class=\"label label-warning\">Inactive</label>") + "</td>";
                html += "<td>" + (p.getUser().getStatus().getIduserStatus() != 1 ? ""
                        + "<button class=\"btn btn-success btn-sm\" onclick=\"activateAcc(" + p.getUserIduser() + ")\">Activate</button>" : ""
                        + "<button class=\"btn btn-danger btn-sm\" onclick=\"deactivateAcc(" + p.getUserIduser() + ")\">Deactivate</button>") + "</td>";

                html += "</tr>";
            }
        }
        System.gc();
        getJspContext().getOut().write(html);
    }

}
