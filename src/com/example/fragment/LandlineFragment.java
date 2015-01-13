package com.example.fragment;

import java.util.ArrayList;
import java.util.List;

import com.example.constant.Constant;
import com.example.fragment.DthFragment.DthAsyanTask;
import com.example.network.RechargeHttpClient;
import com.example.recharge.BaseActivity;
import com.example.recharge.R;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class LandlineFragment extends Fragment implements OnClickListener{
	
	private BaseActivity base;
	
	private Button btn_landline_recharge ;
	private Spinner sp_landline_operator , sp_landline_route;
	private EditText et_insurance_std_code,et_insurance_landline_no ,et_landline_acc_no,et_landline_amount;
	public List<String> operatorList = new ArrayList<String>();
	public List<String> LandlineList = new ArrayList<String>();
	public List<String> routeList = new ArrayList<String>();
	private String sub_id,operator,std_code,landline_no,landline_acc_no,route_value,Landline_amount = null;
	private String st[] ;
	
	public LandlineFragment(BaseActivity base){
	this.base  = base;	
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View v = inflater.inflate(R.layout.fragment_landline, container, false);
		
		
		
		btn_landline_recharge = (Button)v.findViewById(R.id.btn_landline_recharge) ;
		sp_landline_operator = (Spinner)v.findViewById(R.id.sp_landline_operator) ;
		sp_landline_route = (Spinner)v.findViewById(R.id.sp_landline_route) ;
		et_insurance_std_code = (EditText)v.findViewById(R.id.et_insurance_std_code) ;
		et_insurance_landline_no = (EditText)v.findViewById(R.id.et_insurance_landline_no) ;
		et_landline_acc_no = (EditText)v.findViewById(R.id.et_landline_acc_no) ;
		et_landline_amount = (EditText)v.findViewById(R.id.et_landline_amount) ;
		
		btn_landline_recharge.setOnClickListener(this);
		
		
		String[] product_array = getResources().getStringArray(R.array.select_landline_operator_option);
		for (int i = 0; i < product_array.length; i++) {
			LandlineList.add(product_array[i]);
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(base, android.R.layout.simple_spinner_item, LandlineList);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp_landline_operator.setAdapter(adapter);
		sp_landline_operator.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int pos, long arg3) {
				
				if(pos == 0){
					sub_id = "ATL";
				}else if(pos == 1){
					sub_id = "BSL";
				}else if(pos == 2){
					sub_id = "RCOM";
				}else if(pos == 3){
					sub_id = "TIL";
				}else if(pos == 4){
					sub_id = "MTDL";
				}else if(pos == 5){
					sub_id = "TIK";
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
		});
		
		String[] route_array = getResources().getStringArray(R.array.root_array_option);
		for (int i = 0; i < route_array.length; i++) {
			routeList.add(route_array[i]);
		}
		ArrayAdapter<String> adapterRoute = new ArrayAdapter<String>(base, android.R.layout.simple_spinner_item, routeList);
		adapterRoute.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp_landline_route.setAdapter(adapterRoute);
		sp_landline_route.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				if(position == 0){
					route_value = "1";
				}else if(position == 1){
					route_value = "2";
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			
			}
		});
		
		
		return v;
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

		if(validateLandlineRecharge(et_insurance_std_code.getText().toString().trim(),et_insurance_landline_no.getText().toString().trim(),
				et_landline_acc_no.getText().toString().trim(),et_landline_amount.getText().toString().trim())){
			//Do Something
			Landline_amount = et_landline_amount.getText().toString().trim();
			if(base.app.getUserinfo().mode == 1){
				new LandlineAsyanTask().execute();
			}else{
				base.sendOfflineSMS(sub_id+" "+et_insurance_landline_no.getText().toString().trim()+" "+et_landline_amount.getText().toString().trim()+" "+et_landline_acc_no.getText().toString().trim()+et_insurance_std_code.getText().toString().trim());	
			}
		}
	
	}
	
	private boolean validateLandlineRecharge(String std_code,String land_no,String acc_no,String amount) {
		// TODO Auto-generated method stub
		if(std_code.length()==0){
			et_insurance_std_code.setError("Please enter Std Code");
			return false;
		}else if(land_no.length()==0){
			et_insurance_landline_no.setError("Please enter Landline No");
			return false;
		}else if(acc_no.length()==0){
			et_landline_acc_no.setError("Please enter Account No");
			return false;
		}else if(amount.length()==0){
			et_landline_amount.setError("Please enter Amount");
			return false;
		}
		return true;
	}  
	
	public class LandlineAsyanTask extends AsyncTask<Void, Void, Boolean>{

		protected void onPreExecute() {
			base.showProgressDailog();
		}

		protected Boolean doInBackground(Void... params) {

			String url = Constant.PROFILE + getParams();
			String response = RechargeHttpClient.SendHttpPost(url);
			st = response.split(",");
			if (response != null) {
				if (response.startsWith("SUCCESS")) {
					
					return true;
				}
			}
			return false;
		}

		protected void onPostExecute(Boolean result) {
			base.dismissProgressDialog();
			if (result) {
				Toast.makeText(base, "Success....", 5000).show();
			} else {
				Toast.makeText(base, "Failed.... "+st[1], 5000).show();
			}
		}		
	}
	
	public String getParams() {
		return "tokenkey=" + base.app.getUserinfo().token + "&website=rechargedive.com" + "&optcode="+sub_id + "&amount="+Landline_amount + "&route="+route_value;
	}
	  
}