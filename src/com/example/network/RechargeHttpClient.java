package com.example.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

public class RechargeHttpClient {

	private static final String TAG = "HttpClient";
	private static DefaultHttpClient httpclient;

	public static synchronized String SendHttpPost(String URL) {

		try {
			System.out.println("!!Request send:"+URL);
			
			if (httpclient == null)
				httpclient = new DefaultHttpClient();

			HttpGet httpPostRequest = new HttpGet(URL);

			httpPostRequest.setHeader("Accept", "application/json");
			httpPostRequest.setHeader("Content-type", "application/json");
			httpPostRequest.setHeader("Accept-Encoding", "gzip");

			long t = System.currentTimeMillis();
			HttpResponse response = (HttpResponse) httpclient.execute(httpPostRequest);

			HttpEntity entity = response.getEntity();

			if (entity != null) {
				InputStream instream = entity.getContent();
				Header contentEncoding = response.getFirstHeader("Content-Encoding");
				if (contentEncoding != null && contentEncoding.getValue().equalsIgnoreCase("gzip")) {
					instream = new GZIPInputStream(instream);
				}

				String resultString = convertStreamToString(instream);
				instream.close();
				resultString = resultString.substring(0, resultString.length() - 1);
				System.out.println("!!token code:"+resultString);
				return resultString;
			}

		} catch (Exception e) {
			Log.e("Exception", "Exception");
			e.printStackTrace();
		}
		return null;
	}

	private static synchronized String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
				System.out.println("!-- " + line);

			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

}
