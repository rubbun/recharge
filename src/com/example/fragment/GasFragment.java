package com.example.fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.constant.Constant;
import com.example.dialog.MobileRechargeDialog;
import com.example.network.RechargeHttpClient;
import com.example.recharge.BaseActivity;
import com.example.recharge.IDateDialog;
import com.example.recharge.R;

@SuppressLint("ValidFragment")
public class GasFragment extends Fragment implements OnClickListener{
	
	private BaseActivity base;
	
	private Button btn_gas_recharge ;
	private Spinner sp_gas_operator , sp_gas_route;
	private EditText et_gas_service_no ,et_gas_amount;
	
	private String operator_code,route_value = null;
	
	public List<String> operatorList = new ArrayList<String>();
	public List<String> routeList = new ArrayList<String>();
	private String st[] ;
	
	private LinearLayout ll_date;
	private TextView tv_date;
	private IDateDialog datelistener;
	
	public GasFragment(BaseActivity base ,IDateDialog datelistener){
	this.base  = base;	
	this.datelistener = datelistener;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View v = inflater.inflate(R.layout.fragment_gas, container, false);
		
		btn_gas_recharge = (Button)v.findViewById(R.id.btn_gas_recharge) ;
		
		sp_gas_operator = (Spinner)v.findViewById(R.id.sp_gas_operator) ;
		String[] gas_array = getResources().getStringArray(R.array.gas_recharge_operator_option); 
		for(int i = 0; i <gas_array.length ; i++){
			operatorList.add(gas_array[i]);
		}
		ArrayAdapter<String> operatorAdapter = new ArrayAdapter<String>(base, android.R.layout.simple_spinner_item, operatorList);
		operatorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp_gas_operator.setAdapter(operatorAdapter);
		sp_gas_operator.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int pos, long arg3) {
				if(pos == 0){
					operator_code = "IGL";
				}else if(pos == 1){
					operator_code = "MGL";
				}else if(pos == 2){
					operator_code = "GGL";
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
		});
		
		sp_gas_route = (Spinner)v.findViewById(R.id.sp_gas_route) ;
		String[] route_array = getResources().getStringArray(R.array.root_array_option);
		for (int i = 0; i < route_array.length; i++) {
			routeList.add(route_array[i]);
		}
		ArrayAdapter<String> adapterRoute = new ArrayAdapter<String>(base, android.R.layout.simple_spinner_item, routeList);
		adapterRoute.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp_gas_route.setAdapter(adapterRoute);
		sp_gas_route.setOnItemSelectedListener(new OnItemSelectedListener() {

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
		
		et_gas_service_no = (EditText)v.findViewById(R.id.et_gas_service_no) ;
		et_gas_amount = (EditText)v.findViewById(R.id.et_gas_amount) ;
		
		btn_gas_recharge.setOnClickListener(this);
		ll_date = (LinearLayout)v.findViewById(R.id.ll_date);
		ll_date.setOnClickListener(this);
		tv_date = (TextView)v.findViewById(R.id.tv_date);
		if(base.app.getUserinfo().mode == 1){
			ll_date.setVisibility(View.GONE);
		}else{
			ll_date.setVisibility(View.VISIBLE);
		}
		return v;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v == btn_gas_recharge){
			if(validateGasRecharge(et_gas_service_no.getText().toString().trim(),et_gas_amount.getText().toString().trim())){
				
				

				AlertDialog.Builder alert = new AlertDialog.Builder(base);
				alert.setMessage("Are you sure, you want to recharge now?");
				alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						if(base.app.getUserinfo().mode == 1){
							new GasAsyanTask().execute();
						}else{
							
							if(tv_date.getText().toString().trim().equalsIgnoreCase("Enter Due Date")){
								Toast.makeText(base, "Please enter Date", 5000).show();
							}else{
								base.sendOfflineSMS(operator_code+" "+et_gas_service_no.getText().toString().trim()+" "+et_gas_amount.getText().toString().trim() +" "+tv_date.getText().toString().trim().replaceAll("-", ""));
							}
							
							
							
							
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
		}
		
		if(v == ll_date){
			datelistener.onDateset(tv_date);
		}
	}
	
	public class GasAsyanTask extends AsyncTask<Void, Void, Boolean>{

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
				et_gas_service_no.setText("");
				et_gas_amount.setText("");
				new MobileRechargeDialog(base, true, st[1], st[2], st[3], st[4], st[8]).show();
				
				
			} else {
				
				new MobileRechargeDialog(base, false, st[1]).show();
				}
		}		
	}

	
	private boolean validateGasRecharge(String ser_id,String amount) {
		// TODO Auto-generated method stub
		if(ser_id.length()==0){
			et_gas_service_no.setError("Please enter Service Id");
			return false;
		}else if(amount.length()==0){
			et_gas_amount.setError("Please enter Amount");
			return false;
		}
		
		return true;
	}
	
	public String getParams() {
		String serviceNo = et_gas_service_no.getText().toString().trim();
		String amount = et_gas_amount.getText().toString().trim();
		return "tokenkey="+base.app.getUserinfo().token+"&website=rechargedive.com&optcode="+operator_code+"&service="+serviceNo+"&amount="+amount+"&route="+route_value;
	}
	  
}
