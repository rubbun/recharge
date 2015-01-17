package com.example.recharge;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.fragment.AntivirusFragment;
import com.example.fragment.DatacardFragment;
import com.example.fragment.DthFragment;
import com.example.fragment.ElectricityFragment;
import com.example.fragment.GasFragment;
import com.example.fragment.InsuranceFragment;
import com.example.fragment.LandlineFragment;
import com.example.fragment.MobileFragment;
import com.example.fragment.PostpaidFragment;

public class ServiceActivity extends BaseActivity {

	public List<String> list = new ArrayList<String>();
	private Spinner services;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_service);

		services = (Spinner) findViewById(R.id.spinner_services);

		String[] services_list = getResources().getStringArray(R.array.service_arrays);

		for (int i = 0; i < services_list.length; i++) {
			list.add(services_list[i]);
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, list);
		adapter.setDropDownViewResource(R.layout.spinner_item);
		services.setAdapter(adapter);
		
		services.setSelection(0);

		services.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				displayView(arg2);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});
	}

	private void displayView(int position) {
		Fragment fragment = null;
		switch (position) {
		
		case 0:
			fragment = new MobileFragment(this);
			break;
			
		case 1:
			fragment = new DthFragment(this);
			break;
			
		case 2:
			fragment = new DatacardFragment(this);
			break;
		case 3:
			fragment = new PostpaidFragment(this);
			break;
		case 4:
			fragment = new LandlineFragment(this);
			break;
		case 5:
			fragment = new ElectricityFragment(this);
			break;	
		case 6:
			fragment = new GasFragment(this);
			break;	
		case 7:
			fragment = new InsuranceFragment(this);
			break;
		case 8:
			fragment = new AntivirusFragment(this);
			break;			
		
		
			
		}

		if (fragment != null) {
			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();

		} else {
			Log.e("MainActivity", "Error in creating fragment");
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
            	        	Intent i = new Intent(ServiceActivity.this,SignInActivity.class);
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
