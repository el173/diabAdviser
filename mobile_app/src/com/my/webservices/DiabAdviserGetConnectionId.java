package com.my.webservices;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;
import android.annotation.SuppressLint;
import android.os.AsyncTask;

@SuppressLint("UseValueOf")
public class DiabAdviserGetConnectionId extends AsyncTask<String, Integer, Integer> {

	private final String NAMESPACE = "http://webservice.Controller/";
	private final String METHOD_NAME = "getConnectionIdByPatienId";
	private final String URL = "http://"+com.my.detail.Details.WEB_HOST+":8080/diabAdviser/DiabAdviserWebService?WSDL";
	private final String SOAP_ACTION = "";
	private String userId;

	@Override
	protected Integer doInBackground(String... params) {
		userId = params[0];
		Integer i = new Integer(0);
		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty("", userId);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		AndroidHttpTransport androidHttpTransport = new AndroidHttpTransport(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			SoapPrimitive result = (SoapPrimitive) envelope.getResponse();
			i = Integer.parseInt(result.toString());
		} catch (Exception e) {
			i = 0;
		}
		return i;
	}

	@Override
	protected void onPostExecute(Integer result) {
		com.my.detail.Details.connectionId = result;
	}
}
