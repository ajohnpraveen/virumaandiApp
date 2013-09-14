package com.gservicedemo.android.app.justUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import android.util.Log;

public class RequestHelper {
	private static final String TAG = "RequestHelper";
	
	public static JSONObject executeRequest(HttpRequestBase request) throws ClientProtocolException, IOException, JSONException {
		HttpClient client = new DefaultHttpClient();
		HttpResponse resp =  client.execute(request);
    	InputStream is = null;
        try {
        	is = resp.getEntity().getContent();
        	String in = convertStreamToString(is);
        	JSONObject json = new JSONObject(in);
        	return json;
        }
        catch (IOException e) {
        	Log.d(TAG, Log.getStackTraceString(e));
        }
		return null;
	}
	
    protected static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
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
