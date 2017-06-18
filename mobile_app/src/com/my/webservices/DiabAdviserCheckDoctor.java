package com.my.webservices;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;

import com.my.diabadviser.Home;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class DiabAdviserCheckDoctor extends AsyncTask<String, Boolean, Boolean> {

	private final String NAMESPACE = "http://webservice.Controller/";
	private final String METHOD_NAME = "checkDoctorAvailability";
	private final String METHOD_GET_DOCTOR_NAME = "getSelectedDoctorNameByPatient";
	private final String METHOD_GET_DOCTOR_ID = "getDoctorId";
	private final String RANDOM_TIP_METHOD_NAME = "getRandomTip";
	private final String URL = "http://"+com.my.detail.Details.WEB_HOST+":8080/diabAdviser/DiabAdviserWebService?WSDL";
	private final String SOAP_ACTION = "";
	private String userId;
	private Home home;
	private String randomTip;
	private String doctorName = "";

	public DiabAdviserCheckDoctor(Home home) {
		this.home = home;
	}

	@Override
	protected Boolean doInBackground(String... params) {
		boolean b = false;
		userId = params[0];

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty("userId", userId);

		SoapObject randomTipRequest = new SoapObject(NAMESPACE, RANDOM_TIP_METHOD_NAME);

		SoapObject doctorNameRequest = new SoapObject(NAMESPACE, METHOD_GET_DOCTOR_NAME);
		doctorNameRequest.addProperty("patientId", userId);

		SoapObject doctorIdRequest = new SoapObject(NAMESPACE, METHOD_GET_DOCTOR_ID);
		doctorIdRequest.addProperty("patientId", userId);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		AndroidHttpTransport androidHttpTransport = new AndroidHttpTransport(URL);

		SoapSerializationEnvelope randomTipEnvelop = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		randomTipEnvelop.setOutputSoapObject(randomTipRequest);
		AndroidHttpTransport randomTipTransport = new AndroidHttpTransport(URL);

		SoapSerializationEnvelope doctorNameEnvelop = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		doctorNameEnvelop.setOutputSoapObject(doctorNameRequest);
		AndroidHttpTransport doctorNameTransport = new AndroidHttpTransport(URL);

		SoapSerializationEnvelope doctorIdEnvelop = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		doctorIdEnvelop.setOutputSoapObject(doctorIdRequest);
		AndroidHttpTransport doctorIdTransport = new AndroidHttpTransport(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			SoapPrimitive result = (SoapPrimitive) envelope.getResponse();

			if (result.toString().equalsIgnoreCase("true")) {
				b = true;
			} else {
				b = false;
			}

			randomTipTransport.call(SOAP_ACTION, randomTipEnvelop);
			SoapPrimitive randomTipResult = (SoapPrimitive) randomTipEnvelop.getResponse();
			randomTip = randomTipResult.toString();

			if (b) {
				doctorNameTransport.call(SOAP_ACTION, doctorNameEnvelop);
				SoapPrimitive doctorNameResult = (SoapPrimitive) doctorNameEnvelop.getResponse();
				doctorName = doctorNameResult.toString();
				com.my.detail.Details.doctorName = doctorName;

				doctorIdTransport.call(SOAP_ACTION, doctorIdEnvelop);
				SoapPrimitive doctorIdResult = (SoapPrimitive) doctorIdEnvelop.getResponse();
				com.my.detail.Details.doctorId = doctorIdResult.toString();
			}

		} catch (Exception e) {
			Log.d("Get Selected Doctor ERROR", e.toString());
			Toast.makeText(home, e.toString(), Toast.LENGTH_LONG).show();
			b = false;
		}
		System.gc();
		return b;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		home.isDoctorAvailable(result, doctorName);
		home.showDailyTip(randomTip);
		super.onPostExecute(result);
	}

}
