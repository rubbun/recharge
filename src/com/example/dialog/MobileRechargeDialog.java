package com.example.dialog;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.recharge.BaseActivity;
import com.example.recharge.R;

public class MobileRechargeDialog extends Dialog implements android.view.View.OnClickListener {
	
	BaseActivity base;
	private TextView tv_status;
	private LinearLayout ll_detail,ll_detail_failed;
	private TextView tv_order_id,tv_mobile_number,tv_opt,tv_amount,tv_date_time,tv_detail,tv_detail_failed;
	private String order_id,mobile_number,opt, amount, date;
	private String failed_reasen;
	public boolean flag;
	private Button btn_ok;
	public MobileRechargeDialog(BaseActivity base,boolean flag,String order_id, String opt, String mobile_number, String amount, String date ) {
		super(base);
		this.base = base;
		this.order_id = order_id;
		this.mobile_number = mobile_number;
		this.opt = opt;
		this.amount = amount;
		this.date = date;
		this.flag =flag;
	}
	
	public MobileRechargeDialog(BaseActivity base,boolean flag, String failed_reasen) {
		super(base);
		this.base = base;
		this.failed_reasen = failed_reasen;
		this.flag =flag;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		setContentView(R.layout.dlg_mobile_recharge);
		setCancelable(true);
		setCanceledOnTouchOutside(true);
		btn_ok = (Button)findViewById(R.id.btn_ok);
		btn_ok.setOnClickListener(this);
		tv_status = (TextView)findViewById(R.id.tv_status);
		ll_detail = (LinearLayout)findViewById(R.id.ll_detail);
		tv_order_id= (TextView)findViewById(R.id.tv_order_id);
		tv_mobile_number= (TextView)findViewById(R.id.tv_mobile_number);
		tv_opt= (TextView)findViewById(R.id.tv_opt);
		tv_amount= (TextView)findViewById(R.id.tv_amount);
		tv_date_time= (TextView)findViewById(R.id.tv_date_time);
		tv_detail= (TextView)findViewById(R.id.tv_detail);
		ll_detail_failed = (LinearLayout)findViewById(R.id.ll_detail_failed);
		tv_detail_failed = (TextView)findViewById(R.id.tv_detail_failed);
		
		
		
		
			if(flag){
				ll_detail.setVisibility(View.VISIBLE);
				ll_detail_failed.setVisibility(View.GONE);
				tv_status.setText("SUCCESS");
				tv_order_id.setText("Order Id: "+order_id);
				tv_mobile_number.setText("Mobile Number: "+mobile_number);
				tv_opt.setText("Operator: "+opt);
				tv_amount.setText("Amount: "+amount);
				tv_date_time.setText("Data/Time: "+date);
				tv_detail.setText("Detail: ");
			}else{
				ll_detail.setVisibility(View.GONE);
				ll_detail_failed.setVisibility(View.VISIBLE);
				tv_status.setText("FAILED");
				tv_status.setTextColor(Color.RED);
				tv_detail_failed.setText("Detail: "+failed_reasen);
			}
		}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_ok:
			dismiss();
			break;

		default:
			break;
		}
		
	}
		
	
	
	}
	

