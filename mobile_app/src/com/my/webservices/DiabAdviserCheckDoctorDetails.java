package com.my.webservices;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;

import com.my.diabadviser.SendSugarate;

import android.os.AsyncTask;
import android.util.Log;

public class DiabAdviserCheckDoctorDetails extends AsyncTask<String, String, String> {
	private final String NAMESPACE = "http://webservice.Controller/";
	private final String METHOD_NAME = "getDoctorCharge";
	private final String URL = "http://"+com.my.detail.Details.WEB_HOST+":8080/diabAdviser/DiabAdviserWebService?WSDL";
	private final String SOAP_ACTION = "";
	private String doctorId = "";
	SendSugarate sendSugarate;

	public DiabAdviserCheckDoctorDetails(SendSugarate sendSugarate) {
		this.sendSugarate = sendSugarate;
	}

	@Override
	protected String doInBackground(String... params) {
		String doctorCharge = "";
		doctorId = params[0];
		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty("doctorId", doctorId);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		AndroidHttpTransport androidHttpTransport = new AndroidHttpTransport(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			SoapPrimitive result = (SoapPrimitive) envelope.getResponse();
			doctorCharge = result.toString();
		} catch (Exception e) {
			Log.d("Confimation ERROR", e.toString());
			System.err.print(e.toString());
			doctorCharge = "0.0";
		}
		return doctorCharge;
	}

	@Override
	protected void onPostExecute(String result) {
		com.my.detail.Details.doctorCharge = result;
	}

}
