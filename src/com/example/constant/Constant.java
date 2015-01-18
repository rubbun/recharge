package com.example.constant;

public class Constant {

	public static final String BASE_URL = "http://jolo.in/b2bapp/";
	public static final String GET_TOKEN = BASE_URL + "getkey.php?";
	public static final String PROFILE = BASE_URL + "profile.php?";
	public static final String DISPUTE = BASE_URL + "dispute.php?";
	public static final String STATISTICS = BASE_URL + "stats.php?";
	public static final String TRANSACTION = BASE_URL + "history.php?";
	public static final String SERVICE = BASE_URL + "dorc.php?";
	public static final String LOGOUT = BASE_URL + "logout.php?";
	public static final int ONLINE = 1;
	public static final int OFFLINE = 2;
	public static final String OFFLINE_MOBILE_NO = "9999999999";

	public enum values {
		USRINFO, TOKEN, MODE;

	}
}
