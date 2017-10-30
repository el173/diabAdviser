/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.webservice;

import java.util.Date;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author Hashith
 */
@WebService(serviceName = "DiabAdviserWebService")
public class DiabAdviserWebService {

    /**
     * Web service operation
     *
     * @param userName
     * @param password
     * @return Login status
     */
    @WebMethod(operationName = "chekLogin")
    public int chekLogin(@WebParam(name = "userName") String userName, @WebParam(name = "password") String password) {
        printAccessedMessage("sign in");
        System.out.println(new Controller.actions.login().checkPatientLogin(userName, password));
        return new Controller.actions.login().checkPatientLogin(userName, password);
    }

    /**
     *
     * @param email
     * @param pass
     * @param name
     * @param mob
     * @return Saved ?
     */
    @WebMethod(operationName = "registerNewPatient")
    public Boolean registerNewPatient(@WebParam(name = "email") String email, @WebParam(name = "pass") String pass, @WebParam(name = "name") String name, @WebParam(name = "mob") String mob) {
        printAccessedMessage("new account");
        return new Controller.actions.setDetails().savePatient(email, pass, name, mob);
        
    }
    
    private void printAccessedMessage(String msg) {
        System.err.println("WEB CLIENT of diabAdviser on android was Accessed : " + msg + " @" + new Date());
        System.gc();
    }

    /**
     *
     * @param email
     * @param cnfrm_code
     * @return Confirm ?
     */
    @WebMethod(operationName = "confirmAccount")
    public Boolean confirmAccount(@WebParam(name = "email") String email, @WebParam(name = "cnfrm_code") String cnfrm_code) {
        printAccessedMessage("account confimation request for-" + email);
        return new Controller.actions.setDetails().activatePatientAccount(email, cnfrm_code);
    }

    /**
     * Web service operation
     *
     * @param username
     * @return user ID
     */
    @WebMethod(operationName = "getUserId")
    public int getUserId(@WebParam(name = "username") String username) {
        return new Controller.actions.getDetails().getUserIdByEmail(username);
    }

    /**
     * Web service operation
     *
     * @param userId
     * @return Availability
     */
    @WebMethod(operationName = "checkDoctorAvailability")
    public boolean checkDoctorAvailability(@WebParam(name = "userId") String userId) {
        return new Controller.actions.getDetails().checkPatientConnection(userId);
    }

    /**
     * Web service operation
     *
     * @return Random Health Tip
     */
    @WebMethod(operationName = "getRandomTip")
    public String getRandomTip() {
        return new Controller.actions.getDetails().getRandomTip();
    }

    /**
     * Web service operation
     *
     * @return List of Doctors
     */
    @WebMethod(operationName = "getDoctorList")
    public java.util.ArrayList<String> getDoctorList() {
        printAccessedMessage("get Doctor List");
        return new Controller.actions.getDetails().getDoctorList();
    }

    /**
     * Web service operation
     *
     * @param patientId
     * @param doctorId
     * @return
     */
    @WebMethod(operationName = "setPatientConnection")
    public boolean setPatientConnection(@WebParam(name = "patientId") String patientId, @WebParam(name = "doctorId") String doctorId) {
        printAccessedMessage("connection Activated");
        return new Controller.actions.setDetails().saveDoctorPatientConnection(Integer.parseInt(patientId), Integer.parseInt(doctorId));
    }

    /**
     * Web service operation
     *
     * @param doctorId
     * @return
     */
    @WebMethod(operationName = "getDoctorNameById")
    public String getDoctorNameById(@WebParam(name = "doctorId") String doctorId) {
        printAccessedMessage("get Doctor Name");
        return new Controller.actions.getDetails().getUserNameById(Integer.parseInt(doctorId));
    }

    /**
     * Web service operation
     *
     * @param patientId
     * @return
     */
    @WebMethod(operationName = "getSelectedDoctorNameByPatient")
    public String getSelectedDoctorNameByPatient(@WebParam(name = "patientId") String patientId) {
        printAccessedMessage("get Doctor Name By Patient Id :" + patientId);
        return new Controller.actions.getDetails().getDoctorNameByPatient(Integer.parseInt(patientId));
    }

    /**
     * Web service operation
     *
     * @param patientId
     * @return
     */
    @WebMethod(operationName = "getConnectionIdByPatienId")
    public int getConnectionId(@WebParam(name = "patientId") String patientId) {
        printAccessedMessage("getConnectionId");
        return new Controller.actions.getDetails().getConnectionDetailsByPatient(Integer.parseInt(patientId)).getId();
    }

    /**
     * Web service operation
     *
     * @param doctorId
     * @return Doctor Tip Charge
     */
    @WebMethod(operationName = "getDoctorCharge")
    public String getDoctorCharge(@WebParam(name = "doctorId") String doctorId) {
        printAccessedMessage("getDoctorCharge");
        System.out.println(doctorId);
        return String.valueOf(new Controller.actions.getDetails().getDoctorByUserId(Integer.parseInt(doctorId)).getTipPayment());
    }

    /**
     * Web service operation
     *
     * @param patientId
     * @return Doctor Id
     */
    @WebMethod(operationName = "getDoctorId")
    public String getDoctorId(@WebParam(name = "patientId") String patientId) {
        printAccessedMessage("getDoctorId");
        return String.valueOf(new Controller.actions.getDetails().getDoctorIdByPatient(Integer.parseInt(patientId)));
    }

    /**
     * Web service operation
     *
     * @param sugarRate
     * @param msg
     * @param patientId
     * @param amount
     * @return
     */
    @WebMethod(operationName = "saveMessageAndPayment")
    public String saveMessageAndPayment(@WebParam(name = "sugarRate") String sugarRate, @WebParam(name = "msg") String msg, @WebParam(name = "patientId") String patientId, @WebParam(name = "amount") String amount) {
        printAccessedMessage("saveMessageAndPayment");
        new Controller.actions.setDetails().saveSugarRateAndPayment(Double.parseDouble(sugarRate), msg, Integer.parseInt(patientId), Double.parseDouble(amount));
        return "ok";
    }

    /**
     * Web service operation
     *
     * @param patientId
     * @return health Tips
     */
    @WebMethod(operationName = "getPatientMessages")
    public java.util.ArrayList<String> getPatientMessages(@WebParam(name = "patientId") String patientId) {
        printAccessedMessage("getPatientMessages");
        return new Controller.actions.getDetails().getPatientMessages(Integer.parseInt(patientId));
    }

    /**
     * Web service operation
     *
     * @param tipId
     * @return health tip
     */
    @WebMethod(operationName = "getHealthTip")
    public String getHealthTip(@WebParam(name = "tipId") String tipId) {
        printAccessedMessage("getHealthTip");
        return new Controller.actions.getDetails().getHealthTipById(Integer.parseInt(tipId));
    }

    /**
     * Web service operation
     *
     * @param userId
     * @param c_pass
     * @param n_pass
     * @return
     */
    @WebMethod(operationName = "changePassword")
    public String changePassword(@WebParam(name = "userID") String userId, @WebParam(name = "c_pass") String c_pass, @WebParam(name = "n_pass") String n_pass) {
        printAccessedMessage("changePassword");
        return new Controller.actions.setDetails().changePatientPassword(
                Integer.parseInt(userId),
                c_pass, n_pass);
    }
}
