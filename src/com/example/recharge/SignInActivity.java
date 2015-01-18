package com.example.recharge;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;
import org.apache.http.conn.util.InetAddressUtils;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.constant.Constant;
import com.example.network.RechargeHttpClient;

public class SignInActivity extends BaseActivity {

	private EditText et_number, et_password, et_website;
	private Button btn_login;
	String st[] ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signin);
		et_number = (EditText) findViewById(R.id.et_number);
		et_password = (EditText) findViewById(R.id.et_password);
		et_website = (EditText) findViewById(R.id.et_website);
		btn_login = (Button) findViewById(R.id.btn_login);
		btn_login.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.btn_login:
			if(isConnectingToInternet()){
				if (isvalid()) {				
					new SignInAsyncTask().execute();
				}
			}else{
				Toast.makeText(getApplicationContext(), "No Internet connection", Toast.LENGTH_LONG).show();
			}
			

			break;

		}
	}

	public boolean isvalid() {
		if (et_number.getText().toString().trim().length() != 10) {
			et_number.setError("Please enter valid mobile number");
			return false;
		} else if (!(et_password.getText().toString().trim().length() > 0)) {
			et_password.setError("Please enter password");
			return false;
		} else if (!(et_website.getText().toString().trim().length() > 0)) {
			et_website.setError("Please enter password");
			return false;
		}
		return true;

	}

	public class SignInAsyncTask extends AsyncTask<Void, Void, Boolean> {

		protected void onPreExecute() {
			showProgressDailog();
		}

		protected Boolean doInBackground(Void... params) {

			String url = Constant.GET_TOKEN + getParams();
			String response = RechargeHttpClient.SendHttpPost(url);
			st = response.split(",");
			if (response != null) {
				if (response.startsWith("SUCCESS")) {
					
					app.getUserinfo().setToken(st[1]);
					return true;
				}
			}

			return false;
		}

		protected void onPostExecute(Boolean result) {
			dismissProgressDialog();
			if (result) {
				//Intent i = new Intent(SignInActivity.this, DashBoardActivity.class);
				Intent i = new Intent(SignInActivity.this, DashBoardActivity.class);
				startActivity(i);
				SignInActivity.this.finish();
			}else{
				Toast.makeText(getApplicationContext(), "Login not successfull "+st[1], Toast.LENGTH_LONG).show();
			}
		}

	}

	public String getIPAddress() {
		try {
			List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
			for (NetworkInterface intf : interfaces) {
				List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
				for (InetAddress addr : addrs) {
					if (!addr.isLoopbackAddress()) {
						String sAddr = addr.getHostAddress().toUpperCase();
						boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);

						if (isIPv4)
							return sAddr;

					}
				}
			}
		} catch (Exception ex) {
		}
		return "";
	}

	public String getParams() {

		String mobile_number = et_number.getText().toString().trim();
		String password = et_password.getText().toString().trim();
		String website = et_website.getText().toString().trim();
		return "mobile=" + mobile_number + "&pwd=" + password + "&website=" + website + "&userip=" + getIPAddress() + "&device=android";
	}

}
