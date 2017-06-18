package com.my.webservices;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;

import com.my.diabadviser.AccountConfirm;

import android.os.AsyncTask;
import android.util.Log;

public class DiabAdviserActivateAccount extends AsyncTask<String, Boolean, Boolean> {

	private final String NAMESPACE = "http://webservice.Controller/";
	private final String METHOD_NAME = "confirmAccount";
	private final String URL = "http://"+com.my.detail.Details.WEB_HOST+":8080/diabAdviser/DiabAdviserWebService?WSDL";
	private final String SOAP_ACTION = "";
	private String email;
	private String cnfrm_code;
	private AccountConfirm accountConfirm;

	public DiabAdviserActivateAccount(AccountConfirm accountConfirm) {
		this.accountConfirm = accountConfirm;
	}

	@Override
	protected Boolean doInBackground(String... params) {
		boolean b = false;
		email = params[0];
		cnfrm_code = params[1];

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty("email", email);
		request.addProperty("cnfrm_code", cnfrm_code);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		AndroidHttpTransport androidHttpTransport = new AndroidHttpTransport(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			SoapPrimitive result = (SoapPrimitive) envelope.getResponse();

			if (result.toString().equalsIgnoreCase("true")) {
				b = true;
			} else {
				b = false;
			}

		} catch (Exception e) {
			Log.d("Confimation ERROR", e.toString());
			b = false;
		}

		return b;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		accountConfirm.confirmAccount(result);
		// super.onPostExecute(result);
	}
}
