/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my;

import Controller.webclient.Currency;

/**
 *
 * @author Hashith
 */
public class tool {

    public static void main(String[] args) {
        System.out.println(conversionRate(Currency.USD, Currency.LKR));
    }

    private static double conversionRate(Controller.webclient.Currency fromCurrency, Controller.webclient.Currency toCurrency) {
        Controller.webclient.CurrencyConvertor service = new Controller.webclient.CurrencyConvertor();
        Controller.webclient.CurrencyConvertorSoap port = service.getCurrencyConvertorSoap();
        return port.conversionRate(fromCurrency, toCurrency);
    }
}
