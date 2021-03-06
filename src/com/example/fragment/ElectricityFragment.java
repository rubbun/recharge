package com.example.fragment;

import java.util.ArrayList;
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
public class ElectricityFragment extends Fragment implements OnClickListener{
	
	private BaseActivity base;
	private Button btn_electricity_recharge ;
	private Spinner sp_electricity_operator , sp_electricity_route;
	private EditText et_electricity_amount ,et_electricity_service_no;/*,et_billing_unit,et_processing_cycle,et_electricity_city;*/
	
	private String product_code,route_value = null;
	
	public List<String> operatorList = new ArrayList<String>();
	public List<String> routeList = new ArrayList<String>();
	public List<String> cityList = new ArrayList<String>();
	public List<String> billingunitList = new ArrayList<String>();
	public List<String> processionCycleList = new ArrayList<String>();
	private String st[] ;
	private LinearLayout ll_date,ll_billing_unit,ll_processing_cycle,ll_city;
	private TextView tv_date;
	private IDateDialog datelistener;
	
	private Spinner sp_electricity_city,sp_electricity_billing_unit,sp_electricity_processing_cycle;
	
	private String city, billingunit, processingcycle;

	
	public ElectricityFragment(BaseActivity base,IDateDialog datelistener){
	this.base  = base;
	this.datelistener = datelistener;
	
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
				}else if(pos == 10){
					product_code = "PGE";
				}else if(pos == 11){
					product_code = "TPE";
				}else if(pos == 12){
					product_code = "JAE";
				}else if(pos == 13){
					product_code = "JOE";
				}else if(pos == 14){
					product_code = "MSEB";
				}else if(pos == 15){
					product_code = "REL";
				}else if(pos == 16){
					product_code = "BEST";
				}else if(pos == 17){
					product_code = "UPPCL";
				}else if(pos == 18){ }
				
				if(product_code.equals("MSEB")){
					ll_billing_unit.setVisibility(View.VISIBLE);
					ll_processing_cycle.setVisibility(View.VISIBLE);
					ll_city.setVisibility(View.GONE);
				}else if(product_code.equals("TPE")){
					ll_billing_unit.setVisibility(View.GONE);
					ll_processing_cycle.setVisibility(View.GONE);
					ll_city.setVisibility(View.VISIBLE);
				}else{
					ll_billing_unit.setVisibility(View.GONE);
					ll_processing_cycle.setVisibility(View.GONE);
					ll_city.setVisibility(View.GONE);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
		});
		
		et_electricity_amount = (EditText)v.findViewById(R.id.et_electricity_amount) ;
		et_electricity_service_no = (EditText)v.findViewById(R.id.et_electricity_service_no) ;
		sp_electricity_city = (Spinner)v.findViewById(R.id.sp_electricity_city) ;
		sp_electricity_billing_unit = (Spinner)v.findViewById(R.id.sp_electricity_billing_unit) ;
		sp_electricity_processing_cycle = (Spinner)v.findViewById(R.id.sp_electricity_processing_cycle) ;
		
		btn_electricity_recharge.setOnClickListener(this);
		ll_date = (LinearLayout)v.findViewById(R.id.ll_date);
		ll_billing_unit = (LinearLayout)v.findViewById(R.id.ll_billing_unit);
		ll_processing_cycle = (LinearLayout)v.findViewById(R.id.ll_processing_cycle);
		ll_city = (LinearLayout)v.findViewById(R.id.ll_city);
		ll_date.setOnClickListener(this);
		tv_date = (TextView)v.findViewById(R.id.tv_date);
		if(base.app.getUserinfo().mode == 1){
			ll_date.setVisibility(View.VISIBLE);
		}else{
			ll_date.setVisibility(View.VISIBLE);
		}
		
		
		
		String[] city_array = getResources().getStringArray(R.array.electric_bill_city);
		for (int i = 0; i < city_array.length; i++) {
			cityList.add(city_array[i]);
		}
		ArrayAdapter<String> adaptercity = new ArrayAdapter<String>(base, android.R.layout.simple_spinner_item, cityList);
		adaptercity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp_electricity_city.setAdapter(adaptercity);
		sp_electricity_city.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				System.out.println(cityList.get(position));
				city = cityList.get(position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
		});
		
		
		String[] bill_array = getResources().getStringArray(R.array.billing_unit_arr);
		for (int i = 0; i < bill_array.length; i++) {
			billingunitList.add(bill_array[i]);
		}
		ArrayAdapter<String> adapterBillingUnit = new ArrayAdapter<String>(base, android.R.layout.simple_spinner_item, billingunitList);
		adapterBillingUnit.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp_electricity_billing_unit.setAdapter(adapterBillingUnit);
		sp_electricity_billing_unit.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				System.out.println(billingunitList.get(position));
				billingunit = billingunitList.get(position);
				billingunit = billingunit.replace(" ", "%20");
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
		});
		
		
		
		
		
		
		String[] pc_array = getResources().getStringArray(R.array.electric_bill_processing_cycle);
		for (int i = 0; i < pc_array.length; i++) {
			processionCycleList.add(pc_array[i]);
		}
		ArrayAdapter<String> adapterPs = new ArrayAdapter<String>(base, android.R.layout.simple_spinner_item, processionCycleList);
		adapterPs.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp_electricity_processing_cycle.setAdapter(adapterPs);
		sp_electricity_processing_cycle.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				System.out.println(processionCycleList.get(position));
				processingcycle = processionCycleList.get(position);
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
		if(v == btn_electricity_recharge){
			
			if(validateElectricityRecharge(et_electricity_service_no.getText().toString().trim(),et_electricity_amount.getText().toString().trim())){
				
				
				
				
				AlertDialog.Builder alert = new AlertDialog.Builder(base);
				alert.setMessage("Are you sure, you want to recharge now?");
				alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						if(base.app.getUserinfo().mode == 1){
							new ElectricityAsyanTask().execute();
						}else{
							
							if(tv_date.getText().toString().trim().equalsIgnoreCase("Enter Due Date")){
								Toast.makeText(base, "Please enter Date", 5000).show();
							}else{								
								if(product_code.equals("MSEB")){
									base.sendOfflineSMS(product_code+" "+et_electricity_service_no.getText().toString().trim()+" "+et_electricity_amount.getText().toString().trim()+" "+tv_date.getText().toString().trim().replaceAll("-", "")+" "+billingunit+" "+processingcycle);
								}else if(product_code.equals("TPE")){
									base.sendOfflineSMS(product_code+" "+et_electricity_service_no.getText().toString().trim()+" "+et_electricity_amount.getText().toString().trim()+" "+tv_date.getText().toString().trim().replaceAll("-", "")+" "+city);
								}else{
									base.sendOfflineSMS(product_code+" "+et_electricity_service_no.getText().toString().trim()+" "+et_electricity_amount.getText().toString().trim()+" "+tv_date.getText().toString().trim().replaceAll("-", ""));
								}
								
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
				et_electricity_service_no.setText("");
				et_electricity_amount.setText("");
				new MobileRechargeDialog(base, true, st[1], st[2], st[3], st[4], st[8]).show();
				
				
			} else {
				
				new MobileRechargeDialog(base, false, st[1]).show();
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
		}else if(tv_date.getText().toString().trim().equalsIgnoreCase("Enter Due Date")){
			Toast.makeText(base, "Please enter Date", 5000).show();
			return false;
		}
		
		
		
		return true;
	}
	public String getParams() {
		
		String serviceNo = et_electricity_service_no.getText().toString().trim();
		String amount = et_electricity_amount.getText().toString().trim();
		String duedate = tv_date.getText().toString().trim();
		if(product_code.equals("MSEB")){
			return "tokenkey="+base.app.getUserinfo().token+"&website=rechargedive.com&optcode="+product_code+"&service="+serviceNo+"&amount="+amount+"&route="+route_value+"&other1="+tv_date.getText().toString().trim().replaceAll("-", "")+"&other2="+billingunit+"&other3="+processingcycle;
		}else if(product_code.equals("TPE")){
			return "tokenkey="+base.app.getUserinfo().token+"&website=rechargedive.com&optcode="+product_code+"&service="+serviceNo+"&amount="+amount+"&route="+route_value+"&other1="+tv_date.getText().toString().trim().replaceAll("-", "")+"&other2="+city;
		}else{
			return "tokenkey="+base.app.getUserinfo().token+"&website=rechargedive.com&optcode="+product_code+"&service="+serviceNo+"&amount="+amount+"&route="+route_value+"&other1="+tv_date.getText().toString().trim().replaceAll("-", "");
		}
		
	}
}
