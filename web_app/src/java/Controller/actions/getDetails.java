/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.actions;

import Controller.encrypter;
import Model.DB.Doctor;
import Model.DB.DoctorHasPatient;
import Model.DB.Hospital;
import Model.DB.Sugarrate;
import Model.DB.Tippayment;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Hashith
 */
public class getDetails {

    Session session = Controller.Tools.getSession();

    /**
     *
     * @return Hospital Array
     */
    public ArrayList<Model.DB.Hospital> getHospitalList() {
        return (ArrayList<Hospital>) session.createCriteria(Model.DB.Hospital.class).list();
    }

    /**
     *
     * @return Bank Array
     */
    public ArrayList<Model.DB.Bank> getBankList() {
        return (ArrayList<Model.DB.Bank>) session.createCriteria(Model.DB.Bank.class).list();
    }

    /**
     *
     * @return Activated Maintain Cost
     */
    public double getMaintainCost() {
        Criteria criteria = session.createCriteria(Model.DB.Maintaincharge.class);
        criteria.add(Restrictions.eq("status", session.load(Model.DB.Status.class, 1)));
        Model.DB.Maintaincharge maintaincharge = (Model.DB.Maintaincharge) criteria.uniqueResult();
        return maintaincharge != null ? maintaincharge.getAdminCharge() : 0;
    }

    /**
     *
     * @param userId
     * @return Doctor Name
     */
    public String getUserNameById(int userId) {
        Criteria c = session.createCriteria(Model.DB.Doctor.class);
        c.add(Restrictions.eq("userIduser", userId));
        Model.DB.Doctor doctor = (Model.DB.Doctor) c.uniqueResult();
        return doctor != null ? doctor.getLastName() : "";
    }

    /**
     *
     * @param value
     * @return Password
     */
    public String getPasswordById(String value) {
        try {
            Controller.encrypter enc = new encrypter();
            String en = value + "=";
            int userId = Integer.parseInt(enc.decrypt(en));
            Model.DB.User user = (Model.DB.User) session.load(Model.DB.User.class, userId);
            return user != null ? user.getIduser() + "," + user.getPassword() : null;
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     *
     * @param userId
     * @return Doctor Object
     */
    public Model.DB.Doctor getDoctorByUserId(int userId) {
        return (Doctor) session.createCriteria(Model.DB.Doctor.class).add(Restrictions.eq("userIduser", userId)).uniqueResult();
    }

    /**
     *
     * @param email
     * @return User ID
     */
    public int getUserIdByEmail(String email) {
        Criteria c = session.createCriteria(Model.DB.User.class);
        c.add(Restrictions.eq("emal", email));
        Model.DB.User user = (Model.DB.User) c.uniqueResult();
        return user != null ? user.getIduser() : 0;
    }

    /**
     *
     * @param userId
     * @return availability
     */
    public boolean checkPatientConnection(String userId) {
        Criteria c = session.createCriteria(Model.DB.DoctorHasPatient.class);
        Model.DB.Patient patient = (Model.DB.Patient) session.load(Model.DB.Patient.class, Integer.parseInt(userId));
        c.add(Restrictions.and(Restrictions.eq("patient", patient), Restrictions.eq("status", (Model.DB.Status) session.load(Model.DB.Status.class, 1))));
        return (Model.DB.DoctorHasPatient) c.uniqueResult() != null;
    }

    /**
     *
     * @return Random Tip
     */
    public String getRandomTip() {
        Criteria criteria = session.createCriteria(Model.DB.Dailytip.class);
        criteria.add(Restrictions.sqlRestriction("1=1 order by rand()"));
        criteria.setMaxResults(1);
        Model.DB.Dailytip dailytip = (Model.DB.Dailytip) criteria.uniqueResult();
        return dailytip != null ? dailytip.getDailyTip() : "";
    }

    /**
     *
     * @return List of Doctors
     */
    public ArrayList<String> getDoctorList() {
        List<Doctor> doctorList = session.createCriteria(Model.DB.Doctor.class).list();
        ArrayList<String> doctors = new ArrayList();
        for (Model.DB.Doctor doctor : doctorList) {
            doctors.add(doctor.getUserIduser() + "-Dr." + doctor.getFirstName() + " " + doctor.getLastName() + "-" + doctor.getHospital().getHospital() + " LKR." + doctor.getTipPayment());
        }
        return doctors;
    }

    /**
     *
     * @param patientId
     * @return Doctor Name
     */
    public String getDoctorNameByPatient(int patientId) {
        Criteria c = session.createCriteria(Model.DB.DoctorHasPatient.class);
        c.add(Restrictions.and(Restrictions.eq("patient", (Model.DB.Patient) session.load(Model.DB.Patient.class, patientId)), Restrictions.eq("status", (Model.DB.Status) session.load(Model.DB.Status.class, 1))));
        Model.DB.DoctorHasPatient doctorHasPatient = (Model.DB.DoctorHasPatient) c.uniqueResult();
        return doctorHasPatient != null ? "Dr." + doctorHasPatient.getDoctor().getLastName() : "";
    }

    /**
     *
     * @param doctorId
     * @return DoctorHasPatient Connections Details by doctor id
     */
    public List<Model.DB.DoctorHasPatient> getPatientByDoctor(int doctorId) {
        Criteria c = session.createCriteria(Model.DB.DoctorHasPatient.class);
        c.add(Restrictions.and(Restrictions.eq("doctor", (Model.DB.Doctor) session.load(Model.DB.Doctor.class, doctorId)), Restrictions.eq("status", (Model.DB.Status) session.load(Model.DB.Status.class, 1))));
        List<Model.DB.DoctorHasPatient> dhps = c.list();
        return dhps;
    }

    /**
     *
     * @param patientId
     * @return DoctorHasPatient Connection Details by patient id
     */
    public Model.DB.DoctorHasPatient getConnectionDetailsByPatient(int patientId) {
        Criteria c = session.createCriteria(Model.DB.DoctorHasPatient.class);
        c.add(Restrictions.and(Restrictions.eq("patient", (Model.DB.Patient) session.load(Model.DB.Patient.class, patientId)), Restrictions.eq("status", (Model.DB.Status) session.load(Model.DB.Status.class, 1))));
        return (DoctorHasPatient) c.uniqueResult();
    }

    /**
     *
     * @param doctorId
     * @return DoctorHasPatient Connection Details by doctor id
     */
    public Model.DB.DoctorHasPatient getConnectionDetailsByDoctor(int doctorId) {
        Criteria c = session.createCriteria(Model.DB.DoctorHasPatient.class);
        c.add(Restrictions.and(Restrictions.eq("doctor", (Model.DB.Doctor) session.load(Model.DB.Doctor.class, doctorId)), Restrictions.eq("status", (Model.DB.Status) session.load(Model.DB.Status.class, 1))));
        return (DoctorHasPatient) c.uniqueResult();
    }

    /**
     *
     * @param patientId
     * @return Doctor ID
     */
    public int getDoctorIdByPatient(int patientId) {
        Criteria c = session.createCriteria(Model.DB.DoctorHasPatient.class);
        c.add(Restrictions.and(Restrictions.eq("patient", (Model.DB.Patient) session.load(Model.DB.Patient.class, patientId)), Restrictions.eq("status", (Model.DB.Status) session.load(Model.DB.Status.class, 1))));
        Model.DB.DoctorHasPatient doctorHasPatient = (Model.DB.DoctorHasPatient) c.uniqueResult();
        return doctorHasPatient.getDoctor().getUserIduser();
    }

    /**
     *
     * @param doctorId
     * @return unread msg count
     */
    public int getDoctorsUnreadMsgCount(int doctorId) {
        Criteria c = session.createCriteria(Model.DB.Sugarrate.class);
        c.add(Restrictions.and(Restrictions.eq("doctorHasPatient", getConnectionDetailsByDoctor(doctorId)),
                Restrictions.eq("isRead", false)));
        return c.list().size();
    }

    /**
     *
     * @param doctorId
     * @return Messages List
     */
    public List<Model.DB.Sugarrate> getDoctorsMessages(int doctorId) {
        Criteria c = session.createCriteria(Model.DB.Sugarrate.class);
        c.add(Restrictions.eq("doctorHasPatient", getConnectionDetailsByDoctor(doctorId)));
        c.addOrder(Order.desc("date"));
        return c.list();
    }

    /**
     *
     * @param msgId
     * @return Message
     */
    public Model.DB.Sugarrate getMessageDetail(int msgId) {
        return (Sugarrate) session.load(Model.DB.Sugarrate.class, msgId);
    }

    /**
     *
     * @param msgId
     * @return boolean
     */
    public boolean isReplyedForSugarRate(int msgId) {
        Criteria criteria = session.createCriteria(Model.DB.Healthtip.class);
        criteria.add(Restrictions.eq("sugarrate", (Model.DB.Sugarrate) session.load(Model.DB.Sugarrate.class, msgId)));
        return criteria.uniqueResult() != null;
    }

    /**
     *
     * @param doctorId
     * @return Doctor Payment Details
     */
    public List<Model.DB.Tippayment> getPaymentdetailByDoctorId(int doctorId) {
        Criteria tipPayment = session.createCriteria(Model.DB.Tippayment.class);
        tipPayment.createAlias("sugarrate", "sg");
        tipPayment.createAlias("sg.doctorHasPatient", "dhp");
        tipPayment.createAlias("dhp.doctor", "d");

        tipPayment.add(Restrictions.eq("d.userIduser", doctorId));
        tipPayment.addOrder(Order.desc("paidDate"));
        return tipPayment.list();
    }

    /**
     *
     * @param doctorId
     * @return Received All Payment
     */
    public double getDoctorReceivedAmountById(int doctorId) {
        List<Tippayment> payments = getPaymentdetailByDoctorId(doctorId);
        double amount = 0;
        if (!payments.isEmpty()) {
            for (Tippayment p : payments) {
                amount += Controller.Tools.getNetAmount(p.getAmount(), p.getMaintaincharge().getAdminCharge());
            }
        }
        return amount;
    }

    /**
     *
     * @param doctorId
     * @return Heath Tips List
     */
    public List<Model.DB.Healthtip> getHealthTips(int doctorId) {
        Criteria healthTip = session.createCriteria(Model.DB.Healthtip.class);
        healthTip.createAlias("sugarrate", "sg");
        healthTip.createAlias("sg.doctorHasPatient", "dhp");
        healthTip.createAlias("dhp.doctor", "d");

        healthTip.add(Restrictions.eq("d.userIduser", doctorId));
        return healthTip.list();
    }

    /**
     *
     * @param doctorId
     * @return Not Replied Message Count
     */
    public int getNotRepliedMessageCountByDoctorId(int doctorId) {
        List<Sugarrate> messges = getDoctorsMessages(doctorId);
        if (messges.isEmpty()) {
            return 0;
        } else {
            int msg_count = 0;
            for (Sugarrate m : messges) {
                if (!isReplyedForSugarRate(m.getIdsugarrate())) {
                    msg_count++;
                }
            }
            return msg_count;
        }
    }

    /**
     *
     * @param doctorId
     * @return Hospital
     */
    public Model.DB.Hospital getSelectedHospitalByDoctor(int doctorId) {
        Model.DB.Doctor d = (Model.DB.Doctor) session.load(Model.DB.Doctor.class, doctorId);
        return d.getHospital();
    }

    /**
     *
     * @param doctorId
     * @return Bank
     */
    public Model.DB.Bank getSelectedBankByDoctor(int doctorId) {
        Model.DB.Doctor d = (Model.DB.Doctor) session.load(Model.DB.Doctor.class, doctorId);
        return d.getBank();
    }

    /**
     *
     * @param patientId
     * @return Messages
     */
    public ArrayList<String> getPatientMessages(int patientId) {
        Criteria healthTip = session.createCriteria(Model.DB.Healthtip.class);
        healthTip.createAlias("sugarrate", "sg");
        healthTip.createAlias("sg.doctorHasPatient", "dhp");
        healthTip.createAlias("dhp.patient", "p");

        healthTip.add(Restrictions.eq("p.userIduser", patientId));
        List<Model.DB.Healthtip> healthtips = healthTip.list();
        ArrayList<String> al = new ArrayList<>();
        if (!healthtips.isEmpty()) {
            for (Model.DB.Healthtip h : healthtips) {
                al.add(h.getHealthTipsIdtip() + "-" + h.getSendDate() + "-" + h.getSugarrate().getSugarRate() + "(mg/dL)" + "-" + (h.getIsRead() ? "Read" : "Unread"));
            }
        }
        return al;
    }

    /**
     *
     * @param tipId
     * @return Health Tip
     */
    public String getHealthTipById(int tipId) {
        Transaction t = session.beginTransaction();
        Model.DB.Healthtip h = (Model.DB.Healthtip) session.load(Model.DB.Healthtip.class, tipId);
        h.setIsRead(true);
        session.save(h);
        t.commit();
        return h.getTip();
    }
//    public static void main(String[] args) {
//        getDetails d = new getDetails();
//        ArrayList<String> patientMessages = d.getPatientMessages(1);
//        for (String p : patientMessages) {
//            System.out.println(p);
//        }
//    }
}
