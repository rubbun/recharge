package com.example.recharge;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.example.application.Appsettings;
import com.example.application.UserInfo;
import com.example.constant.Constant;

public class BaseActivity extends FragmentActivity implements OnClickListener {
	public ProgressDialog prsDlg;
	public Appsettings app = null;
	//SUCCESS,2102185047370587136,rechargedive.com

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
	
	public boolean isConnectingToInternet(){
		ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		  if (connectivity != null) 
		  {
			  NetworkInfo[] info = connectivity.getAllNetworkInfo();
			  if (info != null) 
				  for (int i = 0; i < info.length; i++) 
					  if (info[i].getState() == NetworkInfo.State.CONNECTED)
					  {
						  return true;
					  }

		  }
		  return false;
	}
}
