package com.example.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

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
import android.widget.Spinner;
import android.widget.Toast;

import com.example.constant.Constant;
import com.example.dialog.MobileRechargeDialog;
import com.example.fragment.DthFragment.DthAsyanTask;
import com.example.network.RechargeHttpClient;
import com.example.recharge.BaseActivity;
import com.example.recharge.R;
 ;
@SuppressLint("ValidFragment")
public class AntivirusFragment extends Fragment implements OnClickListener{
	
	private Button btn_antivirus_recharge ;
	private Spinner sp_antivirus_product , sp_antivirus_route;
	private EditText et_customer_mobile,et_email_id,et_name ;
	private String product_code,route_value,recharge_amount = null;
	
	private BaseActivity base;
	public List<String> productList = new ArrayList<String>();
	public List<String> routeList = new ArrayList<String>();
	private String st[] ;
	
	private int price;
	public static final String EMAIL_PATTERN ="^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
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
					price = 599;
				}else if(pos == 1){
					product_code = "NR2";
					price = 1399;
				}else if(pos == 2){
					product_code = "QH1";
					price = 599;
				}else if(pos == 3){
					product_code = "QH2";
					price = 599;
				}else if(pos == 4){
					product_code = "QH3";
					price = 749;
				}else if(pos == 5){
					product_code = "QH4";
					price = 849;
				}else if(pos == 6){
					price = 899;
					product_code = "QH5";
				}else if(pos == 7){
					price = 899;
					product_code = "QH6";
				}else if(pos == 8){
					price = 1149;
					product_code = "QH7";
				}else if(pos == 9){
					price = 1249;
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
		
		et_customer_mobile = (EditText)v.findViewById(R.id.et_customer_mobile) ;
		et_email_id = (EditText)v.findViewById(R.id.et_email_id) ;
		et_name = (EditText)v.findViewById(R.id.et_name) ;
		btn_antivirus_recharge.setOnClickListener(this);
		
		return v;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
			if(v == btn_antivirus_recharge){
				if(isvalid()){
					
					
					AlertDialog.Builder alert = new AlertDialog.Builder(base);
					alert.setMessage("Are you sure, you want to recharge now?");
					alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							if(base.app.getUserinfo().mode == 1){
								new AntivirusAsyanTask().execute();
							}else{
								base.sendOfflineSMS(product_code+" "+et_customer_mobile.getText().toString().trim()+" "+et_email_id.getText().toString().trim()+" "+et_name.getText().toString().trim());
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
	}
	
	public class AntivirusAsyanTask extends AsyncTask<Void, Void, Boolean>{

		protected void onPreExecute() {
			base.showProgressDailog();
		}

		protected Boolean doInBackground(Void... params) {

			String url = Constant.SERVICE + getParams();
			System.out.println("!! +"+url);
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
				et_customer_mobile.setText("");
				et_email_id.setText("");
				et_name.setText("");
				new MobileRechargeDialog(base, true, st[1], st[2], st[3], st[4], st[8]).show();
				
				
			} else {
				
				new MobileRechargeDialog(base, false, st[1]).show();
						}
		}		
	}

	
	 
	public String getParams() {
		
		String mobile = et_customer_mobile.getText().toString().trim();
		String email = et_email_id.getText().toString().trim();
		String name = et_name.getText().toString().trim();
		return "tokenkey=" + base.app.getUserinfo().token + "&website=rechargedive.com&optcode="+product_code+"&service="+mobile+"&amount="+price+"&route="+route_value+"&other1="+email+"&other2="+name.replaceAll(" ", "%20");
	}
	
	public boolean isvalid(){
		boolean flag = true;
		if(et_customer_mobile.getText().toString().trim().length()<10){
			et_customer_mobile.setError("Please enter valid phone number");
			flag = false;
		}else if(!isvalidMailid(et_email_id.getText().toString().trim())){
			et_email_id.setError("Please enter valid email");
			flag = false;
		}else if(et_name.getText().toString().trim().length()==0){
			et_name.setError("Please enter valid email");
			flag = false;
		}
		return flag;
	}
	
	public  boolean isvalidMailid(String mail) {		
		return Pattern.compile(EMAIL_PATTERN).matcher(mail).matches();
	}
}
