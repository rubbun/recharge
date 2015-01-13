package com.example.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.recharge.BaseActivity;
import com.example.recharge.R;

public class StatusFragment extends Fragment {

	private BaseActivity base;
	private TextView tv_status;
	private LinearLayout ll_detail;
	private TextView tv_order_id,tv_mobile_number,tv_opt,tv_amount,tv_date_time,tv_detail;
	public StatusFragment(BaseActivity base) {
		this.base = base;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.fragment_status, container, false);
		tv_status = (TextView)v.findViewById(R.id.tv_status);
		ll_detail = (LinearLayout)v.findViewById(R.id.ll_detail);
		tv_order_id= (TextView)v.findViewById(R.id.tv_order_id);
		tv_mobile_number= (TextView)v.findViewById(R.id.tv_mobile_number);
		tv_opt= (TextView)v.findViewById(R.id.tv_opt);
		tv_amount= (TextView)v.findViewById(R.id.tv_amount);
		tv_date_time= (TextView)v.findViewById(R.id.tv_date_time);
		tv_detail= (TextView)v.findViewById(R.id.tv_detail);
		
		Bundle bundle = this.getArguments();
		
		if(bundle.getBoolean("result")){
			ll_detail.setVisibility(View.VISIBLE);
			tv_status.setText("SUCCESS");
			tv_order_id.setText("Order Id: "+bundle.getString("orderid"));
			tv_mobile_number.setText("Mobile Number: "+bundle.getString("number"));
			tv_opt.setText("Operator: "+bundle.getString("operator_name"));
			tv_amount.setText("Amount: "+bundle.getString("amount"));
			tv_date_time.setText("Data/Time: "+bundle.getString("time"));
			tv_detail.setText("Detail: ");
		}else{
			ll_detail.setVisibility(View.GONE);
			tv_status.setText("FAILED");
		}
		
		return v;
	}

}
