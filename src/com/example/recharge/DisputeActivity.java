package com.example.recharge;

import com.example.constant.Constant;
import com.example.network.RechargeHttpClient;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DisputeActivity extends BaseActivity{
	
	private EditText et_order_number;
	private Button btn_dispute;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dispute);
		et_order_number =(EditText)findViewById(R.id.et_order_number);
		btn_dispute = (Button)findViewById(R.id.btn_dispute);
		btn_dispute.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.btn_dispute:
			if(isvalid()){
				new DisputeAsyncTask().execute();
			}
			break;

		}
	}
	
	public boolean isvalid() {
		if (!(et_order_number.getText().toString().trim().length()>0)) {
			et_order_number.setError("Please enter valid order number");
			return false;
		}
		return true;

	}
	
	public class DisputeAsyncTask extends AsyncTask<Void, Void, Boolean> {

		protected void onPreExecute() {
			showProgressDailog();
		}

		protected Boolean doInBackground(Void... params) {

			String url = Constant.DISPUTE + getParams();
			String response = RechargeHttpClient.SendHttpPost(url);
			if (response != null) {
				if (response.startsWith("SUCCESS")) {
					return true;
				}
			}

			return false;
		}

		protected void onPostExecute(Boolean result) {
			dismissProgressDialog();
			if (result) {
				Toast.makeText(getApplicationContext(), "Successfully Submit Order", Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(getApplicationContext(), "failed to Submit Order", Toast.LENGTH_LONG).show();
				
			}
		}
	}

	public String getParams() {
		String ordernumber = et_order_number.getText().toString().trim();
		return "tokenkey="+app.getUserinfo().token+"&website=rechargedive.com&orderid="+ordernumber;
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
            	        	Intent i = new Intent(DisputeActivity.this,SignInActivity.class);
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
