package ${local_namespace}.base;

import ${local_namespace}.*;
import ${project_namespace}.R;
import ${project_namespace}.${project_name?cap_first}Application;

import android.content.ContentProvider;
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
	
	// Tools / Common
	public    static Integer baseVersion = 0;
	public    static String baseName = "";
	protected static String item;
	protected static String authority = "${project_namespace}.provider";
	protected static UriMatcher uriMatcher;
	
	// Adapter to SQLite
	<#list entities?values as entity>
		<#if (entity.fields?size>0) >
	protected ${entity.name?cap_first}ProviderAdapter ${entity.name?uncap_first}Provider;
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
		//uriMatcher.addURI(authority, ${entity.name?cap_first}ProviderAdapter.${entity.name?uncap_first}Type, 			${entity.name?upper_case}_ALL);
		//uriMatcher.addURI(authority, ${entity.name?cap_first}ProviderAdapter.${entity.name?uncap_first}Type + "/#", 	${entity.name?upper_case}_ONE);
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
				<#if (firstGo)>
			this.${entity.name?uncap_first}Provider = new ${entity.name?cap_first}ProviderAdapter(this.mContext);
			this.db = this.${entity.name?uncap_first}Provider.getDb();
					<#assign firstGo = false />
				<#else>
			this.${entity.name?uncap_first}Provider = new ${entity.name?cap_first}ProviderAdapter(this.mContext, this.db);
				</#if>
			</#if>
		</#list>
		} catch (Exception e){
			Log.e(TAG, e.getMessage());
			result = false;
		}
		
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
		this.db.beginTransaction();
		try {
			switch (uriMatcher.match(uri)) {
		<#list entities?values as entity>
			<#if (entity.fields?size>0) >
		
			// ${entity.name}
			case ${entity.name?upper_case}_ONE:
				try {
					result = this.${entity.name?uncap_first}Provider.delete(uri, selection, selectionArgs);
				} catch (Exception e) {
					throw new IllegalArgumentException(URI_NOT_SUPPORTED + uri);
				}
				break;
			case ${entity.name?upper_case}_ALL:
				result = this.${entity.name?uncap_first}Provider.delete(uri, selection, selectionArgs);
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

		this.db.beginTransaction();
		try {

			switch (uriMatcher.match(uri)) {
		<#list entities?values as entity>
			<#if (entity.fields?size>0) >
		
			// ${entity.name}
			case ${entity.name?upper_case}_ALL:
				result = this.${entity.name?uncap_first}Provider.insert(uri, values);
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
		if (result != null) {
			this.getContext().getContentResolver().notifyChange(result, null);
		}

		return result;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		Cursor result = null;

		this.db.beginTransaction();
		try {
		
			switch (uriMatcher.match(uri)) {
		<#list entities?values as entity>
			<#if (entity.fields?size>0) >
		
			// ${entity.name}
			case ${entity.name?upper_case}_ONE:
				result = this.${entity.name?uncap_first}Provider.query(uri, 
					projection, 
					selection, 
					selectionArgs, 
					sortOrder);
				break;
			case ${entity.name?upper_case}_ALL:
				result = this.${entity.name?uncap_first}Provider.query(uri, 
					projection, 
					selection, 
					selectionArgs, 
					sortOrder);
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
		
		this.db.beginTransaction();
		try {

			switch (uriMatcher.match(uri)) {
		<#list entities?values as entity>
			<#if (entity.fields?size>0) >
		
			// ${entity.name}
			case ${entity.name?upper_case}_ONE:
				result = this.${entity.name?uncap_first}Provider.update(uri,
					values,
					selection,
					selectionArgs);
				break;
			case ${entity.name?upper_case}_ALL:
				result = this.${entity.name?uncap_first}Provider.update(uri,
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
		if (method.equals(${entity.name?cap_first}ProviderAdapter.METHOD_INSERT_${entity.name?upper_case})) {
			return this.${entity.name?uncap_first}Provider.insert(arg, extras);
		} else
		if (method.equals(${entity.name?cap_first}ProviderAdapter.METHOD_DELETE_${entity.name?upper_case})) {
			return this.${entity.name?uncap_first}Provider.delete(arg, extras);
		} else
		if (method.equals(${entity.name?cap_first}ProviderAdapter.METHOD_UPDATE_${entity.name?upper_case})) {
			return this.${entity.name?uncap_first}Provider.update(arg, extras);
		} else
		if (method.equals(${entity.name?cap_first}ProviderAdapter.METHOD_QUERY_${entity.name?upper_case})) {
			return this.${entity.name?uncap_first}Provider.queryAll(arg, extras);
		} else
		
			</#if>
		</#list>
		{
			return super.call(method, arg, extras);
		}
	}

	public static UriMatcher getUriMatcher() {
		return uriMatcher;
	}
}
