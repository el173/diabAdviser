package com.my.webservices;

import java.util.ArrayList;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;

import com.my.diabadviser.ChangeDoctor;

import android.os.AsyncTask;

public class DiabAdviserGetDoctorList extends AsyncTask<String, String, ArrayList<String>> {

	private final String NAMESPACE = "http://webservice.Controller/";
	private final String METHOD_NAME = "getDoctorList";
	private final String URL = "http://"+com.my.detail.Details.WEB_HOST+":8080/diabAdviser/DiabAdviserWebService?WSDL";
	private final String SOAP_ACTION = "";

	ChangeDoctor changeDoctor;

	public DiabAdviserGetDoctorList(ChangeDoctor changeDoctor) {
		this.changeDoctor = changeDoctor;
	}

	@Override
	protected ArrayList<String> doInBackground(String... params) {
		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		AndroidHttpTransport androidHttpTransport = new AndroidHttpTransport(URL);
		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);

			SoapObject result = (SoapObject) envelope.bodyIn;

			ArrayList<String> list = new ArrayList<String>();

			int childCount = result.getPropertyCount();
			for (int i = 0; i < childCount; i++) {
				String temp = result.getProperty(i).toString();
				list.add(temp);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	protected void onPostExecute(ArrayList<String> result) {
		changeDoctor.setDoctors(result);
		super.onPostExecute(result);
	}
}
