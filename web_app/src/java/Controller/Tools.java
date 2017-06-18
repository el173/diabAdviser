/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.util.Random;

/**
 *
 * @author Hashith
 */
public class Tools {

    public static org.hibernate.Session getSession() {
        return Controller.Connection.getSessionFactory().openSession();
    }

    public static int getRandomInt() {
        return new Random().nextInt(10000) + 1;
    }

    public static double getNetAmount(double amount, double precentage) {
        return Double.parseDouble(String.format("%.2f", (amount - (amount * (precentage / 100)))));
    }
}
