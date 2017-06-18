package com.my.webservices;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;

import com.my.diabadviser.ChangeDoctor;

import android.os.AsyncTask;
import android.util.Log;

public class DiabAdviserAddPatientConnection extends AsyncTask<String, Boolean, Boolean> {

	private final String NAMESPACE = "http://webservice.Controller/";
	private final String METHOD_NAME = "setPatientConnection";
	private final String URL = "http://"+com.my.detail.Details.WEB_HOST+":8080/diabAdviser/DiabAdviserWebService?WSDL";
	private final String SOAP_ACTION = "";
	ChangeDoctor changeDoctor;
	String patientId;
	String doctorId;

	public DiabAdviserAddPatientConnection(ChangeDoctor changeDoctor) {
		this.changeDoctor = changeDoctor;
	}

	@Override
	protected Boolean doInBackground(String... params) {
		boolean b = false;

		patientId = params[0];
		doctorId = params[1];
		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty("patientId", patientId);
		request.addProperty("doctorId", doctorId);

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
			Log.d("Add Connection ERROR", e.toString());
			b = false;
		}

		return b;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		changeDoctor.addConnection(result);
		super.onPostExecute(result);
	}
}
