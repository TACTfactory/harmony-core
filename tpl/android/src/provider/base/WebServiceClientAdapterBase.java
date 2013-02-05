package ${data_namespace}.base;

import ${data_namespace}.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

import com.tactfactory.mda.test.demact.data.RestClient.Verb;

import ${data_namespace}.RestClient.Verb;
import ${project_namespace}.R;
import ${project_namespace}.${project_name?cap_first}Application;

import android.content.Context;

import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

public abstract class WebServiceClientAdapterBase{
	private static final String TAG = "WSClientAdapter";
	protected static String REST_FORMAT = ".json"; //JSon RSS xml or empty (for html)
	
	private List<Header> headers = new ArrayList<Header>();
	private RestClient restClient;
	private Context context;
	private int statusCode;
	private int errorCode;
	private String error;

	

	private String host;


	public WebServiceClientAdapterBase(Context context){

		this.headers.add(new BasicHeader("Content-Type","application/json"));
		this.headers.add(new BasicHeader("Accept","application/json"));

		this.context = context;
		if (${project_name?cap_first}Application.DEBUG){
			host = this.context.getString(R.string.rest_url_dev);
		} else {
			host = this.context.getString(R.string.rest_url_prod);
		}
		this.restClient = new RestClient(host);
	}

	protected synchronized String invokeRequest(Verb verb, String request, JSONObject params) {
		String response = "";
		
		StringBuilder error = new StringBuilder();

		try {
			synchronized (this.restClient) {
				response = this.restClient.invoke(verb, request, params, this.headers);
				this.statusCode = this.restClient.getStatusCode();
				if (isValidResponse(response)){
					this.errorCode = this.appendError(response,error);
					this.error = error.toString();
				}
			}

		} catch (Exception e) {
			this.displayOups();
			
			if (${project_name?cap_first}Application.DEBUG)
				Log.d(TAG, e.getMessage());
		}

		return response;
	}

	/**
	 * @return the error
	 */
	public String getError() {
		return error;
	}

	protected int appendError(String response, StringBuilder error) throws JSONException {
		int result = -1;
		StringBuilder builder = new StringBuilder();
		
		JSONObject json = new JSONObject(response);
		JSONObject jsonErr = json.optJSONObject("Err");
		
		if (jsonErr != null) {
			result = jsonErr.optInt("cd");
				
			JSONArray arrayErrors = json.optJSONArray("msg");
			if (arrayErrors != null) {
				int count = arrayErrors.length();			
				
				for (int i = 0 ; i < count; i++) {
					error.append(arrayErrors.optString(i, null));
					if (!TextUtils.isEmpty(error.toString()))
						builder.append(error + "; ");
				}
				
				error.append(builder.toString());
			}else {
				error.append(jsonErr.optString("msg", ""));
			}
			
			if (TextUtils.isEmpty(error.toString()))
				error = null;
		}
		
		return result;
	}

	protected void displayOups() {
		this.displayOups(this.context.getString(R.string.common_network_error));
	}
	
	protected void displayOups(final String message) {
		if (context != null && !TextUtils.isEmpty(message)) {
			
			((FragmentActivity) context).runOnUiThread(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(
							context, 
							message,//message, 
							Toast.LENGTH_SHORT)
							.show();

				}
			});
		}
	}
	
	protected boolean isValidRequest() {
		return (
				this.statusCode >= 200 && 
				this.statusCode < 300 && 
				this.errorCode == 0);
	}
	
	protected boolean isValidResponse(String response) {
		return (
				response != null && 
				!response.trim().equals("") && 
				response.startsWith("{"));
	}
	
	public DateTime syncTime() {
		String uri = String.format(
				"sync%s",
				REST_FORMAT);
		String response = this.invokeRequest(Verb.GET, uri , null).replace("\"", "");
		return ISODateTimeFormat.dateTimeNoMillis().parseDateTime(response);
	}
}
