package com.my.diabadviser;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class ChangeDoctor extends Activity {

	private Spinner spinner_doc;
	private Intent UIController;
	private Button btn_saveChanges;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_doctor);
		spinner_doc = (Spinner) findViewById(R.id.spn_selectDoc_doc);
		btn_saveChanges = (Button) findViewById(R.id.btn_changeDoctor_saveChange);
		addActionListners();
		new com.my.webservices.DiabAdviserGetDoctorList(this).execute("doctor");
	}

	private void addActionListners() {
		btn_saveChanges.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String doctorId = spinner_doc.getSelectedItem().toString().split("-")[0];
				new com.my.webservices.DiabAdviserAddPatientConnection(ChangeDoctor.this)
						.execute(String.valueOf(com.my.detail.Details.userId), doctorId);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.change_doctor, menu);
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

	@SuppressWarnings("null")
	public void setDoctors(ArrayList<String> result) {
		if (result != null || !result.isEmpty()) {
			@SuppressWarnings({ "rawtypes", "unchecked" })
			ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, result);
			spinner_doc.setAdapter(adapter);
		} else {
			Toast.makeText(getApplicationContext(), "Please Try again Letter", Toast.LENGTH_LONG).show();
			UIController = new Intent(getApplicationContext(), Home.class);
			UIController.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(UIController);
		}
	}

	public void addConnection(boolean status) {
		if (status) {
			Toast.makeText(getApplicationContext(), "Change Successfuly Added! Please Sign in Againg",
					Toast.LENGTH_LONG).show();
			UIController = new Intent(getApplicationContext(), Login.class);
			UIController.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
			startActivity(UIController);
		} else {
			Toast.makeText(getApplicationContext(), "Please Try again Letter", Toast.LENGTH_LONG).show();
			UIController = new Intent(getApplicationContext(), Home.class);
			UIController.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(UIController);
		}
	}
}
