package com.example.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.bean.HistoryBean;
import com.example.recharge.R;
import com.example.recharge.TransactionHistory;

public class TransHistoryAdapter extends ArrayAdapter<HistoryBean>{
	
	private ArrayList<HistoryBean> mItems = new ArrayList<HistoryBean>();
	private ViewHolder mHolder;
	private TransactionHistory activity;
	private String changeTrackStatus;
	private int pos;
	public String TAG = "snomada";
	
	
	public TransHistoryAdapter(TransactionHistory activity, int textViewResourceId,	ArrayList<HistoryBean> items) {
		super(activity, textViewResourceId, items);
		this.mItems = items;
		this.activity =activity;		
	}		  
	
	@Override
	public View getView( final int position,  View convertView, ViewGroup parent) {
		View v = convertView;		  
		if (v == null) {			
			LayoutInflater vi = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.trans_history_row, null);
			mHolder = new ViewHolder();
			v.setTag(mHolder);	
			mHolder.mOrderId = (TextView)v.findViewById(R.id.tv_order_id);
			mHolder.mOperator = (TextView)v.findViewById(R.id.tv_operator);
			mHolder.mServiceNo = (TextView)v.findViewById(R.id.tv_service_no);
			mHolder.mAmount = (TextView)v.findViewById(R.id.tv_amount);
			mHolder.mStatus = (TextView)v.findViewById(R.id.tv_status);
			mHolder.mDateTime = (TextView)v.findViewById(R.id.tv_dateTime);
			mHolder.mProfit = (TextView)v.findViewById(R.id.tv_profit);
			mHolder.mOptId = (TextView)v.findViewById(R.id.tv_opdid);
			mHolder.mDetails = (TextView)v.findViewById(R.id.tv_detail);
		}
		else {
			
			mHolder =  (ViewHolder) v.getTag();
		}
	
		final HistoryBean bean = mItems.get(position);
		if(bean != null){
			mHolder.mOrderId.setText(bean.getOrder_id());
			mHolder.mOperator.setText(bean.getOperator());
			mHolder.mServiceNo.setText(bean.getService_no());
			mHolder.mAmount.setText(bean.getAmount());
			mHolder.mStatus.setText(bean.getStatus());
			mHolder.mDateTime.setText(bean.getDate_time());
			mHolder.mProfit.setText(bean.getProfit());
			mHolder.mOptId.setText(bean.getOptId());
			mHolder.mDetails.setText(bean.getDetail());
			
		}	
		
		return v;
	}
	class ViewHolder {	
		public TextView mOrderId,mOperator,mServiceNo,mAmount,mStatus,mDateTime,mProfit,mOptId,mDetails;
	}
}
