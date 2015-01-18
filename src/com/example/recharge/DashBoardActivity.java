package com.example.recharge;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

public class DashBoardActivity extends BaseActivity {

	private LinearLayout ll_services, ll_transhistory, ll_dispute, ll_profile, ll_stats;
	private Intent mIntent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ll_services = (LinearLayout) findViewById(R.id.ll_services);
		ll_services.setOnClickListener(this);

		ll_transhistory = (LinearLayout) findViewById(R.id.ll_transhistory);
		ll_transhistory.setOnClickListener(this);

		ll_dispute = (LinearLayout) findViewById(R.id.ll_dispute);
		ll_dispute.setOnClickListener(this);

		ll_profile = (LinearLayout) findViewById(R.id.ll_profile);
		ll_profile.setOnClickListener(this);

		ll_stats = (LinearLayout) findViewById(R.id.ll_stats);
		ll_stats.setOnClickListener(this);

	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.ll_services:

			mIntent = new Intent(DashBoardActivity.this, ServiceActivity.class);
			startActivity(mIntent);

			break;
		case R.id.ll_transhistory:

			mIntent = new Intent(DashBoardActivity.this, TransactionHistory.class);
			startActivity(mIntent);

			break;
		case R.id.ll_dispute:

			mIntent = new Intent(DashBoardActivity.this, DisputeActivity.class);
			startActivity(mIntent);

			break;
		case R.id.ll_profile:

			mIntent = new Intent(DashBoardActivity.this, ProfileActivity.class);
			startActivity(mIntent);

			break;
		case R.id.ll_stats:

			mIntent = new Intent(DashBoardActivity.this, StatsActivity.class);
			startActivity(mIntent);

			break;
		}
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
            	        	Intent i = new Intent(DashBoardActivity.this,SignInActivity.class);
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
