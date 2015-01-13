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
 ;
@SuppressLint("ValidFragment")
public class AntivirusFragment extends Fragment implements OnClickListener{
	
	private Button btn_antivirus_recharge ;
	private Spinner sp_antivirus_product , sp_antivirus_route;
	private EditText et_antivirus_amount ;
	private String product_code,route_value,recharge_amount = null;
	
	private BaseActivity base;
	public List<String> productList = new ArrayList<String>();
	public List<String> routeList = new ArrayList<String>();
	private String st[] ;
	
	@SuppressLint("ValidFragment")
	public AntivirusFragment(BaseActivity base){
	this.base  = base;	
	
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View v = inflater.inflate(R.layout.fragment_antivirus, container, false);
		
		btn_antivirus_recharge = (Button)v.findViewById(R.id.btn_antivirus_recharge) ;
		
		String[] product_array = getResources().getStringArray(R.array.antivirus_products_option_arrays);
		for (int i = 0; i < product_array.length; i++) {
			productList.add(product_array[i]);
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(base, android.R.layout.simple_spinner_item, productList);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp_antivirus_product = (Spinner)v.findViewById(R.id.sp_antivirus_product) ;
		sp_antivirus_product.setAdapter(adapter);
		sp_antivirus_product.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int pos, long arg3) {
				
				if(pos == 0){
					product_code = "NR1";
				}else if(pos == 1){
					product_code = "NR2";
				}else if(pos == 2){
					product_code = "QH1";
				}else if(pos == 3){
					product_code = "QH2";
				}else if(pos == 4){
					product_code = "QH3";
				}else if(pos == 5){
					product_code = "QH4";
				}else if(pos == 6){
					product_code = "QH5";
				}else if(pos == 7){
					product_code = "QH6";
				}else if(pos == 8){
					product_code = "QH7";
				}else if(pos == 9){
					product_code = "QH8";
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
		sp_antivirus_route = (Spinner)v.findViewById(R.id.sp_antivirus_route) ;
		sp_antivirus_route.setAdapter(adapterRoute);
		sp_antivirus_route.setOnItemSelectedListener(new OnItemSelectedListener() {

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
		
		et_antivirus_amount = (EditText)v.findViewById(R.id.et_antivirus_amount) ;
		btn_antivirus_recharge.setOnClickListener(this);
		
		return v;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
			if(v == btn_antivirus_recharge){
				if(validateAntivirusRecharge(et_antivirus_amount.getText().toString().trim())){
					recharge_amount = et_antivirus_amount.getText().toString().trim();
					if(base.app.getUserinfo().mode == 1){
						new AntivirusAsyanTask().execute();
					}else{
						base.sendOfflineSMS(product_code+" ");
					}
				}
			}
	}
	
	public class AntivirusAsyanTask extends AsyncTask<Void, Void, Boolean>{

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

	private boolean validateAntivirusRecharge(String amount) {
		// TODO Auto-generated method stub
		if(amount.length()==0){
			et_antivirus_amount.setError("Please enter Amount");
			return false;
		}
		return true;
	}
	 
	public String getParams() {
		return "tokenkey=" + base.app.getUserinfo().token + "&website=rechargedive.com" + "&optcode="+product_code + "&amount="+recharge_amount + "&route="+route_value;
	}
}
