package ${data_namespace}.base;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.tactfactory.mda.test.demact.data.RestClient.Verb;
import com.tactfactory.mda.test.demact.entity.base.EntityBase;

import android.content.Context;
import android.util.Log;

public abstract class SyncClientAdapterBase<T extends EntityBase> extends WebServiceClientAdapterBase<T>{
	private static final String TAG = "SyncClientAdapterBase";
	
	
	public SyncClientAdapterBase(Context context) {
		super(context);
	}
	
	
	private JSONObject addJsonDate(JSONObject jsItems, String paramName, DateTime date) {
		try {
			jsItems.put(paramName, date.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return jsItems;
	}
	
	
	private JSONObject addJsonItems(JSONObject jsItems, String paramName, ArrayList<T> entities) {
		JSONArray jsattr = itemsToJson(entities);

		try {
			jsItems.put(paramName, jsattr);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return jsItems;
	}
	
	public void sync(DateTime dateLast, DateTime dateStart, 
			ArrayList<T> deleted, ArrayList<T> inserted, 
			ArrayList<T> updated, ArrayList<T> merged) {
		
		
		String uri = String.format(
				"user",
				dateLast.toString(ISODateTimeFormat.dateTime().withZoneUTC()),
				dateStart.toString(ISODateTimeFormat.dateTime().withZoneUTC()),
				REST_FORMAT);
		
		JSONObject json = new JSONObject();
		this.addJsonDate(json, "lastSyncDate", dateLast);
		this.addJsonDate(json, "startSyncDate", dateStart);
		this.addJsonItems(json, "Users-d", deleted);
		this.addJsonItems(json, "Users-i", inserted);
		this.addJsonItems(json, "Users-u", updated);
	    //this.addJsonUsers(json, "Users-m", merged);
		
		String response = this.invokeRequest(Verb.POST, uri , json);
		
		inserted.clear();
		updated.clear();
		merged.clear();
		
		try{
			JSONObject jsonResp = new JSONObject(response);
			extractItems(jsonResp, "Users-i", inserted);
			extractItems(jsonResp, "Users-u", updated);
			extractItems(jsonResp, "Users-m", merged);
		}catch(JSONException e){
			Log.e(TAG, e.getMessage());
		}
	}
	
	public int extractItems(JSONObject json, String paramName, List<T> items) throws JSONException{
		JSONArray itemArray = json.optJSONArray(paramName);
		
		int result = -1;
		
		if (itemArray != null) {
			int count = itemArray.length();			
			
			for (int i = 0 ; i < count; i++) {
				JSONObject jsonItem = itemArray.getJSONObject(i);
				
				T item = extract(jsonItem);
				if (item!=null){
					synchronized (items) {
						items.add(item);
					}
				}
			}
		}
		
		if (!json.isNull("Meta")){
			JSONObject meta = json.optJSONObject("Meta");
			result = meta.optInt("nbt",0);
		}
		
		return result;
	}
}
