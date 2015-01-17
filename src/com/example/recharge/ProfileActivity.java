package com.example.recharge;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.constant.Constant;
import com.example.network.RechargeHttpClient;

public class ProfileActivity extends BaseActivity {
	private TextView tv_name, tv_mobile_number, tv_userid, tv_current_balance, tv_total_earning;
	private String name, userid, email, mobile, mainbal, totalearn,failedMessage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		tv_name = (TextView) findViewById(R.id.tv_name);
		tv_mobile_number = (TextView) findViewById(R.id.tv_mobile_number);
		tv_userid = (TextView) findViewById(R.id.tv_userid);
		tv_current_balance = (TextView) findViewById(R.id.tv_current_balance);
		tv_total_earning = (TextView) findViewById(R.id.tv_total_earning);
		new ProfileAsyncTask().execute();
	}

	public class ProfileAsyncTask extends AsyncTask<Void, Void, Boolean> {

		protected void onPreExecute() {
			showProgressDailog();
		}

		protected Boolean doInBackground(Void... params) {

			String url = Constant.PROFILE + getParams();
			String response = RechargeHttpClient.SendHttpPost(url);
			if (response != null) {
				if (response.startsWith("SUCCESS")) {
					String st[] = response.split(",");
					userid = st[1];
					name = st[2];
					email = st[3];
					mobile = st[4];
					mainbal = st[5];
					totalearn = st[6];
					return true;
				}else if(response.startsWith("FAILED")){
					String st[] = response.split(",");
					failedMessage = st[1];
					return false;
				}
			}

			return false;
		}

		protected void onPostExecute(Boolean result) {
			dismissProgressDialog();
			if (result) {
				tv_name.setText(name);
				tv_mobile_number.setText(mobile);
				tv_userid.setText(userid);
				tv_current_balance.setText(mainbal);
				tv_total_earning.setText(totalearn);

			} else {
				new AlertDialog.Builder(ProfileActivity.this)
				.setTitle("Alert Message")
				.setMessage(failedMessage)
				.setPositiveButton("Ok", new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						ProfileActivity.this.finish();
					}
				});
			}
		}
	}

	public String getParams() {
		return "tokenkey=" + app.getUserinfo().token + "&website=rechargedive.com";
	}
	
	
    @Override  
    public boolean onCreateOptionsMenu(Menu menu) {  
        // Inflate the menu; this adds items to the action bar if it is present.  
        getMenuInflater().inflate(R.menu.main, menu);//Menu Resource, Menu  
        return true;  
    }  
    @Override  
    public boolean onOptionsItemSelected(MenuItem item) {  
        switch (item.getItemId()) {  
            case R.id.log_out:                
              DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            	    @Override
            	    public void onClick(DialogInterface dialog, int which) {
            	        switch (which){
            	        case DialogInterface.BUTTON_POSITIVE:
            	            //Yes button clicked
            	        	
            	        	app.getUserinfo().clearPref();
            	        	Intent i = new Intent(ProfileActivity.this,SignInActivity.class);
            	        	i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            	        	startActivity(i);
            	            break;

            	        case DialogInterface.BUTTON_NEGATIVE:
            	            //No button clicked
            	            break;
            	        }
            	    }
            	};

            	AlertDialog.Builder builder = new AlertDialog.Builder(this);
            	builder.setMessage("Are you Want to logged out ?").setPositiveButton("Yes", dialogClickListener)
            	    .setNegativeButton("No", dialogClickListener).show();
              
              
            return true;     
          
              default:  
                return super.onOptionsItemSelected(item);  
        }  
    } 
}
