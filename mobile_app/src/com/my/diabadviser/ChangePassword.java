package com.my.diabadviser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ChangePassword extends Activity {

	TextView c_pass;
	TextView n_pass;
	TextView crmf_pass;
	Button btn_changePass;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_password);
		c_pass = (TextView) findViewById(R.id.txt_changePass_c_pass);
		n_pass = (TextView) findViewById(R.id.txt_changePass_newPass);
		crmf_pass = (TextView) findViewById(R.id.txt_changePass_crmfPass);
		btn_changePass = (Button) findViewById(R.id.btn_changePass);
		addListner();
	}

	private void addListner() {
		btn_changePass.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (c_pass.getText().toString().isEmpty() || n_pass.getText().toString().isEmpty()
						|| crmf_pass.getText().toString().isEmpty()) {
					showMessage("Please Fill all fields");
				} else {
					if (n_pass.getText().toString().equals(crmf_pass.getText().toString())) {
						new com.my.webservices.DiabAdviserChangePassword(ChangePassword.this).execute(
								String.valueOf(com.my.detail.Details.userId),
								c_pass.getText().toString(),
								n_pass.getText().toString());
					} else {
						showMessage("Confirm Password dose not match");
					}
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.change_password, menu);
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

	public void showMessage(String msg) {
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
	}

	public void done(String result) {
		if (result.equals("1")) {
			showMessage("Password Changed");
			Intent intent = new Intent(getApplicationContext(), Home.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		} else {
			showMessage("Invalid Current Password");
		}
	}
}
