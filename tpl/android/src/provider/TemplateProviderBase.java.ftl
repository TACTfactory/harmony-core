package ${local_namespace};

import ${project_namespace}.data.*;
import ${project_namespace}.R;
import ${project_namespace}.${project_name?cap_first}Application;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
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
		if (${project_name?cap_first}Application.DEBUG) {
			Log.d(TAG, "Initialize Provider...");
		}
		
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		<#list entities?values as entity>
			<#if (entity.fields?size>0) >
		
		// ${entity.name} URI mapping
		final String ${entity.name?lower_case}Type 		= "${entity.name?lower_case}";
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
		} catch (Exception e) {
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
	public String getType(final Uri uri) {
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
	public int delete(final Uri uri, final String selection, final String[] selectionArgs) {
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
	public Uri insert(final Uri uri, final ContentValues values) {
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
		return Uri.parse("content://" + authority);
	}
}
