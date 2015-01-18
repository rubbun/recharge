package com.example.recharge;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class TransactionHistory extends BaseActivity {

	private LinearLayout ll_history_list;
	private LinearLayout ll_start_date, ll_end_date;
	private Button btn_history_search;
	private TextView tv_start_date,tv_end_date;
	private Spinner spinner_transtion_history;

	static final int DATE_PICKER_ID = 1111;
	private int year;
	private int month;
	private int day;
	
	private int current_year;
	private int current_month;
	private int current_day;
	
	private int selection_type = 0; 
	String selection_value = null;

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
				Toast.makeText(TransactionHistory.this, "Plesae select a start date",6000).show();
			}else if(checkDateValidation() == 1){
				Toast.makeText(TransactionHistory.this, "Start Date Can't greater than End date",6000).show();;
			}else{
				
			}
			break;
		}
	}

	private int checkDateValidation() {
	
		int status = 0;
		String start_date = tv_start_date.getText().toString().trim();
		String end_date = tv_start_date.getText().toString().trim();
		System.out.println("!!start date: int"+Integer.parseInt(start_date[0]));
		
		int start_year = Integer.parseInt(start_date[0]);
		int end_year = Integer.parseInt(start_date[1]);
		
		int start_month = Integer.parseInt(start_date[0]);
		int end_month = Integer.parseInt(start_date[1]);
		
		int start_day = Integer.parseInt(start_date[0]);
		int end_day = Integer.parseInt(start_date[1]);
		
		if(start_year > current_year){
			status = 2;
		}else if(end_year > current_year){
			status = 2;
		}else if((current_year == start_year) && ()){
			
		}
		if(start_year > end_year){
			status = 1;
		}else if((start_year == end_year) && (start_month > end_month)){
			status = 1;
		}else if((start_year == end_year) && (start_month == end_month) && (start_day > end_day)){
			status = 1;
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
             
            year  = selectedYear;
            month = selectedMonth;
            day   = selectedDay;
 
            // Show selected date
            
            if(selection_type == 0){
            	tv_start_date.setText(new StringBuilder().append(year)
                        .append("-").append(month + 1).append("-").append(day)
                        .append(" "));
            }else if(selection_type == 1){
            	tv_end_date.setText(new StringBuilder().append(year)
                        .append("-").append(month + 1).append("-").append(day)
                        .append(" "));
            }
           }
        };
}
