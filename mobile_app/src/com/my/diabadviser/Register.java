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

public class Register extends Activity {

	private TextView txt_name;
	private TextView txt_email;
	private TextView txt_mob;
	private TextView txt_pass;
	private TextView txt_crnfPass;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		txt_name = (TextView) findViewById(R.id.txt_reg_name);
		txt_email = (TextView) findViewById(R.id.txt_reg_email);
		txt_mob = (TextView) findViewById(R.id.txt_reg_mobile);
		txt_pass = (TextView) findViewById(R.id.txt_reg_pass);
		txt_crnfPass = (TextView) findViewById(R.id.txt_reg_confirmPass);

		addActionListener();
	}

	private void addActionListener() {
		Button btn_createAcc = (Button) findViewById(R.id.btn_createAcount);
		btn_createAcc.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (txt_name.getText().toString().isEmpty() || txt_email.getText().toString().isEmpty()
						|| txt_mob.getText().toString().isEmpty() || txt_pass.getText().toString().isEmpty()
						|| txt_crnfPass.getText().toString().isEmpty()) {
					showAlert("Fill all feilds");
				} else {
					if (txt_pass.getText().toString().equals(txt_crnfPass.getText().toString())) {
						new com.my.webservices.DiabAdviserRegister(Register.this).execute(
								txt_email.getText().toString(), txt_pass.getText().toString(),
								txt_name.getText().toString(), txt_mob.getText().toString());
					} else {
						showAlert("Password dose not match");
					}
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
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

	private void showAlert(String alert) {
		Toast.makeText(Register.this, alert, Toast.LENGTH_SHORT).show();
	}

	public void createNewAccount(int result) {
		if (result == 0) {
			showAlert("Your Conrfimation code was send to your email address");
			Intent UIController = new Intent(getApplicationContext(), AccountConfirm.class);
			UIController.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(UIController);
			Register.this.finish();
		} else {
			showAlert("Registation Faild !! or \n e-mail alredy exsits");
		}
	}

}
