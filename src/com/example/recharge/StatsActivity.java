package com.example.recharge;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.constant.Constant;
import com.example.network.RechargeHttpClient;

public class StatsActivity extends BaseActivity {
	private TextView tv_earning, tv_sale, tv_load;
	private String today_earning, userid, today_sale, today_load,
			error_message;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stats);
		tv_earning = (TextView) findViewById(R.id.tv_earning);
		tv_sale = (TextView) findViewById(R.id.tv_sale);
		tv_load = (TextView) findViewById(R.id.tv_load);

		new CallStatsService().execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);// Menu Resource, Menu
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.log_out:
			DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					switch (which) {
					case DialogInterface.BUTTON_POSITIVE:
						// Yes button clicked

						app.getUserinfo().clearPref();
						Intent i = new Intent(StatsActivity.this,
								SignInActivity.class);
						i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
								| Intent.FLAG_ACTIVITY_CLEAR_TASK);
						startActivity(i);
						break;

					case DialogInterface.BUTTON_NEGATIVE:
						// No button clicked
						break;
					}
				}
			};

			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("Are you Want to logged out ?")
					.setPositiveButton("Yes", dialogClickListener)
					.setNegativeButton("No", dialogClickListener).show();

			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public class CallStatsService extends AsyncTask<Void, Void, Boolean> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			showProgressDailog();
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			String url = Constant.STATISTICS + getParams();
			String response = RechargeHttpClient.SendHttpPost(url);
			if (response != null) {
				if (response.startsWith("SUCCESS")) {
					String st[] = response.split(",");
					today_earning = st[1];
					today_load = st[2];
					today_sale = st[3];
					return true;
				} else if (response.startsWith("FAILED")) {
					String st[] = response.split(",");
					error_message = st[1];
					return false;
				}
			}
			return false;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			dismissProgressDialog();
			if (result) {
				tv_earning.setText("Rs. "+today_earning);
				tv_load.setText("Rs. "+today_load);
				tv_sale.setText("Rs. "+today_sale);
			} else {
				new AlertDialog.Builder(StatsActivity.this)
						.setTitle("Alert Message").setMessage(error_message)
						.setPositiveButton("Ok", new OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								StatsActivity.this.finish();
							}
						}).show();
			}

		}
	}

	public String getParams() {
		return "tokenkey=" + app.getUserinfo().token
				+ "&website=rechargedive.com";
	}
}
