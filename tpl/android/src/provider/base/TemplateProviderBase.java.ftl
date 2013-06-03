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
	/** TAG for debug purpose. */
	protected static String TAG = "${project_name?cap_first}Provider";
	/** Uri not supported. */
	protected static String URI_NOT_SUPPORTED;
	
	/* Tools / Common. */
	/** Base version. */
	public    static Integer baseVersion = 0;
	/** Base name. */
	public    static String baseName = "";
	/** item. */
	protected static String item;
	/** ${project_namespace}.provider authority. */
	public static String authority 
				= "${project_namespace}.provider";
	/** URI Matcher. */
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
	
	/**
	 * Context.
	 */
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
		boolean alreadyInTransaction = this.db.inTransaction();
		if (!alreadyInTransaction) {
			this.db.beginTransaction();
		}
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
			if (!alreadyInTransaction) {
				this.db.setTransactionSuccessful();
			}
		} finally {
			if (!alreadyInTransaction) {
				this.db.endTransaction();
			}
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

		boolean alreadyInTransaction = this.db.inTransaction();
	
		if (!alreadyInTransaction) {
			this.db.beginTransaction();
		}
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
			if (!alreadyInTransaction) {
				this.db.setTransactionSuccessful();
			}
		} finally {
			if (!alreadyInTransaction) {
				this.db.endTransaction();
			}
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
	public Cursor query(final Uri uri, final String[] projection, 
			final String selection, final String[] selectionArgs, 
			final String sortOrder) {
		Cursor result = null;

		boolean alreadyInTransaction = this.db.inTransaction();

		if (!alreadyInTransaction) {
			this.db.beginTransaction();
		}
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
			if (!alreadyInTransaction) {
				this.db.setTransactionSuccessful();
			}
		} finally {
			if (!alreadyInTransaction) {
				this.db.endTransaction();
			}
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
	public int update(final Uri uri, final ContentValues values, 
				      final String selection, final String[] selectionArgs) {
		int result = 0;

		boolean alreadyInTransaction = this.db.inTransaction();
		
		if (!alreadyInTransaction) {
			this.db.beginTransaction();
		}
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

			if (!alreadyInTransaction) {
				this.db.setTransactionSuccessful();
			}
		} finally {
			if (!alreadyInTransaction) {
				this.db.endTransaction();
			}
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
	public static final Uri generateUri(final String typePath) {
		return Uri.parse("content://" + authority + "/" + typePath);
	}
	
	/** Utils function.
	 * @return generated URI
	 */
	public static final Uri generateUri() {
		return Uri.parse("content://" + authority);
	}

	/**
	 * Get URI Matcher.
	 * @return the uriMatcher
	 */
	public static UriMatcher getUriMatcher() {
		return uriMatcher;
	}
}
