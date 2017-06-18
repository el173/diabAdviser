package com.my.webservices;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;

import com.my.diabadviser.ChangePassword;

import android.os.AsyncTask;

public class DiabAdviserChangePassword extends AsyncTask<String, String, String> {
	private final String NAMESPACE = "http://webservice.Controller/";
	private final String METHOD_NAME = "changePassword";
	private final String URL = "http://"+com.my.detail.Details.WEB_HOST+":8080/diabAdviser/DiabAdviserWebService?WSDL";
	private final String SOAP_ACTION = "";

	ChangePassword changePassword;

	public DiabAdviserChangePassword(ChangePassword changePassword) {
		this.changePassword = changePassword;
	}

	@Override
	protected String doInBackground(String... params) {
		String output = "";
		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty("userID", params[0]);
		request.addProperty("c_pass", params[1]);
		request.addProperty("n_pass", params[2]);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		AndroidHttpTransport androidHttpTransport = new AndroidHttpTransport(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			SoapPrimitive result = (SoapPrimitive) envelope.getResponse();
			output = result.toString();
		} catch (Exception e) {
			
		}
		return output;
	}

	@Override
	protected void onPostExecute(String result) {
		changePassword.done(result);
		super.onPostExecute(result);
	}
}
