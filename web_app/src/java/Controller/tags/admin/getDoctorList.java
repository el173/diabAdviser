/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.tags.admin;

import Model.DB.Doctor;
import java.io.IOException;
import java.util.List;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 *
 * @author Hashith
 */
public class getDoctorList extends SimpleTagSupport {
    
    @Override
    public void doTag() throws JspException, IOException {
        List<Doctor> allDoctors = new Controller.actions.admin.getDetails().getAllDoctors();
        String html = "";
        if (allDoctors.isEmpty()) {
            html += "<p class=\"h4 text-danger\">No Record Founded<p>";
        } else {
            for (Doctor d : allDoctors) {
                html += "<tr>";
                
                html += "<td>" + d.getUserIduser() + "-" + d.getFirstName() + " " + d.getLastName() + "</td>";
                html += "<td>" + d.getUser().getEmal() + "</td>";
                html += "<td>" + d.getMobile() + "</td>";
                html += "<td>" + d.getBank().getBankName() + " Bank<br>" + d.getAccountName() + "<br>Acc.Num: " + d.getAccountNumber() + "</td>";
                html += "<td>" + d.getUser().getRegDate() + "</td>";
                html += "<td>" + (d.getUser().getStatus().getIduserStatus() == 1 ? ""
                        + "<label class=\"label label-success\">Active</label>" : ""
                        + "<label class=\"label label-warning\">Inactive</label>") + "</td>";
                html += "<td>" + new Controller.actions.admin.getDetails().getUnClaimedAmountByDoctor(d.getUserIduser()) + "</td>";
                html += "<td>" + (d.getUser().getStatus().getIduserStatus() != 1 ? ""
                        + "<button class=\"btn btn-success btn-sm\" onclick=\"activateAcc(" + d.getUserIduser() + ")\">Activate</button>" : ""
                        + "<button class=\"btn btn-danger btn-sm\" onclick=\"deactivateAcc(" + d.getUserIduser() + ")\">Deactivate</button>") + "</td>";
                
                html += "</tr>";
            }
        }
        System.gc();
        getJspContext().getOut().write(html);
    }
    
}
