package com.example.recharge;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adapter.TransHistoryAdapter;
import com.example.bean.HistoryBean;
import com.example.constant.Constant;

public class TransactionHistory extends BaseActivity {

	private LinearLayout ll_history_list;
	private LinearLayout ll_start_date, ll_end_date;
	private Button btn_history_search;
	private TextView tv_start_date,tv_end_date;
	private Spinner spinner_transtion_history;

	static final int DATE_PICKER_ID = 1111;
	private String year;
	private String month;
	private String day;
	
	private int current_year;
	private int current_month;
	private int current_day;
	
	private String start_date,end_date;
	
	private int selection_type = 0; 
	String selection_value = null;
	
	private TransHistoryAdapter adapter;
	private ArrayList<HistoryBean> arrayList = new ArrayList<HistoryBean>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_transaction);
		
		// Get current date by calender
        final Calendar c = Calendar.getInstance();
        current_year  = c.get(Calendar.YEAR);
        current_month = c.get(Calendar.MONTH);
        current_day   = c.get(Calendar.DAY_OF_MONTH);

        ll_history_list = (LinearLayout) findViewById(R.id.ll_history_list);
		tv_start_date = (TextView)findViewById(R.id.tv_start_date);
		tv_end_date = (TextView)findViewById(R.id.tv_end_date);
		
		spinner_transtion_history = (Spinner)findViewById(R.id.spinner_transtion_history);
		spinner_transtion_history.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				
				selection_value = spinner_transtion_history.getItemAtPosition(arg2).toString();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
		});
		
		ll_start_date = (LinearLayout) findViewById(R.id.ll_start_date);
		ll_start_date.setOnClickListener(this);

		ll_end_date = (LinearLayout) findViewById(R.id.ll_end_date);
		ll_end_date.setOnClickListener(this);

		btn_history_search = (Button) findViewById(R.id.btn_history_search);
		btn_history_search.setOnClickListener(this);
		
		
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.ll_start_date:
			selection_type = 0;
			showDialog(DATE_PICKER_ID);
			break;

		case R.id.ll_end_date:
			selection_type = 1;
			showDialog(DATE_PICKER_ID);
			break;

		case R.id.btn_history_search:

			if(!tv_start_date.getText().toString().trim().contains("-")){
				Toast.makeText(TransactionHistory.this, "Plesae select a start date",6000).show();
			}else if(!tv_end_date.getText().toString().trim().contains("-")){
				Toast.makeText(TransactionHistory.this, "Plesae select a end date",6000).show();
			}else{
				int status = checkDateValidation();
				if(status == 1){
					showAlertMessage("Start Date Can't greater than End date");
				}else if(status == 2){
					showAlertMessage("Start Date Can't greater than Current date");
					//Toast.makeText(TransactionHistory.this, "Start Date Can't greater than Current date",6000).show();
				}else if(status == 3){
					showAlertMessage("End Date Can't greater than Current date");
					//Toast.makeText(TransactionHistory.this, "End Date Can't greater than Current date",6000).show();
				}else{
					arrayList.clear();
					new FetchTransactionList().execute();
				}
			}
			break;
		}
	}
	
	public void showAlertMessage(String failedMessage){
		new AlertDialog.Builder(TransactionHistory.this)
		.setTitle("Alert Message")
		.setMessage(failedMessage)
		.setPositiveButton("Ok", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		})
		.show();
	}

	private int checkDateValidation() {
	
		int status = 0;
		start_date = tv_start_date.getText().toString().trim();
		end_date = tv_end_date.getText().toString().trim();
		String current_date = new StringBuilder().append(current_year)
                .append("-").append(current_month + 1).append("-").append(current_day)
                .append(" ").toString().trim();
		
		System.out.println("!!current date:"+current_date);
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
		try {
			Date firstDate = formatter.parse(start_date);
			Date endDate = formatter.parse(end_date);
			Date currentDate = formatter.parse(current_date);
			
			if(currentDate.compareTo(firstDate)< 0){
				status = 2;
			}else if(currentDate.compareTo(endDate)< 0){
				status = 3;
				
			}else if(endDate.compareTo(firstDate)< 0){
				status = 1;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}	
		return status;
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_PICKER_ID:

			// open datepicker dialog.
			// set date picker for current date
			// add pickerListener listner to date picker
			return new DatePickerDialog(this, pickerListener, current_year, current_month, current_day);
		}
		return null;
	}
	
	private DatePickerDialog.OnDateSetListener pickerListener = new DatePickerDialog.OnDateSetListener() {
		 
        // when dialog box is closed, below method will be called.
        @Override
        public void onDateSet(DatePicker view, int selectedYear,
                int selectedMonth, int selectedDay) {
             
            year  = ""+selectedYear;
            month = ""+(selectedMonth+1);
            day   = ""+selectedDay;
            
            if(day.length() == 1){
            	day = "0"+day;
            }
            if(month.length() == 1){
            	month = "0"+ month;
            }
 
            // Show selected date
            
            if(selection_type == 0){
            	tv_start_date.setText(new StringBuilder().append(year)
                        .append("-").append(month).append("-").append(day)
                        .append(" "));
            }else if(selection_type == 1){
            	tv_end_date.setText(new StringBuilder().append(year)
                        .append("-").append(month).append("-").append(day)
                        .append(" "));
            }
           }
        };
        
        public class FetchTransactionList extends AsyncTask<Void, Void, Void>{

        	@Override
        	protected void onPreExecute() {
        		super.onPreExecute();
        		showProgressDailog();
        	}
			@Override
			protected Void doInBackground(Void... params) {
				String url = Constant.TRANSACTION + getParams();
				System.out.println("!!url is:"+url);
				try{
				 Document doc = Jsoup.connect(url).get();
			        Elements table = doc.select("table");
			        Elements trs = doc.select("tr");
			        
			        for(int i=0;i<trs.size(); i++){
			        	
			        	if(i== 0){
			        		continue;
			        	}
			        	
			        	Elements tds = trs.get(i).select("td");
			        	for(int j=0 ;j<1; j++){
			        		
			        		String order_id= tds.get(0).text();
			        		String operator= tds.get(1).text();
			        		String service_no= tds.get(2).text();
			        		String amount= tds.get(3).text();
			        		String status= tds.get(4).text();
			        		String date_time= tds.get(5).text();
			        		String profit= tds.get(6).text();
			        		String optId= tds.get(7).text();
			        		String detail= tds.get(8).text();
			        		arrayList.add(new HistoryBean(order_id, operator, service_no, amount, status, date_time, profit, optId, detail));
			        		
			        		
			        		System.out.println("!!val ==== "+j+"    "+tds.get(j).text());
			        	}
			        	
			        }
				}catch(Exception e){
					
				}
				return null;
			}
        
			@Override
			protected void onPostExecute(Void result) {
				super.onPostExecute(result);
				dismissProgressDialog();
				if(arrayList!=null){
					showLListDialog();
				}
				
			}
        }
        
        private void showLListDialog() {
        	
        	final Dialog customDialog = new Dialog(TransactionHistory.this);
        	customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        	customDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        	customDialog.setContentView(R.layout.dialog_trans_history_list);
        	Window window = customDialog.getWindow();
        	window.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            customDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        	ListView lv_historyList = (ListView)customDialog.findViewById(R.id.lv_history_list);
        	adapter = new TransHistoryAdapter(TransactionHistory.this, R.layout.trans_history_row, arrayList);
        	lv_historyList.setAdapter(adapter);
        	Button btn_ok = (Button)customDialog.findViewById(R.id.btn_ok);
        	btn_ok.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					customDialog.dismiss();
				}
			});
        	customDialog.show();
		}
        public String getParams() {

        	String fdate = start_date.replace("-", "");
        	String tdate = end_date.replace("-", "");
        	if(selection_value.contains("All")){
        		return "website=rechargedive.com&tokenkey="+app.getUserinfo().token+"&fdate="+fdate+"&tdate="+tdate+"&rowz=0";
        	}else{
        		return "website=rechargedive.com&tokenkey="+app.getUserinfo().token+"&fdate="+fdate+"&tdate="+tdate+"&rowz="+selection_value;
        	}
    	}
}
