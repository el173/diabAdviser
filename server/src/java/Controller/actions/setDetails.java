/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.actions;

import Controller.encrypter;
import Model.DB.Bank;
import Model.DB.Dailytip;
import Model.DB.Doctor;
import Model.DB.DoctorHasPatient;
import Model.DB.Healthtip;
import Model.DB.Hospital;
import Model.DB.Maintaincharge;
import Model.DB.Paidstatus;
import Model.DB.Patient;
import Model.DB.Status;
import Model.DB.Sugarrate;
import Model.DB.Tippayment;
import Model.DB.User;
import java.util.Date;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Hashith
 */
public class setDetails {

    Session session = Controller.Tools.getSession();
    Transaction t = session.beginTransaction();
    public User user;

    /**
     *
     * @param hospitalName
     * @return
     */
    public boolean saveNewHospital(String hospitalName) {
        try {
            Model.DB.Hospital hospital = new Hospital();
            hospital.setHospital(hospitalName);
            session.save(hospital);
            t.commit();
            System.gc();
            return true;
        } catch (Exception e) {
            System.gc();
            return false;
        }
    }

    /**
     *
     * @param bankName
     * @return
     */
    public boolean saveNewBank(String bankName) {
        try {
            Model.DB.Bank bank = new Bank();
            bank.setBankName(bankName);
            session.save(bank);
            t.commit();
            System.gc();
            return true;
        } catch (Exception e) {
            System.gc();
            return false;
        }
    }

    /**
     *
     * @param email
     * @param password
     * @param firstName
     * @param lastName
     * @param mobile
     * @param hospitalId
     * @param payment_tip
     * @param accNum
     * @param accName
     * @param bankId
     * @return
     */
    public boolean saveNewDoctor(String email, String password, String firstName,
            String lastName, String mobile, int hospitalId,
            Double payment_tip, long accNum, String accName, int bankId) {
        try {
            user = new User();
            user.setEmal(email);
            user.setPassword(password);
            user.setRegDate(new Date());
            user.setStatus((Status) session.load(Model.DB.Status.class, 2));
            session.save(user);

            Model.DB.Doctor doctor = new Doctor();
            doctor.setFirstName(firstName);
            doctor.setLastName(lastName);
            doctor.setMobile(mobile);
            doctor.setHospital((Hospital) session.load(Model.DB.Hospital.class, hospitalId));
            doctor.setTipPayment(payment_tip);
            doctor.setAccountNumber(accNum);
            doctor.setAccountName(accName);
            doctor.setBank((Bank) session.load(Model.DB.Bank.class, bankId));
            doctor.setUser(user);
            session.save(doctor);

            t.commit();

            System.gc();
            return true;
        } catch (Exception e) {
            System.gc();
            return false;
        }
    }

    /**
     *
     * @param value
     * @return
     */
    public boolean activateAccount(String value) {
        try {
            Controller.encrypter enc = new encrypter();
            String en = value + "=";
            int userId = Integer.parseInt(enc.decrypt(en));
            User activeUser = (User) session.load(Model.DB.User.class, userId);
            activeUser.setStatus((Status) session.load(Model.DB.Status.class, 1));
            session.save(activeUser);
            t.commit();
            System.gc();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     *
     * @param userId
     * @param password
     * @return
     */
    public boolean changePassword(int userId, String password) {
        try {
            Model.DB.User newUser = (Model.DB.User) session.load(Model.DB.User.class, userId);
            newUser.setPassword(password);
            session.save(newUser);
            t.commit();
            System.gc();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     *
     * @param doctor
     * @param text
     * @return
     */
    public boolean saveNewDailyTip(Model.DB.Doctor doctor, String text) {
        try {
            Model.DB.Dailytip dailytip = new Dailytip();
            dailytip.setDailyTip(text);
            dailytip.setDoctor(doctor);
            dailytip.setAddedDate(new Date());
            session.save(dailytip);
            t.commit();
            System.gc();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     *
     * @param email
     * @param password
     * @param name
     * @param mobile
     * @return
     */
    public boolean savePatient(String email, String password, String name, String mobile) {
        if (new Controller.actions.user().checkUserAvailabilitybyEmail(email) == null) {
            try {
                user = new User();
                user.setEmal(email);
                user.setPassword(password);
                user.setRegDate(new Date());
                user.setStatus((Status) session.load(Model.DB.Status.class, 2));
                session.save(user);

                Model.DB.Patient patient = new Patient();
                patient.setUser(user);
                patient.setFirstName(name);
                patient.setMobile(mobile);
                int confirm_code = Controller.Tools.getRandomInt();
                patient.setConfitmCode(String.valueOf(confirm_code));
                session.save(patient);

                if (new Controller.emailSender().send(email, "daibAdviser Confimation Code for Android", String.valueOf(confirm_code), Controller.emailSender.PATIENT_ACCOUNT_CONFIRM_CODE)) {
                    t.commit();
                    return true;
                } else {
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     *
     * @param email
     * @param cnfrm_code
     * @return
     */
    public boolean activatePatientAccount(String email, String cnfrm_code) {
        try {
            Criteria getUser = session.createCriteria(Model.DB.User.class);
            getUser.add(Restrictions.eq("emal", email));
            Model.DB.User Activation_user = (Model.DB.User) getUser.uniqueResult();
            if (Activation_user != null) {
                Model.DB.Patient patient = Activation_user.getPatient();
                if (patient.getConfitmCode().equals(cnfrm_code)) {
                    Activation_user.setStatus((Status) session.load(Model.DB.Status.class, 1));
                    session.save(Activation_user);
                    t.commit();
                    System.gc();
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     *
     * @param patientId
     * @param doctorId
     * @return
     */
    public boolean saveDoctorPatientConnection(int patientId, int doctorId) {
        try {
            Model.DB.DoctorHasPatient doctorHasPatient = new DoctorHasPatient();
            doctorHasPatient.setPatient((Model.DB.Patient) session.load(Model.DB.Patient.class, patientId));
            doctorHasPatient.setDoctor((Model.DB.Doctor) session.load(Model.DB.Doctor.class, doctorId));
            doctorHasPatient.setStartDate(new Date());
            doctorHasPatient.setStatus((Model.DB.Status) session.load(Model.DB.Status.class, 1));
            session.save(doctorHasPatient);
            t.commit();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     *
     * @param sugarRate
     * @param msg
     * @param patientId
     * @param amount
     */
    public void saveSugarRateAndPayment(double sugarRate, String msg, int patientId, double amount) {
        Model.DB.Sugarrate sugarrate = new Sugarrate();
        sugarrate.setDate(new Date().toString());
        sugarrate.setSugarRate(sugarRate);
        sugarrate.setMessage(msg);
        sugarrate.setDoctorHasPatient(new Controller.actions.getDetails().getConnectionDetailsByPatient(patientId));
        sugarrate.setIsRead(false);
        session.save(sugarrate);

        Model.DB.Tippayment tippayment = new Tippayment();
        tippayment.setPaidDate(new Date());
        tippayment.setAmount(amount);
        tippayment.setSugarrate(sugarrate);
        tippayment.setPaidstatus((Paidstatus) session.load(Model.DB.Paidstatus.class, 1));
        tippayment.setMaintaincharge(getMaitainCharge());
        session.save(tippayment);

        t.commit();
    }

    /**
     *
     * @return Maintain Charge
     */
    public Model.DB.Maintaincharge getMaitainCharge() {
        Criteria c = session.createCriteria(Model.DB.Maintaincharge.class);
        c.add(Restrictions.eq("status", (Model.DB.Status) session.load(Model.DB.Status.class, 1)));
        return (Maintaincharge) c.uniqueResult();
    }

    /**
     *
     * @param msgId
     */
    public void markAsReadSugarRate(int msgId) {
        Model.DB.Sugarrate sugarrate = (Model.DB.Sugarrate) session.load(Model.DB.Sugarrate.class, msgId);
        sugarrate.setIsRead(true);
        session.save(sugarrate);
        t.commit();
    }

    /**
     *
     * @param msg_id
     * @param reply
     * @return boolean
     */
    public boolean sendReply(int msg_id, String reply) {
        Model.DB.Healthtip healthtip = new Healthtip();
        healthtip.setSugarrate((Model.DB.Sugarrate) session.load(Model.DB.Sugarrate.class, msg_id));
        healthtip.setTip(reply);
        healthtip.setSendDate(new Date().toString());
        healthtip.setIsRead(false);
        session.save(healthtip);
        t.commit();
        return true;
    }

    /**
     *
     * @param userId
     * @param password
     * @param first_name
     * @param last_name
     * @param mobile
     * @param tip_payment
     * @param hospital_id
     * @param bank_id
     * @param acc_name
     * @param acc_num
     */
    public void upDateDoctorDetails(int userId, String first_name,
            String last_name, String mobile, Double tip_payment, int hospital_id,
            int bank_id, String acc_name, Long acc_num) {
        Model.DB.Doctor d = (Model.DB.Doctor) session.load(Model.DB.Doctor.class, userId);
        d.setFirstName(first_name);
        d.setLastName(last_name);
        d.setMobile(mobile);
        d.setTipPayment(tip_payment);
        d.setHospital((Model.DB.Hospital) session.load(Model.DB.Hospital.class, hospital_id));
        d.setBank((Model.DB.Bank) session.load(Model.DB.Bank.class, bank_id));
        d.setAccountName(acc_name);
        d.setAccountNumber(acc_num);
        session.save(d);
        t.commit();
    }

    /**
     *
     * @param userId
     * @param n_pass
     */
    public void updateUserPassword(int userId, String n_pass) {
        Model.DB.User u = (Model.DB.User) session.load(Model.DB.User.class, userId);
        u.setPassword(n_pass);
        session.save(u);
        t.commit();
    }

    /**
     *
     * @param userId
     * @param c_pass
     * @param n_pass
     * @return
     */
    public String changePatientPassword(int userId, String c_pass, String n_pass) {
        String output = "";
        Model.DB.User u = (Model.DB.User) session.load(Model.DB.User.class, userId);
        if (u.getPassword().equals(c_pass)) {
            u.setPassword(n_pass);
            session.save(u);
            t.commit();
            output = "1";
        } else {
            output = "0";
        }
        return output;
    }
}
