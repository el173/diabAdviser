package com.my.webservices;

import android.os.AsyncTask;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;

import com.my.diabadviser.Login;

public class DiabAdviserSignIn extends AsyncTask<String, Integer, Integer> {

	private final String NAMESPACE = "http://webservice.Controller/";
	private final String METHOD_NAME = "chekLogin";
	private final String USERID_REQUEST_METHOD = "getUserId";
	private final String URL = "http://"+com.my.detail.Details.WEB_HOST+":8080/diabAdviser/DiabAdviserWebService?WSDL";
	private final String SOAP_ACTION = "";
	private String userName;
	private String password;
	private Login login;
	private int userId;

	public DiabAdviserSignIn(Login login) {
		this.login = login;
	}

	@Override
	protected Integer doInBackground(String... params) {
		userName = params[0];
		password = params[1];
		SoapObject userIdRequest = new SoapObject(NAMESPACE, USERID_REQUEST_METHOD);
		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty("userName", userName);
		request.addProperty("password", password);
		userIdRequest.addProperty("username", userName);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		AndroidHttpTransport androidHttpTransport = new AndroidHttpTransport(URL);

		SoapSerializationEnvelope userIdEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		userIdEnvelope.setOutputSoapObject(userIdRequest);
		AndroidHttpTransport userIdHttpTranspot = new AndroidHttpTransport(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			SoapPrimitive result = (SoapPrimitive) envelope.getResponse();

			try {
				userIdHttpTranspot.call(SOAP_ACTION, userIdEnvelope);
				SoapPrimitive resultUserId = (SoapPrimitive) userIdEnvelope.getResponse();
				userId = Integer.parseInt(resultUserId.toString());
				com.my.detail.Details.userId = userId;
			} catch (Exception e) {
				com.my.detail.Details.userId = 0;
			}

			return Integer.parseInt(result.toString());

		} catch (Exception e) {
			return 10;
		}

	}

	@Override
	protected void onPostExecute(Integer result) {
		login.signIn(result);
		// super.onPostExecute(result);
	}

}
