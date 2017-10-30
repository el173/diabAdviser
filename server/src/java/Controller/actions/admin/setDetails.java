/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.actions.admin;

import java.util.Date;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Hashith
 */
public class setDetails {

    public static int ACTIVATE = 1;
    public static int DEACTIVATE = 2;

    Session session = Controller.Tools.getSession();
    Transaction t = session.beginTransaction();

    /**
     *
     * @param paymentId
     */
    public void claimPayment(int paymentId) {
        Model.DB.Tippayment tippayment = (Model.DB.Tippayment) session.load(Model.DB.Tippayment.class, paymentId);
        tippayment.setClaimedDate(new Date());
        session.save(tippayment);
        t.commit();
    }

    /**
     *
     * @param userId
     * @param type
     */
    public void accountHandler(int userId, int type) {
        Model.DB.User user = (Model.DB.User) session.load(Model.DB.User.class, userId);
        if (type == ACTIVATE) {
            user.setStatus((Model.DB.Status) session.load(Model.DB.Status.class, 1));
        } else {
            user.setStatus((Model.DB.Status) session.load(Model.DB.Status.class, 3));
        }
        session.save(user);
        t.commit();
    }
}
