package com.my.diabadviser;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends Activity {

	private Intent UIController;
	private TextView txt_userName;
	private TextView txt_password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		txt_userName = (TextView) findViewById(R.id.txt_userName);
		txt_password = (TextView) findViewById(R.id.txt_password);

		setActionListners();
	}

	private void setActionListners() {
		Button btn_sign = (Button) findViewById(R.id.btn_sign);
		btn_sign.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new com.my.webservices.DiabAdviserSignIn(Login.this).execute(txt_userName.getText().toString(),
						txt_password.getText().toString());
			}
		});

		Button btn_reg = (Button) findViewById(R.id.btn_reg);
		btn_reg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				UIController = new Intent(getApplicationContext(), Register.class);
				UIController.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(UIController);
			}
		});
		Button btn_forget_password = (Button) findViewById(R.id.btn_login_forgetPassword);
		btn_forget_password.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "Please visit diabAdviser website to reset your password",
						Toast.LENGTH_LONG).show();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void signIn(int value) {
		if (value == 0) {
			UIController = new Intent(Login.this, Home.class);
			UIController.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			this.startActivity(UIController);
			Login.this.finish();
		} else if (value == 2) {
			showMessageAlert();
		} else {
			Toast.makeText(Login.this, "Login Faild !!!", Toast.LENGTH_SHORT).show();
		}
	}

	private void showMessageAlert() {
		DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case DialogInterface.BUTTON_POSITIVE:
					UIController = new Intent(Login.this, AccountConfirm.class);
					UIController.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					Login.this.startActivity(UIController);
					Login.this.finish();
					break;
				case DialogInterface.BUTTON_NEGATIVE:
					break;
				}
			}
		};

		AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
		builder.setMessage("Please Activate Your Account").setPositiveButton("OK", dialogClickListener)
				.setNegativeButton("Cancel", dialogClickListener).show();
	}
}
