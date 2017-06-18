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

public class AccountConfirm extends Activity {

	private TextView txt_email;
	private TextView txt_crmf_code;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account_confirm);
		
		txt_email = (TextView) findViewById(R.id.txt_cnfrm_email);
		txt_crmf_code = (TextView) findViewById(R.id.txt_cnfrm_code);
		
		addActionListner();
	}

	private void addActionListner() {
		Button btn_crfrm = (Button) findViewById(R.id.btn_crnf_activate);
		btn_crfrm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(txt_crmf_code.getText().toString().isEmpty()||
						txt_email.getText().toString().isEmpty()){
					showAlert("Fill all the feilds !!");
				}else{
					new com.my.webservices.DiabAdviserActivateAccount(AccountConfirm.this).execute(txt_email.getText().toString(),
							txt_crmf_code.getText().toString());
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.account_confirm, menu);
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
		Toast.makeText(AccountConfirm.this, alert, Toast.LENGTH_SHORT).show();
	}
	
	public void confirmAccount(Boolean bool){
		if(bool){
			showAlert("Your account has been activated");
			Intent UIController = new Intent(getApplicationContext(), Login.class);
			UIController.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			AccountConfirm.this.finish();
			startActivity(UIController);
		}else{
			showAlert("Activation Faild !!");
		}
	}
}
