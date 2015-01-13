package com.example.recharge;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.example.application.Appsettings;
import com.example.application.UserInfo;
import com.example.constant.Constant;

public class BaseActivity extends FragmentActivity implements OnClickListener {
	public ProgressDialog prsDlg;
	public Appsettings app = null;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		app = (Appsettings) getApplication();
		
		app.setUserinfo(new UserInfo(this));
	}

	public void showProgressDailog() {
		prsDlg = new ProgressDialog(this);
		prsDlg.setMessage("Please wait...");
		prsDlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		prsDlg.setIndeterminate(true);
		prsDlg.setCancelable(false);
		prsDlg.show();

	}

	public void dismissProgressDialog() {
		if (prsDlg.isShowing()) {
			prsDlg.dismiss();
		}
	}

	public void onClick(View arg0) {

	}
	
	public void sendOfflineSMS(final String str) {
		DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
		    @Override
		    public void onClick(DialogInterface dialog, int which) {
		        switch (which){
		        case DialogInterface.BUTTON_POSITIVE:
		            //Yes button clicked
		        	SmsManager smsManager = SmsManager.getDefault();
					smsManager.sendTextMessage(Constant.OFFLINE_MOBILE_NO, null, str, null, null);
					Toast.makeText(getApplicationContext(), "sms sent succesfully! Please wait for response message.",
								Toast.LENGTH_LONG).show();
		            break;

		        case DialogInterface.BUTTON_NEGATIVE:
		            //No button clicked
		            break;
		        }
		    }
		};

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Appplication will send 1 sms and your sim will be charge for sending sms").setPositiveButton("Ok", dialogClickListener)
		    .setNegativeButton("Cancel", dialogClickListener).show();
	}
}
