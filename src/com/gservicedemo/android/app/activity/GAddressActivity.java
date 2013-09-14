package com.gservicedemo.android.app.activity;

import java.io.IOException;
import org.apache.http.client.methods.HttpGet;
import com.gservicedemo.android.app.justUtils.JsonParser;
import com.gservicedemo.android.app.justUtils.RequestHelper;
import com.gservicedemo.android.app.justUtils.Utils;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class GAddressActivity extends Activity {
	private static final String TAG ="GAddressActivity";
	
	TextView contentTv;
	EditText postalEdit;
	Button magicBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_gservice);
		contentTv = (TextView) findViewById(R.id.fullscreen_content);
		contentTv.setMovementMethod(new ScrollingMovementMethod());
		postalEdit = (EditText) findViewById(R.id.address_editText);
		magicBtn = (Button) findViewById(R.id.magic_button);
		
		magicBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(!TextUtils.isEmpty(postalEdit.getText())) {
				GServiceTask task = new GServiceTask();
				task.execute(postalEdit.getText().toString());
				} else {
					Toast.makeText(GAddressActivity.this, R.string.no_postal_code, Toast.LENGTH_LONG).show();
				}
			}
		});
		super.onCreate(savedInstanceState);
	}
	

	private class GServiceTask extends AsyncTask<String, Integer, String> {

		@Override
		protected void onPreExecute() {
			contentTv.setText(getString(R.string.loading));
			super.onPreExecute();
		}
		
		@Override
		protected String doInBackground(String... params) {
			String postal = params[0];
			HttpGet get = new HttpGet(Utils.GEO_CODE_URL + "address=" + postal +"&sensor=true");
			try {
				String latitude = JsonParser.parseGeoCode(RequestHelper.executeRequest(get));
				HttpGet getAddress = new HttpGet(Utils.GEO_CODE_URL + "latlng=" + latitude +"&sensor=true");
				return JsonParser.parseReverseGeoCode(RequestHelper.executeRequest(getAddress));
			} catch (IOException e) {
				Log.e(TAG, Log.getStackTraceString(e));
			} catch (Exception e) {
				Log.e(TAG, "DAMOOOOOO, cant handle it anymore", e);
			}
			return null;
		}		
		
		@Override
		protected void onPostExecute(String result) {
			if(!TextUtils.isEmpty(result)) {
				contentTv.setText(Html.fromHtml(result));
			} else {
				contentTv.setText(getString(R.string.smthng_wrong));
			}
			super.onPostExecute(result);
		}
	}
	
}
