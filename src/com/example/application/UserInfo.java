package com.example.application;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.example.constant.Constant;

public class UserInfo {

	//01-19 02:10:48.560: I/System.out(4237): !!token code:SUCCESS,953084063302639616,rechargedive.com

	public String token = "";
	public int mode = 0;
	public SharedPreferences preference = null;

	public UserInfo(Context ctx) {

		preference = ctx.getSharedPreferences(Constant.values.USRINFO.name(), Context.MODE_PRIVATE);
		token = preference.getString(Constant.values.TOKEN.name(), token);
		mode = preference.getInt(Constant.values.MODE.name(), 0);
	}

	public void setToken(String token) {
		this.token = token;
		Editor edit = preference.edit();
		edit.putString(Constant.values.TOKEN.name(), token);

		edit.commit();
	}
	
	public void setMode(int mode) {
		this.mode = mode;
		Editor edit = preference.edit();
		edit.putInt(Constant.values.MODE.name(), mode);

		edit.commit();
	}
	
	public void clearPref(){
		Editor editor = preference.edit();
		editor.clear().commit();
	}

}
