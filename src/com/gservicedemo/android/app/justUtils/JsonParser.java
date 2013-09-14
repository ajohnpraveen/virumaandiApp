package com.gservicedemo.android.app.justUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonParser {
	
	public static String parseGeoCode(JSONObject json) throws JSONException {
		JSONArray result = json.getJSONArray("results");
		double lat = result.getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getDouble("lat");
		double lng = result.getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getDouble("lng");
		return Double.toString(lat) + "," + Double.toString(lng); 
	}
	
	public static String parseReverseGeoCode(JSONObject json) throws JSONException {
		String addr = "";
		JSONArray result = json.getJSONArray("results");
		for (int count = 0; count < result.getJSONObject(0).getJSONArray("address_components").length(); count++) {
			JSONObject addrComp = result.getJSONObject(0).getJSONArray("address_components").getJSONObject(count);
			if(count != 0)
				addr += ",<br/>";
			addr += addrComp.getString("long_name");
		}
		
		return addr;
	}
	
}
