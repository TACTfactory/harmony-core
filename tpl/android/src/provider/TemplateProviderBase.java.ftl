package ${local_namespace};

import ${project_namespace}.data.*;
import ${project_namespace}.R;
import ${project_namespace}.${project_name?cap_first}Application;

import ${entity_namespace}.*;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

public class ${project_name?cap_first}ProviderBase extends ContentProvider {
	protected static String TAG = "${project_name?cap_first}Provider";
	protected String URI_NOT_SUPPORTED;
	
	// Internal code
	<#assign id = 0 /> 
	<#list entities?values as entity>
		<#if (entity.fields?size>0) >
	protected static final int ${entity.name?upper_case}_ALL 		= ${id + 0};
	protected static final int ${entity.name?upper_case}_ONE 		= ${id + 1};
		<#assign id = id + 10 /> 
		</#if>
	</#list>
	
	// Public URI (by Entity)
	<#list entities?values as entity>
		<#if (entity.fields?size>0) >
	public	  static Uri ${entity.name?upper_case}_URI;
		</#if>
	</#list>

	// Public methods names	
	<#list entities?values as entity>			
		<#if (entity.fields?size>0) >
	public static final String METHOD_INSERT_${entity.name?upper_case} = "insert${entity.name?cap_first}";
		</#if>
	</#list>
	<#list entities?values as entity>			
		<#if (entity.fields?size>0) >
	public static final String METHOD_UPDATE_${entity.name?upper_case} = "update${entity.name?cap_first}";
		</#if>
	</#list>
	<#list entities?values as entity>			
		<#if (entity.fields?size>0) >
	public static final String METHOD_DELETE_${entity.name?upper_case} = "delete${entity.name?cap_first}";
		</#if>
	</#list>
	<#list entities?values as entity>			
		<#if (entity.fields?size>0) >
	public static final String METHOD_QUERY_${entity.name?upper_case} = "query${entity.name?cap_first}";
		</#if>
	</#list>
	
	// Tools / Common
	public    static Integer baseVersion = 0;
	public    static String baseName = "";
	protected static String item;
	protected static String authority = "${project_namespace}.provider";
	protected static UriMatcher uriMatcher;
	
	// Adapter to SQLite
	<#list entities?values as entity>
		<#if (entity.fields?size>0) >
	protected ${entity.name?cap_first}SQLiteAdapter dbAdapter${entity.name?cap_first};
		</#if>
	</#list>
	protected SQLiteDatabase db;
	
	protected Context mContext;
	
	// Static constructor
	static {
		if (${project_name?cap_first}Application.DEBUG)
			Log.d(TAG, "Initialize Provider...");
		
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		<#list entities?values as entity>
			<#if (entity.fields?size>0) >
		
		// ${entity.name} URI mapping
		String ${entity.name?lower_case}Type 		= "${entity.name?lower_case}";
		${entity.name?upper_case}_URI	= generateUri(${entity.name?lower_case}Type);
		uriMatcher.addURI(authority, ${entity.name?lower_case}Type, 			${entity.name?upper_case}_ALL);
		uriMatcher.addURI(authority, ${entity.name?lower_case}Type + "/#", 	${entity.name?upper_case}_ONE);
			</#if>
		</#list>
	}
	
	@Override
	public boolean onCreate() {
		boolean result = true;
		
		this.mContext = getContext();
		URI_NOT_SUPPORTED = this.getContext().getString(
				R.string.uri_not_supported);
		
		try {
		<#assign firstGo = true />
		<#list entities?values as entity>
			<#if (entity.fields?size>0) >
			this.dbAdapter${entity.name?cap_first} = new ${entity.name?cap_first}SQLiteAdapter(this.mContext);
				<#if (firstGo)>
			this.db = this.dbAdapter${entity.name?cap_first}.open();
				<#assign firstGo = false />
				<#else>
			this.dbAdapter${entity.name?cap_first}.open(this.db);
				</#if>
			</#if>
		</#list>
		} catch (Exception e){
			Log.e(TAG, e.getMessage());
		}
		
		<#list entities?values as entity>
			<#if (entity.fields?size>0) >
		result = result && (this.dbAdapter${entity.name?cap_first} != null);
        	</#if>
        </#list>
        
		return result;
	}
	
	@Override
	protected void finalize() throws Throwable {
			//this.doUnbindService();
			super.finalize();
	}
	
	@Override
	public String getType(Uri uri) {
		String result = null;
		final String single = "vnc.android.cursor.item/" + authority + ".";
		final String collection = "vnc.android.cursor.collection/" + authority + ".";
		
		switch (uriMatcher.match(uri)) {
		<#list entities?values as entity>
			<#if (entity.fields?size>0) >
		
		// ${entity.name} type mapping
		case ${entity.name?upper_case}_ONE:
			result = single + "${entity.name?lower_case}";
			break;
		case ${entity.name?upper_case}_ALL:
			result = collection + "${entity.name?lower_case}";
			break;
			</#if>
		</#list>
		
		default:
			throw new IllegalArgumentException(URI_NOT_SUPPORTED + uri);
		}
		
		return result;
	}
	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int result = 0;
		int id = 0;
		this.db.beginTransaction();
		try {
			switch (uriMatcher.match(uri)) {
		<#list entities?values as entity>
			<#if (entity.fields?size>0) >
		
			// ${entity.name}
			case ${entity.name?upper_case}_ONE:
				try {
					id = Integer.parseInt(uri.getPathSegments().get(1));
					result = this.dbAdapter${entity.name?cap_first}.delete(id);
				} catch (Exception e) {
					throw new IllegalArgumentException(URI_NOT_SUPPORTED + uri);
				}
				break;
			case ${entity.name?upper_case}_ALL:
				result = this.dbAdapter${entity.name?cap_first}.delete(
							selection, 
							selectionArgs);
				break;
			</#if>
		</#list>
		
			default:
				throw new IllegalArgumentException(URI_NOT_SUPPORTED + uri);
			}

			this.db.setTransactionSuccessful();
		} finally {
			this.db.endTransaction();
		}
		
		if (result > 0) {
			this.getContext().getContentResolver().notifyChange(uri, null);
		}
		return result;
	}
	
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		Uri result = null;
		long id = 0;

		this.db.beginTransaction();
		try {

			switch (uriMatcher.match(uri)) {
		<#list entities?values as entity>
			<#if (entity.fields?size>0) >
		
			// ${entity.name}
			case ${entity.name?upper_case}_ALL:
				id = this.dbAdapter${entity.name?cap_first}.insert(null, values);
			
				if (id > 0) {
					result = ContentUris.withAppendedId(${project_name?cap_first}ProviderBase.${entity.name?upper_case}_URI, id);
					getContext().getContentResolver().notifyChange(result, null);
				}
				break;
			</#if>
		</#list>
		
			default:
				throw new IllegalArgumentException(URI_NOT_SUPPORTED + uri);
			}
			this.db.setTransactionSuccessful();
		} finally {
			this.db.endTransaction();
		}

		return result;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		Cursor result = null;
		int id = 0;

		this.db.beginTransaction();
		try {
		
			switch (uriMatcher.match(uri)) {
		<#list entities?values as entity>
			<#if (entity.fields?size>0) >
		
			// ${entity.name}
			case ${entity.name?upper_case}_ONE:
				id = Integer.parseInt(uri.getPathSegments().get(1));
				result = this.dbAdapter${entity.name?cap_first}.query(id);
				break;
			case ${entity.name?upper_case}_ALL:
				result = this.dbAdapter${entity.name?cap_first}.query(
							projection, 
							selection,
							selectionArgs, 
							sortOrder,
							null,
							null);
				break;
			</#if>
		</#list>
		
			default:
				throw new IllegalArgumentException(URI_NOT_SUPPORTED + uri);
			}

			this.db.setTransactionSuccessful();
		} finally {
			this.db.endTransaction();
		}

		return result;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		int result = 0;
		String id;
		
		this.db.beginTransaction();
		try {

			switch (uriMatcher.match(uri)) {
		<#list entities?values as entity>
			<#if (entity.fields?size>0) >
		
			// ${entity.name}
			case ${entity.name?upper_case}_ONE:
				id = uri.getPathSegments().get(1);
				result = this.dbAdapter${entity.name?cap_first}.update(
						values, 
						${entity.name?cap_first}SQLiteAdapter.COL_ID + " = " + id, 
						selectionArgs);
				break;
			case ${entity.name?upper_case}_ALL:
				result = this.dbAdapter${entity.name?cap_first}.update(
							values, 
							selection, 
							selectionArgs);
				break;
			</#if>
		</#list>
		
			default:
				throw new IllegalArgumentException(URI_NOT_SUPPORTED + uri);
			}
			this.db.setTransactionSuccessful();
		} finally {
			this.db.endTransaction();
		}
		
		if (result > 0) {
			getContext().getContentResolver().notifyChange(uri, null);
		}
		return result;
	}
	
	// Utils function
	//-------------------------------------------------------------------------
	
	public static final Uri generateUri(String typePath) {
		return Uri.parse("content://" + authority + "/" + typePath);
	}
	
	public static final Uri generateUri() {
		return Uri.parse("content://" + authority );
	}

	/* (non-Javadoc)
	 * @see android.content.ContentProvider#call(java.lang.String, java.lang.String, android.os.Bundle)
	 */
	@Override
	public Bundle call(String method, String arg, Bundle extras) {
		<#list entities?values as entity>			
			<#if (entity.fields?size>0) >
		if (method.equals(METHOD_INSERT_${entity.name?upper_case})) {
			return this.insert${entity.name?cap_first}(arg, extras);
		} else
		if (method.equals(METHOD_DELETE_${entity.name?upper_case})) {
			return this.delete${entity.name?cap_first}(arg, extras);
		} else
		if (method.equals(METHOD_UPDATE_${entity.name?upper_case})) {
			return this.update${entity.name?cap_first}(arg, extras);
		} else
		if (method.equals(METHOD_QUERY_${entity.name?upper_case})) {
			return this.query${entity.name?cap_first}(arg, extras);
		} else
		
			</#if>
		</#list>
		{
			return super.call(method, arg, extras);
		}
	}

	<#list entities?values as entity>		
		<#if (entity.fields?size>0) >
	public Bundle insert${entity.name?cap_first}(String arg, Bundle extras) {
		${entity.name?cap_first} item = (${entity.name?cap_first}) extras.getSerializable("${entity.name?cap_first}");
		this.db.beginTransaction();
		try {
			item.setId((int) this.dbAdapter${entity.name?cap_first}.insert(item));
			this.db.setTransactionSuccessful();
		} catch(Exception e) {
			Log.e(TAG, "Error while inserting ${entity.name?cap_first} into database : " + e.getMessage());
			item = null;
		} finally {
			this.db.endTransaction();
		}
		
		Bundle result = new Bundle();
		if (item != null) {
			result.putSerializable("${entity.name?cap_first}", item);
		}
		return result;
	}

	public Bundle delete${entity.name?cap_first}(String arg, Bundle extras) {
		${entity.name?cap_first} item = (${entity.name?cap_first}) extras.getSerializable("${entity.name?cap_first}");
		this.db.beginTransaction();
		try {
			this.dbAdapter${entity.name?cap_first}.delete(item);
			this.db.setTransactionSuccessful();
		} catch(Exception e) {
			Log.e(TAG, "Error while inserting ${entity.name?cap_first} into database : " + e.getMessage());
			item = null;
		} finally {
			this.db.endTransaction();
		}
		
		Bundle result = new Bundle();
		if (item != null) {
			result.putSerializable("${entity.name?cap_first}", item);
		}
		return result;
	}

	public Bundle update${entity.name?cap_first}(String arg, Bundle extras) {
		${entity.name?cap_first} item = (${entity.name?cap_first}) extras.getSerializable("${entity.name?cap_first}");
		this.db.beginTransaction();
		try {
			this.dbAdapter${entity.name?cap_first}.update(item);
			this.db.setTransactionSuccessful();
		} catch(Exception e) {
			Log.e(TAG, "Error while inserting ${entity.name?cap_first} into database : " + e.getMessage());
			item = null;
		} finally {
			this.db.endTransaction();
		}
		
		Bundle result = new Bundle();
		if (item != null) {
			result.putSerializable("${entity.name?cap_first}", item);
		}
		return result;
	}

	public Bundle query${entity.name?cap_first}(String arg, Bundle extras) {
		${entity.name?cap_first} item = (${entity.name?cap_first}) extras.getSerializable("${entity.name?cap_first}");
		this.db.beginTransaction();
		try {
			this.dbAdapter${entity.name?cap_first}.getByID(item.getId());
			this.db.setTransactionSuccessful();
		} catch(Exception e) {
			Log.e(TAG, "Error while inserting ${entity.name?cap_first} into database : " + e.getMessage());
			item = null;
		} finally {
			this.db.endTransaction();
		}
		
		Bundle result = new Bundle();
		if (item != null) {
			result.putSerializable("${entity.name?cap_first}", item);
		}
		return result;
	}
	
		</#if>
	</#list>
}
