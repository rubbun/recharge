package com.example.fragment;

import java.util.ArrayList;
import java.util.List;

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

import com.example.constant.Constant;
import com.example.fragment.AntivirusFragment.AntivirusAsyanTask;
import com.example.network.RechargeHttpClient;
import com.example.recharge.BaseActivity;
import com.example.recharge.R;

public class DatacardFragment extends Fragment implements OnClickListener{
	
	private BaseActivity base;
	private EditText et_datacard_phone;
	private Spinner sp_datacard_operator;
	private EditText et_datacard_amount;
	private Spinner sp_datacard_route;
	private Button btn_datacard_recharge;
	public List<String> OptList = new ArrayList<String>();
	public List<String> RouteList = new ArrayList<String>();
	private String product_code,route_value;
	private String st[] ;
	
	public DatacardFragment(BaseActivity base){
	this.base  = base;	
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View v = inflater.inflate(R.layout.fragment_datacard, container, false);
		et_datacard_phone = (EditText)v.findViewById(R.id.et_datacard_phone);
		sp_datacard_operator = (Spinner)v.findViewById(R.id.sp_datacard_operator);
		et_datacard_amount = (EditText)v.findViewById(R.id.et_datacard_amount);
		sp_datacard_route = (Spinner)v.findViewById(R.id.sp_datacard_route);
		btn_datacard_recharge = (Button)v.findViewById(R.id.btn_datacard_recharge);
		btn_datacard_recharge.setOnClickListener(this);
		
		String[] operator_list = getResources().getStringArray(R.array.datacard_recharge_operator_option);
		String[] route_list = getResources().getStringArray(R.array.root_array_option);

		for (int i = 0; i < operator_list.length; i++) {
			OptList.add(operator_list[i]);
		}
		
		for (int i = 0; i < route_list.length; i++) {
			RouteList.add(route_list[i]);
		}
		
		ArrayAdapter<String> adapter_operator = new ArrayAdapter<String>(base, android.R.layout.simple_spinner_item, OptList);
		adapter_operator.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp_datacard_operator.setAdapter(adapter_operator);
		
		
		ArrayAdapter<String> adapter_route = new ArrayAdapter<String>(base, android.R.layout.simple_spinner_item, RouteList);
		adapter_route.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp_datacard_route.setAdapter(adapter_route);

		sp_datacard_operator.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int pos, long arg3) {
				if(pos == 0){
					product_code = "ATD";
				}else if(pos == 1){
					product_code = "BSD";
				}else if(pos == 2){
					product_code = "IDXD";
				}else if(pos == 3){
					product_code = "VFD";
				}else if(pos == 4){
					product_code = "RLD";
				}else if(pos == 5){
					product_code = "UND";
				}else if(pos == 6){
					product_code = "MSD";
				}else if(pos == 7){
					product_code = "ALD";
				}else if(pos == 8){
					product_code = "TID";
				}else if(pos == 9){
					product_code = "MTDD";
				}else if(pos == 10){
					product_code = "MTMD";
				}else if(pos == 11){
					product_code = "LMD";
				}else if(pos == 12){
					product_code = "VDD";
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});
		
		sp_datacard_route.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
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
		switch (v.getId()) {
		case R.id.btn_datacard_recharge:
			if(isvalid()){
				if(base.app.getUserinfo().mode == 1){
					new DatacardAsyanTask().execute();
				}else{
					base.sendOfflineSMS(product_code+" "+et_datacard_amount.getText().toString().trim()+" "+et_datacard_phone.getText().toString().trim());
				}			
				}
			break;

	
		}
		
	}
	
	public boolean isvalid(){
		if(!(et_datacard_phone.getText().toString().trim().length() == 10)){
			et_datacard_phone.setError("Enetr valid mobile number");
			return false;
		}else if(et_datacard_amount.getText().toString().trim().length() == 0){
			et_datacard_amount.setError("Please enter amount");
			return false;
		}
		return true;
	}
	
	public class DatacardAsyanTask extends AsyncTask<Void, Void, Boolean>{

		protected void onPreExecute() {
			base.showProgressDailog();
		}

		protected Boolean doInBackground(Void... params) {

			String url = Constant.SERVICE + getParams();
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
		String recharge_amount = et_datacard_phone.getText().toString().trim();
		String mobileno = et_datacard_amount.getText().toString().trim();
		return "tokenkey="+base.app.getUserinfo().token+"&website=rechargedive.com&optcode="+product_code+"&service="+mobileno+"&amount="+recharge_amount+"&route="+route_value;
		//return "tokenkey=" + base.app.getUserinfo().token + "&website=rechargedive.com" + "&optcode="+product_code + "&amount="+recharge_amount + "&route="+route_value;
	}
	 
}
