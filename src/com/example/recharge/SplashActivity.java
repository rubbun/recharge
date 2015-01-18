package com.example.recharge;

import com.example.constant.Constant;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SplashActivity extends BaseActivity {

	private Button btn_online, btn_offline;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		btn_online = (Button) findViewById(R.id.btn_online);
		btn_offline = (Button) findViewById(R.id.btn_offline);
		btn_online.setOnClickListener(this);
		btn_offline.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.btn_online:

			if (app.getUserinfo().token.length() >1) {
				/*app.getUserinfo().setToken("2102185047370587136");
				app.getUserinfo().setMode(Constant.ONLINE);
				Intent i = new Intent(SplashActivity.this, DashBoardActivity.class);
				startActivity(i);
				SplashActivity.this.finish();*/
				
				
				Intent i = new Intent(SplashActivity.this, DashBoardActivity.class);
				app.getUserinfo().setMode(Constant.ONLINE);
				startActivity(i);
				SplashActivity.this.finish();
			} else {
				
				
				/*app.getUserinfo().setToken("2102185047370587136");
				app.getUserinfo().setMode(Constant.ONLINE);
				Intent i = new Intent(SplashActivity.this, DashBoardActivity.class);
				startActivity(i);
				SplashActivity.this.finish();*/
				
				
				Intent i = new Intent(SplashActivity.this, SignInActivity.class);
				app.getUserinfo().setMode(Constant.ONLINE);
				startActivity(i);
				SplashActivity.this.finish();
			}

			break;

		case R.id.btn_offline:
			Intent i = new Intent(SplashActivity.this, ServiceActivity.class);
			app.getUserinfo().setMode(Constant.OFFLINE);
			startActivity(i);
			SplashActivity.this.finish();
			break;

		}
	}

}
