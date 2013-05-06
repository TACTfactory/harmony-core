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
/**
 * ${project_name?cap_first}ProviderBase.
 */
public class ${project_name?cap_first}ProviderBase extends ContentProvider {
	protected static String TAG = "${project_name?cap_first}Provider";
	protected String URI_NOT_SUPPORTED;
	
	/** Tools / Common.
	 * 
	 */
	public    static Integer baseVersion = 0;
	public    static String baseName = "";
	protected static String item;
	protected static String authority 
				= "${project_namespace}.provider";
	protected static UriMatcher uriMatcher = 
			new UriMatcher(UriMatcher.NO_MATCH);
	
	/** Adapter to SQLite.
	 * 
	 */
	<#list entities?values as entity>
		<#if (entity.fields?size>0) >
	protected ${entity.name?cap_first}ProviderAdapter ${entity.name?uncap_first}Provider;
		</#if>
	</#list>
	protected SQLiteDatabase db;
	
	protected Context mContext;

	/**
	 * Called when the contentProvider is first created.
	 * @return true if everything goes well, false otherwise
	 */
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
			this.${entity.name?uncap_first}Provider = 
			new ${entity.name?cap_first}ProviderAdapter(this.mContext);
			this.db = this.${entity.name?uncap_first}Provider.getDb();
					<#assign firstGo = false />
				<#else>
			this.${entity.name?uncap_first}Provider = 
			new ${entity.name?cap_first}ProviderAdapter(
					this.mContext, 
					this.db);
				</#if>
			</#if>
		</#list>
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
			result = false;
		}
		
		return result;
	}
	
	/**
	 * Get the entity from the URI.
	 * @param uri URI
	 * @return A String representing the entity name 
	 */
	@Override
	public String getType(final Uri uri) {
		String result = null;
		final String single = 
				"vnc.android.cursor.item/" + authority + ".";
		final String collection = 
				"vnc.android.cursor.collection/" + authority + ".";
		
		switch (uriMatcher.match(uri)) {
		<#list entities?values as entity>
			<#if (entity.fields?size>0) >
		
		// ${entity.name} type mapping
		case ${entity.name?cap_first}ProviderAdapter
									.${entity.name?upper_case}_ONE:
			result = single + "${entity.name?lower_case}";
			break;
		case ${entity.name?cap_first}ProviderAdapter
									.${entity.name?upper_case}_ALL:
			result = collection + "${entity.name?lower_case}";
			break;
			</#if>
		</#list>
		
		default:
			throw new IllegalArgumentException(URI_NOT_SUPPORTED + uri);
		}
		
		return result;
	}
	
	/**
	 * Delete the entities matching with uri from the DB.
	 * @param uri URI
	 * @param selection SELECT clause for SQL
	 * @param selectionArgs SELECT arguments for SQL
	 * @return how many token deleted
	 */
	@Override
	public int delete(final Uri uri, final String selection, 
			final String[] selectionArgs) {
		int result = 0;
		this.db.beginTransaction();
		try {
			switch (uriMatcher.match(uri)) {
		<#list entities?values as entity>
			<#if (entity.fields?size>0) >
		
			// ${entity.name}
			case ${entity.name?cap_first}ProviderAdapter
								.${entity.name?upper_case}_ONE:
				try {
					result = this.${entity.name?uncap_first}Provider.delete(uri, 
							selection, 
							selectionArgs);
				} catch (Exception e) {
					throw new IllegalArgumentException(URI_NOT_SUPPORTED + uri);
				}
				break;
			case ${entity.name?cap_first}ProviderAdapter
								.${entity.name?upper_case}_ALL:
				result = this.${entity.name?uncap_first}Provider.delete(uri, 
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
	
	/**
	 * Insert the entities matching with uri from the DB.
	 * @param uri URI
	 * @param values ContentValues to insert
	 * @return how many token inserted
	 */
	@Override
	public Uri insert(final Uri uri, final ContentValues values) {
		Uri result = null;

		this.db.beginTransaction();
		try {

			switch (uriMatcher.match(uri)) {
		<#list entities?values as entity>
			<#if (entity.fields?size>0) >
		
			// ${entity.name}
			case ${entity.name?cap_first}ProviderAdapter
									.${entity.name?upper_case}_ALL:
				result = this.${entity.name?uncap_first}Provider.insert(uri, 
						values);
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
	
	/**
	 * Send a query to the DB.
	 * @param uri URI
	 * @param projection Columns to work with
	 * @param selection SELECT clause for SQL
	 * @param selectionArgs SELECT arguments for SQL
	 * @param sortOrder ORDER BY clause
	 * @return A cursor pointing to the result of the query
	 */
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
			case ${entity.name?cap_first}ProviderAdapter
							.${entity.name?upper_case}_ONE:
				result = this.${entity.name?uncap_first}Provider.query(uri, 
					projection, 
					selection, 
					selectionArgs, 
					sortOrder);
				break;
			case ${entity.name?cap_first}ProviderAdapter
							.${entity.name?upper_case}_ALL:
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
	
	/**
	 * Update the entities matching with uri from the DB.
	 * @param uri URI
	 * @param values ContentValues to update
	 * @param selection SELECT clause for SQL
	 * @param selectionArgs SELECT arguments for SQL
	 * @return how many token update
	 */
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
			case ${entity.name?cap_first}ProviderAdapter
							.${entity.name?upper_case}_ONE:
				result = this.${entity.name?uncap_first}Provider.update(uri,
					values,
					selection,
					selectionArgs);
				break;
			case ${entity.name?cap_first}ProviderAdapter
							.${entity.name?upper_case}_ALL:
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
	
	//-------------------------------------------------------------------------
	
	/** Utils function.
	 * @param typePath Path to type
	 * @return generated URI
	 */	
	public static final Uri generateUri(String typePath) {
		return Uri.parse("content://" + authority + "/" + typePath);
	}
	
	/** Utils function.
	 * @return generated URI
	 */
	public static final Uri generateUri() {
		return Uri.parse("content://" + authority);
	}

	/* (non-Javadoc)
	 * @see android.content.ContentProvider#call<br />
	 * (java.lang.String, java.lang.String, android.os.Bundle)
	 */
	@Override
	public Bundle call(String method, String arg, Bundle extras) {
		Bundle result = null;
		<#list entities?values as entity>			
			<#if (entity.fields?size>0) >
		if (method.equals(${entity.name?cap_first}ProviderAdapter
				.METHOD_INSERT_${entity.name?upper_case})) {
			result = this.${entity.name?uncap_first}Provider.insert(arg, extras);
		}
		else if (method.equals(${entity.name?cap_first}ProviderAdapter
				.METHOD_DELETE_${entity.name?upper_case})) {
			result = this.${entity.name?uncap_first}Provider.delete(arg, extras);
		}
		else if (method.equals(${entity.name?cap_first}ProviderAdapter
				.METHOD_UPDATE_${entity.name?upper_case})) {
			result = this.${entity.name?uncap_first}Provider.update(arg, extras);
		} else
		if (method.equals(
				${entity.name?cap_first}ProviderAdapter.METHOD_QUERY_${entity.name?upper_case})) {
			if (extras != null && extras.containsKey("id")) {
				result = this.${entity.name?uncap_first}Provider.query(arg, 
																		extras);
			} else {
				result = this.${entity.name?uncap_first}Provider.queryAll(arg, 
						extras);	
			}
		} else
			</#if>
		</#list>
		{
			result = super.call(method, arg, extras);
		}
		
		return result;
	}

	/**
	 * Get URI Matcher.
	 * @return the uriMatcher
	 */
	public static UriMatcher getUriMatcher() {
		return uriMatcher;
	}
}
