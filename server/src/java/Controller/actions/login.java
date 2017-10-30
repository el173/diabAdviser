/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.actions;

import Model.DB.User;
import Model.DB.Userloginlog;
import java.util.Date;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Hashith
 */
public class login {

    public User u;
    private final Session session;
    private final int ACC_ACTIVATE = 0;
    private final int ACC_NOT_EXISTS = 1;
    private final int ACC_NOT_ACTIVATE = 2;

    public login() {
        session = Controller.Tools.getSession();
    }

    /**
     *
     * @param un
     * @param pw
     * @return User Status
     */
    public boolean checkUserLogin(String un, String pw) {
        Criteria users = session.createCriteria(Model.DB.User.class);
        users.add(Restrictions.eq("emal", un));
        users.add(Restrictions.eq("password", pw));
        users.add(Restrictions.eq("status", session.load(Model.DB.Status.class, 1)));
        u = (Model.DB.User) users.uniqueResult();
        if (u != null) {
            return u.getDoctor() != null;
        } else {
            return false;
        }
    }

    /**
     *
     * @param user
     */
    public void saveUserLogin(User user) {
        Transaction t = session.beginTransaction();
        Userloginlog userloginlog = new Userloginlog(user, new Date());
        session.save(userloginlog);
        t.commit();
    }

    /**
     *
     * @param un
     * @param pw
     * @return
     */
    public int checkPatientLogin(String un, String pw) {
        Criteria users = session.createCriteria(Model.DB.User.class);
        users.add(Restrictions.eq("emal", un));
        users.add(Restrictions.eq("password", pw));
        u = (Model.DB.User) users.uniqueResult();
        if (u != null) {
            if (u.getPatient() != null) {
                if (u.getStatus() == session.load(Model.DB.Status.class, 1)) {
                    saveUserLogin(u);
                    return ACC_ACTIVATE;
                } else if (u.getStatus() == session.load(Model.DB.Status.class, 2)) {
                    return ACC_NOT_ACTIVATE;
                } else {
                    return ACC_NOT_EXISTS;
                }
            } else {
                return ACC_NOT_EXISTS;
            }
        } else {
            return ACC_NOT_EXISTS;
        }
    }
}
