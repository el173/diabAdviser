package com.my.diabadviser;

import java.math.BigDecimal;

import org.json.JSONException;

import com.paypal.android.sdk.payments.PayPalAuthorization;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalFuturePaymentActivity;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

public class SendSugarate extends Activity {

	private TextView txt_sugrate;
	private TextView txt_message;
	private Button btn_pay_and_send;
	private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_SANDBOX;

	// note that these credentials will differ between live & sandbox
	// environments.
	private static final String CONFIG_CLIENT_ID = "ARJb7ntPOAuOQH8mcTMJPcbS2Q0JIn-MmZYQBXcPrHF6jvbBV6Q8tjRwAbHFCXbjltY_HjyiDtIUX8fL";

	private static final int REQUEST_CODE_PAYMENT = 1;
	private static final int REQUEST_CODE_FUTURE_PAYMENT = 2;

	private static PayPalConfiguration config = new PayPalConfiguration().environment(CONFIG_ENVIRONMENT)
			.clientId(CONFIG_CLIENT_ID)
			// The following are only used in PayPalFuturePaymentActivity.
			.merchantName("diabAdviser").merchantPrivacyPolicyUri(Uri.parse("https://www.diabadviser.com/privacy"))
			.merchantUserAgreementUri(Uri.parse("https://www.diabadviser.com/legal"));

	PayPalPayment doctorPayment;
	SendSugarate sugarRate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_send_sugarate);
		Intent intent = new Intent(this, PayPalService.class);
		intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
		startService(intent);
		sugarRate = this;
		new com.my.webservices.DiabAdviserCheckDoctorDetails(this).execute(com.my.detail.Details.doctorId);
		txt_sugrate = (TextView) findViewById(R.id.txt_sendRate_rate);
		txt_message = (TextView) findViewById(R.id.txt_sendRate_addtionalMessage);
		btn_pay_and_send = (Button) findViewById(R.id.btn_sedRate_payNsend);
		addListner();
	}

	private void addListner() {
		btn_pay_and_send.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!txt_sugrate.getText().toString().isEmpty()) {
					doctorPayment = new PayPalPayment(new BigDecimal(com.my.detail.Details.doctorCharge), "USD",
							"Doctor Payment", PayPalPayment.PAYMENT_INTENT_SALE);
					Intent intent = new Intent(SendSugarate.this, PaymentActivity.class);

					intent.putExtra(PaymentActivity.EXTRA_PAYMENT, doctorPayment);

					startActivityForResult(intent, REQUEST_CODE_PAYMENT);
				} else {
					Toast.makeText(getApplicationContext(), "Please Enter Your Sugar Rate", Toast.LENGTH_LONG).show();
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.send_sugarate, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void onFuturePaymentPressed(View pressed) {
		Intent intent = new Intent(SendSugarate.this, PayPalFuturePaymentActivity.class);

		startActivityForResult(intent, REQUEST_CODE_FUTURE_PAYMENT);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_CODE_PAYMENT) {
			if (resultCode == Activity.RESULT_OK) {
				PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
				if (confirm != null) {
					try {
						System.out.println(confirm.toJSONObject().toString(4));
						System.out.println(confirm.getPayment().toJSONObject().toString(4));

						new com.my.webservices.DiabAdviserSaveMessageAndPayment(sugarRate).execute(
								txt_sugrate.getText().toString(), txt_message.getText().toString(),
								String.valueOf(com.my.detail.Details.userId), com.my.detail.Details.doctorCharge);
						showMessages("Message Send");
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			} else if (resultCode == Activity.RESULT_CANCELED) {
				System.out.println("The user canceled.");
				Toast.makeText(getApplicationContext(), "The User Canceled ", Toast.LENGTH_LONG).show();
			} else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
				System.out.println("An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
				Toast.makeText(getApplicationContext(), "Something went wrong try again", Toast.LENGTH_LONG).show();
			}
		} else if (requestCode == REQUEST_CODE_FUTURE_PAYMENT) {
			if (resultCode == Activity.RESULT_OK) {
				PayPalAuthorization auth = data
						.getParcelableExtra(PayPalFuturePaymentActivity.EXTRA_RESULT_AUTHORIZATION);
				if (auth != null) {
					try {
						Log.i("FuturePaymentExample", auth.toJSONObject().toString(4));

						String authorization_code = auth.getAuthorizationCode();
						Log.i("FuturePaymentExample", authorization_code);

						sendAuthorizationToServer(auth);
						Toast.makeText(getApplicationContext(), "Future Payment code received from PayPal",
								Toast.LENGTH_LONG).show();

					} catch (JSONException e) {
						Log.e("FuturePaymentExample", "an extremely unlikely failure occurred: ", e);
					}
				}
			} else if (resultCode == Activity.RESULT_CANCELED) {
				Log.i("FuturePaymentExample", "The user canceled.");
			} else if (resultCode == PayPalFuturePaymentActivity.RESULT_EXTRAS_INVALID) {
				Log.i("FuturePaymentExample",
						"Probably the attempt to previously start the PayPalService had an invalid PayPalConfiguration. Please see the docs.");
			}
		}
	}

	private void sendAuthorizationToServer(PayPalAuthorization authorization) {

	}

	public void onFuturePaymentPurchasePressed(View pressed) {
		// Get the Application Correlation ID from the SDK
		String correlationId = PayPalConfiguration.getApplicationCorrelationId(this);

		Log.i("FuturePaymentExample", "Application Correlation ID: " + correlationId);

		// TODO: Send correlationId and transaction details to your server for
		// processing with
		// PayPal...
		Toast.makeText(getApplicationContext(), "App Correlation ID received from SDK", Toast.LENGTH_LONG).show();
	}

	@Override
	public void onDestroy() {
		// Stop service when done
		stopService(new Intent(this, PayPalService.class));
		super.onDestroy();
	}

	public void showMessages(String s) {
		Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
		Intent intent = new Intent(getApplicationContext(), Home.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
		sugarRate.finish();
	}

}
