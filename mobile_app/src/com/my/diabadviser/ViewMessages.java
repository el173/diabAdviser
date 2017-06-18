package com.my.diabadviser;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
public class ViewMessages extends Activity {

	private Spinner spn_msg;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_messages);
		spn_msg = (Spinner) findViewById(R.id.spn_viewMsg);
		setup();
		addListners();
	}

	private void addListners() {
		spn_msg.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				String items = spn_msg.getSelectedItem().toString();
				new com.my.webservices.DiabAdviserGetHealthTip(ViewMessages.this).execute(items.split("-")[0]);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			
			}
		});
	}

	private void setup() {
		new com.my.webservices.DiabAdviserViewMessage(ViewMessages.this).execute(String.valueOf(com.my.detail.Details.userId));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_messages, menu);
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
	public void setSpinnerData(ArrayList<String> s){
		if (s != null || !s.isEmpty()) {
			@SuppressWarnings({ "rawtypes", "unchecked" })
			ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, s);
			spn_msg.setAdapter(adapter);
		} 
	}
	
	public void showHealthTip(String Tip) {
		DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		};

		AlertDialog.Builder builder = new AlertDialog.Builder(ViewMessages.this);
		builder.setMessage(Tip).setPositiveButton("OK", dialogClickListener)
				.setNegativeButton("Cancel", dialogClickListener).setTitle("Health Tip").show();
	}
}
