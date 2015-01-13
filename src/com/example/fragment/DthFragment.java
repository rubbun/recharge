package com.example.fragment;

import java.util.ArrayList;
import java.util.List;

import com.example.constant.Constant;
import com.example.fragment.AntivirusFragment.AntivirusAsyanTask;
import com.example.network.RechargeHttpClient;
import com.example.recharge.BaseActivity;
import com.example.recharge.R;

import android.annotation.SuppressLint;
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

@SuppressLint("ValidFragment")
public class DthFragment extends Fragment implements OnClickListener{
	
	private BaseActivity base;
	
	private Button btn_dth_recharge ;
	private Spinner sp_dth_operator , sp_dth_route;
	private EditText et_dth_sub_id ,et_dth_amount;
	public List<String> operatorList = new ArrayList<String>();
	public List<String> DthList = new ArrayList<String>();
	public List<String> routeList = new ArrayList<String>();
	private String operator,sub_id,route_value,dth_amount = null;
	private String st[] ;
	
	public DthFragment(BaseActivity base){
	this.base  = base;	
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View v = inflater.inflate(R.layout.fragment_dth, container, false);
		
		btn_dth_recharge = (Button)v.findViewById(R.id.btn_dth_recharge) ;
		sp_dth_operator = (Spinner)v.findViewById(R.id.sp_dth_operator) ;
		sp_dth_route = (Spinner)v.findViewById(R.id.sp_dth_route) ;
		et_dth_sub_id = (EditText)v.findViewById(R.id.et_dth_sub_id) ;
		et_dth_amount = (EditText)v.findViewById(R.id.et_dth_amount) ;
		
		btn_dth_recharge.setOnClickListener(this);
		
		
		String[] product_array = getResources().getStringArray(R.array.dth_operator_option_arrays);
		for (int i = 0; i < product_array.length; i++) {
			DthList.add(product_array[i]);
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(base, android.R.layout.simple_spinner_item, DthList);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		//sp_dth_operator = (Spinner)v.findViewById(R.id.sp_antivirus_product) ;
		sp_dth_operator.setAdapter(adapter);
		sp_dth_operator.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int pos, long arg3) {
				
				if(pos == 0){
					sub_id = "AD";
				}else if(pos == 1){
					sub_id = "SD";
				}else if(pos == 2){
					sub_id = "TS";
				}else if(pos == 3){
					sub_id = "DT";
				}else if(pos == 4){
					sub_id = "BT";
				}else if(pos == 5){
					sub_id = "VT";
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
		//sp_dth_route = (Spinner)v.findViewById(R.id.sp_antivirus_route) ;
		sp_dth_route.setAdapter(adapterRoute);
		sp_dth_route.setOnItemSelectedListener(new OnItemSelectedListener() {

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
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v == btn_dth_recharge){
			if(validateDthRecharge(et_dth_sub_id.getText().toString().trim(),et_dth_amount.getText().toString().trim())){
				//Do Something
				dth_amount = et_dth_amount.getText().toString().trim();
				if(base.app.getUserinfo().mode == 1){
					new DthAsyanTask().execute();
				}else{
					base.sendOfflineSMS(sub_id+" "+et_dth_sub_id.getText().toString().trim()+" "+et_dth_amount.getText().toString().trim());
				}
			}
		}
	}
	
	private boolean validateDthRecharge(String sub_id,String amount) {
		// TODO Auto-generated method stub
		if(sub_id.length()==0){
			et_dth_sub_id.setError("Please enter Sub Id");
			return false;
		}else if(amount.length()==0){
			et_dth_amount.setError("Please enter Amount");
			return false;
		}
		return true;
	}  
	
	public class DthAsyanTask extends AsyncTask<Void, Void, Boolean>{

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
		return "tokenkey=" + base.app.getUserinfo().token + "&website=rechargedive.com" + "&optcode="+sub_id + "&amount="+dth_amount + "&route="+route_value;
	}
}
