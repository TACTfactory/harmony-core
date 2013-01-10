package ${data_namespace};

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

import ${data_namespace}.RestClient.Verb;
import ${project_namespace}.R;
import ${project_namespace}.BuildConfig;

import android.content.Context;

import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.widget.Toast;

public abstract class WebServiceClientAdapterBase{
	private static final String TAG = "WebServiceClientAdapterBase";

	private List<Header> headers = new ArrayList<Header>();
	private RestClient restClient;
	private Context context;
	private int statusCode;
	private int errorCode;
	private String error;

	private String host;


	public WebServiceClientAdapterBase(Context context){
		if(BuildConfig.DEBUG){
			host = this.context.getString(R.string.rest_url_dev);
		}
		else{
			host = this.context.getString(R.string.rest_url_prod);
		}

		this.headers.add(new BasicHeader("Content-Type","application/json"));
		this.headers.add(new BasicHeader("Accept","application/json"));

		this.context = context;
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
		return (this.statusCode >= 200 && this.statusCode < 300 && this.errorCode == 0);
	}
	
	protected boolean isValidResponse(String response) {
		return (response != null && !response.trim().equals("") && response.startsWith("{"));
	}
}
