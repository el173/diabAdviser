package com.my.webservices;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;

import com.my.diabadviser.SendSugarate;

import android.os.AsyncTask;

public class DiabAdviserSaveMessageAndPayment extends AsyncTask<String, String, String> {

	private final String NAMESPACE = "http://webservice.Controller/";
	private final String METHOD_NAME = "saveMessageAndPayment";
	private final String URL = "http://"+com.my.detail.Details.WEB_HOST+":8080/diabAdviser/DiabAdviserWebService?WSDL";
	private final String SOAP_ACTION = "";
	SendSugarate sendSugarate;
	private String result;

	public DiabAdviserSaveMessageAndPayment(SendSugarate sendSugarate) {
		this.sendSugarate = sendSugarate;
	}

	protected String doInBackground(String... params) {
		SoapObject paymentSaveRequest = new SoapObject(NAMESPACE, METHOD_NAME);
		paymentSaveRequest.addProperty("sugarRate", params[0]);
		paymentSaveRequest.addProperty("msg", params[1]);
		paymentSaveRequest.addProperty("patientId", params[2]);
		paymentSaveRequest.addProperty("amount", params[3]);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.setOutputSoapObject(paymentSaveRequest);

		AndroidHttpTransport httpTransport = new AndroidHttpTransport(URL);
		try {
			httpTransport.call(SOAP_ACTION, envelope);
			SoapPrimitive soapPrimitive = (SoapPrimitive) envelope.getResponse();
			result = soapPrimitive.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	protected void onPostExecute(String result) {

	}
}
