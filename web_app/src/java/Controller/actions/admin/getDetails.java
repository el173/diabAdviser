/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.actions.admin;

import Model.DB.Tippayment;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Hashith
 */
public class getDetails {

    Session session = Controller.Tools.getSession();

    /**
     *
     * @return All Payment Details
     */
    public List<Model.DB.Tippayment> getAllPayment() {
        return session.createCriteria(Model.DB.Tippayment.class).list();
    }

    /**
     *
     * @return All registered doctors list
     */
    public List<Model.DB.Doctor> getAllDoctors() {
        return session.createCriteria(Model.DB.Doctor.class).list();
    }

    /**
     *
     * @return All registered patients list
     */
    public List<Model.DB.Patient> getAllPatients() {
        return session.createCriteria(Model.DB.Patient.class).list();
    }

    /**
     *
     * @param patientId
     * @return DoctorHasPatient Object
     */
    public Model.DB.DoctorHasPatient getDoctorNameByPatient(int patientId) {
        Criteria c = session.createCriteria(Model.DB.DoctorHasPatient.class);
        c.add(Restrictions.and(Restrictions.eq("patient", (Model.DB.Patient) session.load(Model.DB.Patient.class, patientId)), Restrictions.eq("status", (Model.DB.Status) session.load(Model.DB.Status.class, 1))));
        Model.DB.DoctorHasPatient doctorHasPatient = (Model.DB.DoctorHasPatient) c.uniqueResult();
        return doctorHasPatient;
    }

    /**
     *
     * @param doctorId
     * @return Amount to be claimed
     */
    public double getUnClaimedAmountByDoctor(int doctorId) {
        double amount = 0.0;
        Criteria c = session.createCriteria(Model.DB.Tippayment.class);
        c.createAlias("sugarrate", "sg");
        c.createAlias("sg.doctorHasPatient", "dhp");
        c.createAlias("dhp.doctor", "d");
        c.add(Restrictions.eq("d.userIduser", doctorId));
        c.add(Restrictions.eqOrIsNull("claimedDate", null));

        List<Model.DB.Tippayment> tippayments = c.list();
        if (!tippayments.isEmpty()) {
            for (Tippayment t : tippayments) {
                amount += Controller.Tools.getNetAmount(t.getAmount(), t.getMaintaincharge().getAdminCharge());
            }
        }
        return amount;
    }

//    public static void main(String[] args) {
//        getDetails d = new getDetails();
//        System.out.println(d.getUnClaimedAmountByDoctor(3));
//    }
}
