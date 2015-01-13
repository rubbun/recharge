package com.example.application;

import org.json.JSONArray;

import android.app.Application;

public class Appsettings extends Application{


	
	private UserInfo userinfo;	
	public boolean init = false;
	private JSONArray contactJsonArr;

	public UserInfo getUserinfo() {
		return userinfo;
	}

	public void setUserinfo(UserInfo userinfo) {
		this.userinfo = userinfo;
	}

	public JSONArray getContactJsonArr() {
		return contactJsonArr;
	}

	public void setContactJsonArr(JSONArray contactJsonArr) {
		this.contactJsonArr = contactJsonArr;
	}
	
	

}
