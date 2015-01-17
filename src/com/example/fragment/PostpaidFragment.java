package com.example.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.constant.Constant;
import com.example.dialog.MobileRechargeDialog;
import com.example.fragment.DatacardFragment.DatacardAsyanTask;
import com.example.network.RechargeHttpClient;
import com.example.recharge.BaseActivity;
import com.example.recharge.R;

public class PostpaidFragment extends Fragment implements OnClickListener{
	
	private BaseActivity base;
	public List<String> OptList = new ArrayList<String>();
	public List<String> routeList = new ArrayList<String>();
	
	private EditText et_mobile_no,et_mobile_amount;
	private Spinner sp_mobile_operator,sp_mobile_route;
	private Button btn_mobile_recharge;
	private String product_code,route_value,recharge_amount, mobileno;
	
	private String st[] ;
	
	public PostpaidFragment(BaseActivity base){
	this.base  = base;	
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View v = inflater.inflate(R.layout.fragment_postpaid, container, false);
		et_mobile_no = (EditText)v.findViewById(R.id.et_postpaid_mobile_no);
		sp_mobile_operator = (Spinner)v.findViewById(R.id.sp_postpaid_operator);
		et_mobile_amount = (EditText)v.findViewById(R.id.et_postpaid_amount);
		sp_mobile_route = (Spinner)v.findViewById(R.id.sp_postpaid_route);
		btn_mobile_recharge = (Button)v.findViewById(R.id.btn_postpaid_recharge);
		btn_mobile_recharge.setOnClickListener(this);
		String[] mobile_array = getResources().getStringArray(R.array.postpaid_array_option);
		for (int i = 0; i < mobile_array.length; i++) {
			OptList.add(mobile_array[i]);
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(base, android.R.layout.simple_spinner_item, OptList);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp_mobile_operator.setAdapter(adapter);
		sp_mobile_operator.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int pos, long arg3) {
				
				if(pos == 0){
					product_code = "APOS";
				}else if(pos == 1){
					product_code = "BPOS";
				}else if(pos == 2){
					product_code = "IPOS";
				}else if(pos == 3){
					product_code = "VPOS";
				}else if(pos == 4){
					product_code = "RGPOS";
				}else if(pos == 5){
					product_code = "RCPOS";
				}else if(pos == 6){
					product_code = "DGPOS";
				}else if(pos == 7){
					product_code = "DCPOS";
				}else if(pos == 8){
					product_code = "LPOS";
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
		sp_mobile_route.setAdapter(adapterRoute);
		sp_mobile_route.setOnItemSelectedListener(new OnItemSelectedListener() {

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


	public void onClick(View arg0) {
	switch (arg0.getId()) {
	case R.id.btn_postpaid_recharge:
		
		if(isValid()){
			
			AlertDialog.Builder alert = new AlertDialog.Builder(base);
			alert.setMessage("Are you sure, you want to recharge now?");
			alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					if(base.app.getUserinfo().mode == 1){
						new PostPaidAsyanTask().execute();
					}else{
						base.sendOfflineSMS(product_code+" "+et_mobile_no.getText().toString().trim()+" "+et_mobile_amount.getText().toString().trim());	
					}
					
					
				}
			});
			
			alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
					dialog.dismiss();
				}
			});
			alert.show();
			
					
			
			
			
			
		
		
		}
		break;

	}
		
	}
	
	public boolean isValid(){
		if(!(et_mobile_no.getText().toString().trim().length() == 10)){
			et_mobile_no.setError("insert a valid mobile number");
			return false;
		}else if(!(et_mobile_amount.getText().toString().trim().length()>0)){
			et_mobile_amount.setError("insert a valid amount");
			return false;
		}
		return true;
	}
	
	public class PostPaidAsyanTask extends AsyncTask<Void, Void, Boolean>{

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
				et_mobile_amount.setText("");
				et_mobile_no.setText("");
				new MobileRechargeDialog(base, true, st[1], st[2], st[3], st[4], st[8]).show();
				
				
			} else {
				
				new MobileRechargeDialog(base, false, st[1]).show();
						}
		}		
	}

	
	 
	public String getParams() {
		recharge_amount = et_mobile_amount.getText().toString().trim();
		mobileno = et_mobile_no.getText().toString().trim();
		return "tokenkey="+base.app.getUserinfo().token+"&website=rechargedive.com&optcode="+product_code+"&service="+mobileno+"&amount="+recharge_amount+"&route="+route_value;
		//return "tokenkey=" + base.app.getUserinfo().token + "&website=rechargedive.com" + "&optcode="+product_code + "&amount="+recharge_amount + "&route="+route_value;
	}
	 
}


