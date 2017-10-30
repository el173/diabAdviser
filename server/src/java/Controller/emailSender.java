/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import com.sun.mail.smtp.SMTPTransport;
import java.util.Date;
import java.util.Properties;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Hashith
 */
public class emailSender {
    
    final public static int EMAIL_VERIFICATION = 1;
    final public static int PASSWORD_RECOVERY = 2;
    final public static int PATIENT_ACCOUNT_CONFIRM_CODE = 3;
    final public static int CONTACT_ADMIN = 4;

//    public static void main(String[] args) throws MessagingException {
//        emailSender sender = new emailSender();
//        sender.send("hashithkarunarathne@gmail.com", "CONFORM YOUR ACCOUNT", "www.facebook.com", 2);
//    }
    /**
     *
     * @param email
     * @param subject
     * @param link
     * @param Type
     * @return
     * @throws MessagingException
     */
    public boolean send(String email, String subject, String link, int Type) throws javax.mail.MessagingException {
        boolean r = false;
//        System.out.println(link);
        try {
            String[] guy = {email};
            String from = "ibuild.contact@gmail.com";
            String pass = "qwertyuiop[";
            String host = "smtp.gmail.com";
            
            Properties prop = new Properties();
            prop = System.getProperties();
            prop.put("mail.smtp.starttls.enable", "true");
            prop.put("mail.smtp.host", host);
            prop.put("mail.smtp.socketFactory.port", 465);
            prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            prop.put("mail.smtp.user", from);
            prop.put("mail.smtp.password", pass);
            prop.put("mail.smtp.port", 465);
            prop.put("mail.smtp.auth", "true");
            Session ses = Session.getDefaultInstance(prop, null);
            javax.mail.internet.MimeMessage mm = new MimeMessage(ses);
            mm.setFrom(new InternetAddress(from));
            InternetAddress[] ias = new InternetAddress[guy.length];
            for (int i = 0; i < guy.length; i++) {
                ias[i] = new InternetAddress(guy[i]);
            }
            for (int i = 0; i < ias.length; i++) {
                mm.addRecipients(RecipientType.TO, ias);
            }
            mm.setSubject(subject);
            if (Type == PASSWORD_RECOVERY) {
                mm.setContent("<center><body style=\"font-family:Segoe UI\"><div"
                        + " style=\"width: 500px; height: 300px; background-color: burlywood; "
                        + "border-radius: 10px;border-left-color: #CCC;"
                        + "-moz-box-shadow: 0 0 20px black;-webkit-box-shadow: 0 0 20px black;"
                        + "box-shadow: 0 0 20px black;\"><br>"
                        + "<h1 style=\"\">Password Reset e-mail For Your diabAdviser Account</h1>"
                        + "<h3>Do not reply to this</h3><center>"
                        + "<p>Please click the button below to reset your</p><P>diabAdviser Password<P>"
                        + "<a style=\"background-color:#5cb85c;border-radius: 5px; width: 130px;height: 27px;\"><b>"
                        + "<a href=\"" + link + "\">Reset</a></b></a></center></div><a href=\"#\"><u>by DURACT.</u></a>"
                        + "<label>(" + new Date() + ")</label></body></center>", "text/html");
            } else if (Type == EMAIL_VERIFICATION) {
                mm.setContent("<center><body style=\"font-family:Segoe UI\"><div style=\"width: 500px; height: 300px; background-color: burlywood; border-radius: 10px;border-left-color: #CCC;-moz-box-shadow: 0 0 20px black;-webkit-box-shadow: 0 0 20px black;box-shadow: 0 0 20px black;\"><br><h1 style=\"\">Please Conform Your Email address In diabAdviser</h1><h3>Do not reply to this</h3><center><p>Please click the button below to conform your</p><P>diabAdviser Account</P><a style=\"background-color:#5cb85c;border-radius: 5px; width: 130px;height: 27px;\"><b><a href='" + link + " '>Confirm</a></b><br> or go to <br> <a>" + link + "</a></center></div><a href=\"#\"><u>by DURACT.</u></a><label>(" + new Date() + ")</label></body></center>", "text/html");
            } else if (Type == PATIENT_ACCOUNT_CONFIRM_CODE) {
                mm.setContent("<html><center><p>Your diabAdviser Account Confirmation Code is</p><h2>" + link + "</h2><p>" + new Date() + "</p></center></html>", "text/html");
            } else if (Type == CONTACT_ADMIN) {
                mm.setContent(link, "text/html");
            }
            SMTPTransport tran = (SMTPTransport) ses.getTransport("smtp");
            tran.connect(host, 465, from, pass);
            tran.sendMessage(mm, mm.getAllRecipients());
            tran.close();
            System.out.println("MAIL WAS SEND");
            r = true;
        } catch (Exception ex) {
//            ex.printStackTrace();
            r = false;
        }
        return r;
    }
    
}
