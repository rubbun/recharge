package com.example.fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
import android.widget.Spinner;
import android.widget.Toast;

import com.example.constant.Constant;
import com.example.network.RechargeHttpClient;
import com.example.recharge.BaseActivity;
import com.example.recharge.R;

public class InsuranceFragment extends Fragment implements OnClickListener{
	
	private BaseActivity base;
	private EditText et_insurance_service_no,et_insurance_amount;
	private Spinner sp_insurance_operator,sp_insurance_route;
	private Button btn_insurance_recharge;
	public List<String> IncList = new ArrayList<String>();
	public List<String> RouteList = new ArrayList<String>();
	
	private String insurance_code,route_value = null;
	
	public List<String> operatorList = new ArrayList<String>();
	public List<String> routeList = new ArrayList<String>();
	private String st[] ;
	
	public InsuranceFragment(BaseActivity base){
	this.base  = base;	
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View v = inflater.inflate(R.layout.fragment_insurance, container, false);
		et_insurance_service_no = (EditText)v.findViewById(R.id.et_insurance_service_no);
		sp_insurance_operator = (Spinner)v.findViewById(R.id.sp_insurance_operator);
		String[] insurance_array = getResources().getStringArray(R.array.insurance_operator_option); 
		for(int i = 0; i <insurance_array.length ; i++){
			operatorList.add(insurance_array[i]);
		}
		ArrayAdapter<String> operatorAdapter = new ArrayAdapter<String>(base, android.R.layout.simple_spinner_item, operatorList);
		operatorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp_insurance_operator.setAdapter(operatorAdapter);
		sp_insurance_operator.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int pos, long arg3) {
				if(pos == 0){
					insurance_code = "IPL";
				}else if(pos == 1){
					insurance_code = "TAL";
				}else if(pos == 2){
					insurance_code = "PMI";
				}else if(pos == 3){
					insurance_code = "LIC";
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
		});
		
		et_insurance_amount = (EditText)v.findViewById(R.id.et_insurance_amount);
		sp_insurance_route = (Spinner)v.findViewById(R.id.sp_insurance_route);
		String[] route_array = getResources().getStringArray(R.array.root_array_option);
		for (int i = 0; i < route_array.length; i++) {
			routeList.add(route_array[i]);
		}
		ArrayAdapter<String> adapterRoute = new ArrayAdapter<String>(base, android.R.layout.simple_spinner_item, routeList);
		adapterRoute.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp_insurance_route.setAdapter(adapterRoute);
		sp_insurance_route.setOnItemSelectedListener(new OnItemSelectedListener() {

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
		
		btn_insurance_recharge = (Button)v.findViewById(R.id.btn_insurance_recharge);
		btn_insurance_recharge.setOnClickListener(this);
		
		return v;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_insurance_recharge:
			if(isvalid()){
				if(base.app.getUserinfo().mode == 1){
					new InsuranceAsyanTask().execute();
				}else{
					Calendar c = Calendar.getInstance();
					SimpleDateFormat df = new SimpleDateFormat("ddMMyyyy");
			        String formattedDate = df.format(c.getTime());
					base.sendOfflineSMS(insurance_code+" "+et_insurance_service_no.getText().toString().trim()+" "+et_insurance_amount.getText().toString().trim() +" "+formattedDate);	
				}
			}
			break;

		}
	}
	public class InsuranceAsyanTask extends AsyncTask<Void, Void, Boolean>{

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
	
	public boolean isvalid(){
		if(et_insurance_service_no.getText().toString().trim().length() == 0){
			et_insurance_service_no.setError("Enetr valid service number");
			return false;
		}else if(et_insurance_amount.getText().toString().trim().length() == 0){
			et_insurance_amount.setError("Please enter amount");
			return false;
		}
		return true;
	}
	
	public String getParams() {
		//return "tokenkey=" + base.app.getUserinfo().token + "&website=rechargedive.com" + "&optcode="+product_code + "&amount="+recharge_amount + "&route="+route_value;
		return null;
	}
}
