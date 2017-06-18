package com.my.diabadviser;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Home extends Activity {

	private Intent UIController;
	private TextView lbl_doctor_warning;
	private Button btn_viewMessgaes;
	private Button btn_getTip;
	private Button btn_selectDoc;
	private Button btn_changePassword;
	public String dailyTip;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		lbl_doctor_warning = (TextView) findViewById(R.id.selected_doctor);
		btn_viewMessgaes = (Button) findViewById(R.id.btn_home_viewMessages);
		btn_getTip = (Button) findViewById(R.id.btn_home_getTip);
		btn_selectDoc = (Button) findViewById(R.id.btn_home_selectDoc);
		btn_changePassword = (Button) findViewById(R.id.btn_home_changePass);
		new com.my.webservices.DiabAdviserCheckDoctor(this).execute(String.valueOf(com.my.detail.Details.userId));
		setup();
	}

	private void setup() {
		addAcctionListner();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
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

	private void addAcctionListner() {
		btn_selectDoc.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				UIController = new Intent(getApplicationContext(), ChangeDoctor.class);
				UIController.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(UIController);
			}
		});

		btn_getTip.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				UIController = new Intent(getApplicationContext(), SendSugarate.class);
				UIController.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(UIController);
			}
		});
		btn_viewMessgaes.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				UIController = new Intent(getApplicationContext(), ViewMessages.class);
				UIController.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(UIController);
			}
		});
		btn_changePassword.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				UIController = new Intent(getApplicationContext(), ChangePassword.class);
				UIController.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(UIController);
			}
		});
	}

	public void isDoctorAvailable(boolean b, String doctorName) {
		if (b) {
			lbl_doctor_warning.setText(doctorName);
			btn_selectDoc.setEnabled(false);
			com.my.detail.Details.doctorAvailability = true;
			new com.my.webservices.DiabAdviserGetConnectionId().execute(String.valueOf(com.my.detail.Details.userId));
		} else {
			lbl_doctor_warning.setText("Please Select A Doctor");
			lbl_doctor_warning.setBackgroundColor(Color.RED);
			btn_viewMessgaes.setEnabled(false);
			btn_getTip.setEnabled(false);
			com.my.detail.Details.doctorAvailability = false;
		}
	}

	public void showDailyTip(String Tip) {
		DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		};

		AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
		builder.setMessage(Tip).setPositiveButton("OK", dialogClickListener)
				.setNegativeButton("Cancel", dialogClickListener).setTitle("Daily Tip From diabAdviser").show();
	}
}
