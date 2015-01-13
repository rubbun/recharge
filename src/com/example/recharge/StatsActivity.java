package com.example.recharge;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class StatsActivity extends BaseActivity{
	private TextView tv_earning, tv_sale, tv_load;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stats);
		tv_earning = (TextView)findViewById(R.id.tv_earning);
		tv_sale = (TextView)findViewById(R.id.tv_sale);
		tv_load = (TextView)findViewById(R.id.tv_load);
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
            	        	Intent i = new Intent(StatsActivity.this,SignInActivity.class);
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
