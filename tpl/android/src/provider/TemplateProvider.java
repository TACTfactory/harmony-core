package ${local_namespace};

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

public class ${project_name?cap_first}Provider extends ContentProvider {
	private static String TAG = "${name?cap_first}Provider";
	private String URI_NOT_SUPPORTED;
	
	// Internal code
	protected static final int ${name?upper_case}_ALL 		= 0;
	protected static final int ${name?upper_case}_ONE 		= 1;
	
	// Public URI (by Entity)
	public	  static Uri ${name?upper_case}_URI;
	
	// Tools / Common
	public    static Integer baseVersion = 0;
	public    static String baseName = "";
	protected static String item;
	protected static String authority;
	protected static UriMatcher uriMatcher;
	
	// Adapter to SQLite
	protected ${name?cap_first}SqliteAdapterBase dbAdapter${name?cap_first};
	
	// Attributes
	protected boolean mIsBound = false;
	protected ServiceConnection mConnection;
	protected I${name?cap_first}Service mBoundService;
	//protected I${name?cap_first}ServiceListener mListener;
	protected Context mContext;
	
	// Static constructor
	protected static void initializeProvider() {
		Log.d(TAG, "Initialize Provider...");
		
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		
		String ${name?lower_case}Type 		= "${name?lower_case}";
		${name?upper_case}_URI	= generateUri(${name?lower_case}Type);
		uriMatcher.addURI(authority, ${name?lower_case}Type, 		${name?upper_case}_ALL);
		uriMatcher.addURI(authority, ${name?lower_case}Type 		+ "/#", 	${name?upper_case}_ONE);
		uriMatcher.addURI(authority, ${name?lower_case}Type 		+ "/hash/", ${name?upper_case}_ONE);
	}
	
	@Override
	public boolean onCreate() {
		boolean result = false;
		
		this.mContext = getContext();
		URI_NOT_SUPPORTED = this.getContext().getString(
				R.string.uri_not_supported);
		
		this.dbAdapter${name?cap_first} = new ${name?cap_first}Adapter(this.mContext);
		this.dbAdapter${name?cap_first}.open();
		
        result = (this.dbAdapter${name?cap_first} != null);
	
        //this.mListener = this.createServiceListener();
		//this.mConnection = this.createServiceConnection();
        this.doBindService();
        
        return result;
	}
	
	@Override
	protected void finalize() throws Throwable {
			this.doUnbindService();
			super.finalize();
	}
	
	@Override
	public String getType(Uri uri) {
		String result = null;
		final String single = "vnc.android.cursor.item/";
		final String collection = "vnc.android.cursor.collection/";
		
		switch (uriMatcher.match(uri)) {
		
		case ${name?upper_case}_ONE:
			result = single + authority + "." + "${name?lower_case}";
			break;
		case ${name?upper_case}TWITTER_ALL:
			result = collection + authority + "." + "${name?lower_case}";
			break;
			
		default:
			throw new IllegalArgumentException(URI_NOT_SUPPORTED + uri);
		}
		
		return result;
	}
	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int result = 0;
		int id = 0;
		
		switch (uriMatcher.match(uri)) {
		
		case ${name?upper_case}_ONE:
			try {
				id = Integer.parseInt(uri.getPathSegments().get(1));
				result = this.dbAdapter${name?cap_first}.remove(id);
			} catch (Exception e) {
				throw new IllegalArgumentException(URI_NOT_SUPPORTED + uri);
			}
			break;
		case ${name?upper_case}_ALL:
			result = this.dbAdapter${name?cap_first}.remove(selection, selectionArgs);
			break;
		
		default:
			throw new IllegalArgumentException(URI_NOT_SUPPORTED + uri);
		}
		
		this.getContext().getContentResolver().notifyChange(uri, null);
		return result;
	}
	
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		Uri result = null;
		long id = 0;

		switch (uriMatcher.match(uri)) {
		
		case ${name?upper_case}_ALL:
			id = this.dbAdapter${name?cap_first}.insert(values);
			
			if (id > 0) {
				result = ContentUris.withAppendedId(${name?cap_first}Provider.${name?upper_case}_URI, id);
				getContext().getContentResolver().notifyChange(result, null);
			}
			break;
		
		default:
			throw new IllegalArgumentException(URI_NOT_SUPPORTED + uri);
		}

		//getContext().getContentResolver().notifyChange(result, null);
		return result;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		Cursor result = null;
		int id, hash = 0;
		
		switch (uriMatcher.match(uri)) {
		
		case ${name?upper_case}_ONE:
			if (uri.getPathSegments().get(1).equals("hash")) {
				hash = Integer.parseInt(selection);
				result = this.dbAdapter${name?cap_first}.get${name?cap_first}ItemCursorByHash(hash);
			} else {
				id = Integer.parseInt(uri.getPathSegments().get(1));
				result = this.dbAdapter${name?cap_first}.get${name?cap_first}ItemCursor(id);
			}
			break;
		case ${name?upper_case}_ALL:
			result = this.dbAdapter${name?cap_first}.getAll${name?cap_first}ItemCursor(projection, selection,
					selectionArgs, sortOrder);
			break;
		
		default:
			throw new IllegalArgumentException(URI_NOT_SUPPORTED + uri);
		}

		result.setNotificationUri(getContext().getContentResolver(), uri);
		return result;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		int result = 0;
		String id;
		
		switch (uriMatcher.match(uri)) {
		
		case ${name?upper_case}_ONE:
			id = uri.getPathSegments().get(1);
			result = this.dbAdapter${name?cap_first}.update${name?cap_first}Item(
					values, 
					${name?cap_first}Adapter.COL_ID + " = " + id, 
					selectionArgs);
			break;
		case ${name?upper_case}_ALL:
			result = this.dbAdapter${name?cap_first}.update${name?cap_first}Item(
					values, 
					selection, 
					selectionArgs);
			break;
		
		default:
			throw new IllegalArgumentException(URI_NOT_SUPPORTED + uri);
		}
		
		getContext().getContentResolver().notifyChange(uri, null);
		return result;
	}

	/*private ServiceConnection createServiceConnection() {
		return new ServiceConnection() {
			@Override
			public void onServiceDisconnected(ComponentName name) {
				Log.d("Base${name?cap_first}Activity", "Disconnected !");
				
				mBoundService = null;
			}

			@Override
			public void onServiceConnected(ComponentName name, IBinder service) {
				Log.d("Base${name?cap_first}Activity", "Connected!");
				
				mBoundService = ((${name?cap_first}ServiceBinder) service).getService();
				mBoundService.addListener(mListener);

			}
		};
	}*/
	
	/*private I${name?cap_first}ServiceListener createServiceListener() {
		return new I${name?cap_first}ServiceListener() {
			public void dataChanged(final ${name?cap_first} item) {
				Base${name?cap_first}Activity.this.runOnUiThread(new Runnable() {
					public void run() {
						if (item != null) {
							Log.d("Base${name?cap_first}Activity.", "Add ${name?cap_first}Item");
							//mMessages.add(item);
							//TODO Pk adapter.add(item);
							//mAdapter.notifyDataSetChanged();
						}
					}
				});
			}
		};
	}
	*/
	
	// Utils function
	//-------------------------------------------------------------------------
	// Utils
	public static final Uri generateUri(String typePath) {
		return Uri.parse("content://" + authority + "/" + typePath);
	}
	public static final Uri generateUri() {
		return Uri.parse("content://" + authority );
	}
	
	// Service call
	//-------------------------------------------------------------------------
	private void doBindService() {
		Log.i(TAG, "Bind to ${name?upper_case} Service !");
		
		if (!this.mIsBound) {
			Log.d(TAG, "Binded to ${name?upper_case} Service !");
			
			// Attach our connection
			//this.mContext.bindService(srvIntent, this.mConnection, Context.BIND_AUTO_CREATE);
			
			Intent srvIntent = new Intent(this.mContext, ${name?cap_first}Service.class);
			this.mContext.startService(srvIntent);
			
			this.mIsBound = true;
		}
	}
	
	private void doUnbindService() {
		Log.i(TAG, "Unbind the ${name?upper_case} Service !");
		
		if (this.mIsBound) {
			Log.d(TAG, "Unbinded the ${name?upper_case} Service !");
			
			Intent srvIntent = new Intent(this.mContext, ${name?cap_first}Service.class);
			this.mContext.stopService(srvIntent);
			
			// Detach our existing connection.
			//this.mContext.unbindService(this.mConnection);
			
			this.mIsBound = false;
		}
	}
}