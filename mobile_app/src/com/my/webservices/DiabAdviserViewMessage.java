package com.my.webservices;

import java.util.ArrayList;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;

import com.my.diabadviser.ViewMessages;

import android.os.AsyncTask;

public class DiabAdviserViewMessage extends AsyncTask<String, String, ArrayList<String>> {

	private final String NAMESPACE = "http://webservice.Controller/";
	private final String METHOD_NAME = "getPatientMessages";
	private final String URL = "http://"+com.my.detail.Details.WEB_HOST+":8080/diabAdviser/DiabAdviserWebService?WSDL";
	private final String SOAP_ACTION = "";

	ViewMessages viewMessages;

	public DiabAdviserViewMessage(ViewMessages viewMessages) {
		this.viewMessages = viewMessages;
	}

	@Override
	protected ArrayList<String> doInBackground(String... params) {
		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty("patientId", params[0]);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		AndroidHttpTransport androidHttpTransport = new AndroidHttpTransport(URL);

		ArrayList<String> list = new ArrayList<String>();
		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);

			SoapObject result = (SoapObject) envelope.bodyIn;

			int childCount = result.getPropertyCount();
			for (int i = 0; i < childCount; i++) {
				String temp = result.getProperty(i).toString();
				list.add(temp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	protected void onPostExecute(ArrayList<String> result) {
		viewMessages.setSpinnerData(result);;
		super.onPostExecute(result);
	}
}
