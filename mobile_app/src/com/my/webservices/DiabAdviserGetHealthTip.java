package com.my.webservices;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;

import com.my.diabadviser.ViewMessages;

import android.os.AsyncTask;
import android.util.Log;

public class DiabAdviserGetHealthTip extends AsyncTask<String, String, String> {

	private final String NAMESPACE = "http://webservice.Controller/";
	private final String METHOD_NAME = "getHealthTip";
	private final String URL = "http://"+com.my.detail.Details.WEB_HOST+":8080/diabAdviser/DiabAdviserWebService?WSDL";
	private final String SOAP_ACTION = "";
	ViewMessages viewMessages;

	public DiabAdviserGetHealthTip(ViewMessages viewMessages) {
		this.viewMessages = viewMessages;
	}

	@Override
	protected String doInBackground(String... params) {
		String resultString = "";
		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty("tipId", params[0]);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		AndroidHttpTransport androidHttpTransport = new AndroidHttpTransport(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			SoapPrimitive result = (SoapPrimitive) envelope.getResponse();

			resultString = result.toString();

		} catch (Exception e) {
			Log.d("Msg Reading ERROR", e.toString());
		}
		return resultString;
	}

	@Override
	protected void onPostExecute(String result) {
		viewMessages.showHealthTip(result);
		super.onPostExecute(result);
	}
}
