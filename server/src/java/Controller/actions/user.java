/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.actions;

import Model.DB.Doctor;
import Model.DB.User;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Hashith
 */
public class user {
    
    public User user;
    public Doctor doctor;

    /**
     *
     * @param email
     * @return User Object
     */
    public Model.DB.User checkUserAvailabilitybyEmail(String email) {
        Criteria users = Controller.Tools.getSession().createCriteria(Model.DB.User.class);
        users.add(Restrictions.eq("emal", email));
        Model.DB.User u = (Model.DB.User) users.uniqueResult();
        return u != null ? u : null;
    }
}
