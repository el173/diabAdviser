package com.my.webservices;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;

import com.my.diabadviser.Register;

import android.os.AsyncTask;
import android.util.Log;

public class DiabAdviserRegister extends AsyncTask<String, Integer, Integer> {

	private final String NAMESPACE = "http://webservice.Controller/";
	private final String METHOD_NAME = "registerNewPatient";
	private final String URL = "http://"+com.my.detail.Details.WEB_HOST+":8080/diabAdviser/DiabAdviserWebService?WSDL";
	private final String SOAP_ACTION = "";
	private Register register;
	private String email;
	private String password;
	private String name;
	private String mobile;

	public DiabAdviserRegister(Register register) {
		this.register = register;
	}

	@Override
	protected Integer doInBackground(String... params) {
		email = params[0];
		password = params[1];
		name = params[2];
		mobile = params[3];
		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty("email", email);
		request.addProperty("pass", password);
		request.addProperty("name", name);
		request.addProperty("mob", mobile);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		AndroidHttpTransport androidHttpTransport = new AndroidHttpTransport(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			SoapPrimitive result = (SoapPrimitive) envelope.getResponse();

			if (result.toString().equalsIgnoreCase("true")) {
				return 0;
			} else {
				return 1;
			}

		} catch (Exception e) {
			Log.d("Registation ERROR", e.toString());
			return 1;
		}
	}

	@Override
	protected void onPostExecute(Integer result) {
		register.createNewAccount(result);
		// super.onPostExecute(result);
	}

}
