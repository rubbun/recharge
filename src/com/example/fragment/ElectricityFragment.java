package com.example.fragment;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.constant.Constant;
import com.example.fragment.GasFragment.GasAsyanTask;
import com.example.network.RechargeHttpClient;
import com.example.recharge.BaseActivity;
import com.example.recharge.R;

@SuppressLint("ValidFragment")
public class ElectricityFragment extends Fragment implements OnClickListener{
	
	private BaseActivity base;
	private Button btn_electricity_recharge ;
	private Spinner sp_electricity_operator , sp_electricity_route;
	private EditText et_electricity_amount ,et_electricity_service_no;
	
	private String product_code,route_value = null;
	
	public List<String> operatorList = new ArrayList<String>();
	public List<String> routeList = new ArrayList<String>();
	private String st[] ;
	
	public ElectricityFragment(BaseActivity base){
	this.base  = base;	
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View v = inflater.inflate(R.layout.fragment_electricity, container, false);
		
		btn_electricity_recharge = (Button)v.findViewById(R.id.btn_electricity_recharge) ;
		sp_electricity_route = (Spinner)v.findViewById(R.id.sp_electricity_route) ;
		String[] route_array = getResources().getStringArray(R.array.root_array_option);
		for (int i = 0; i < route_array.length; i++) {
			routeList.add(route_array[i]);
		}
		ArrayAdapter<String> adapterRoute = new ArrayAdapter<String>(base, android.R.layout.simple_spinner_item, routeList);
		adapterRoute.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp_electricity_route.setAdapter(adapterRoute);
		sp_electricity_route.setOnItemSelectedListener(new OnItemSelectedListener() {

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
		
		String[] electricity_array = getResources().getStringArray(R.array.electricity_operator_option_arrays);
		for (int i = 0; i < electricity_array.length; i++) {
			operatorList.add(electricity_array[i]);
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(base, android.R.layout.simple_spinner_item, operatorList);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp_electricity_operator = (Spinner)v.findViewById(R.id.sp_electricity_operator) ;
		sp_electricity_operator.setAdapter(adapter);
		sp_electricity_operator.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int pos, long arg3) {
				if(pos == 0){
					product_code = "APE";
				}else if(pos == 1){
					product_code = "SPCL";
				}else if(pos == 2){
					product_code = "BSESR";
				}else if(pos == 3){
					product_code = "BSESY";
				}else if(pos == 4){
					product_code = "NDPL";
				}else if(pos == 5){
					product_code = "CESC";
				}else if(pos == 6){
					product_code = "CSE";
				}else if(pos == 7){
					product_code = "DGE";
				}else if(pos == 8){
					product_code = "MGE";
				}else if(pos == 9){
					product_code = "UGE";
				}else if(pos == 8){
					product_code = "PGE";
				}else if(pos == 9){
					product_code = "TPE";
				}else if(pos == 8){
					product_code = "JAE";
				}else if(pos == 9){
					product_code = "JOE";
				}else if(pos == 8){
					product_code = "MSEB";
				}else if(pos == 9){
					product_code = "REL";
				}else if(pos == 8){
					product_code = "BEST";
				}else if(pos == 9){
					product_code = "UPPCL";
				}else if(pos == 8){
					product_code = "MPEB";
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
		});
		
		et_electricity_amount = (EditText)v.findViewById(R.id.et_electricity_amount) ;
		et_electricity_service_no = (EditText)v.findViewById(R.id.et_electricity_service_no) ;
		
		btn_electricity_recharge.setOnClickListener(this);
		
		return v;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v == btn_electricity_recharge){
			if(validateElectricityRecharge(et_electricity_service_no.getText().toString().trim(),et_electricity_amount.getText().toString().trim())){
				if(base.app.getUserinfo().mode == 1){
					new ElectricityAsyanTask().execute();
				}else{
					base.sendOfflineSMS(product_code+" "+et_electricity_service_no.getText().toString().trim()+" "+et_electricity_amount.getText().toString().trim());
				}
			}
		}
	}
	
	public class ElectricityAsyanTask extends AsyncTask<Void, Void, Boolean>{

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
	
	private boolean validateElectricityRecharge(String serv_no,String amount) {
		// TODO Auto-generated method stub
		if(serv_no.length()==0){
			et_electricity_service_no.setError("Please enter Service No");
			return false;
		}else if(amount.length()==0){
			et_electricity_amount.setError("Please enter Amount");
			return false;
		}
		
		return true;
	}
	public String getParams() {
		//return "tokenkey=" + base.app.getUserinfo().token + "&website=rechargedive.com" + "&optcode="+product_code + "&amount="+recharge_amount + "&route="+route_value;
		return null;
	}
}
