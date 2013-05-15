<#assign curr = entities[current_entity] />
<#assign internal = false />
<#if (curr.internal?? && curr.internal == "true")><#assign internal = true /></#if>
package ${local_namespace}.base;

import ${local_namespace}.${project_name?cap_first}Provider;

<#if (!internal)>
import ${entity_namespace}.${curr.name};
</#if>import ${data_namespace}.${curr.name}SQLiteAdapter;

import android.content.Context;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
<#assign ext = curr.name?cap_first />
<#if (internal)>
	<#assign ext = "Void" />
</#if>

/**
 * ${curr.name?cap_first}ProviderAdapterBase.
 */
public abstract class ${curr.name?cap_first}ProviderAdapterBase 
				extends ProviderAdapterBase<${ext}> {
	
	/** TAG for debug purpose. */
	protected static String TAG = "${curr.name?cap_first}ProviderAdapter";

	/** ${curr.name?upper_case}_URI. */
	public	  static Uri ${curr.name?upper_case}_URI;

	/** ${curr.name?cap_first} key */
	public static String ITEM_KEY = "${curr.name?cap_first}";

	/** ${curr.name?uncap_first} type. */
	protected static final String ${curr.name?uncap_first}Type = 
			"${curr.name?lower_case}";

	/** ${curr.name?upper_case} Insert method name. */
	public static final String METHOD_INSERT_${curr.name?upper_case} = 
			"insert${curr.name?cap_first}";
	/** ${curr.name?upper_case} Update method name. */
	public static final String METHOD_UPDATE_${curr.name?upper_case} = 
			"update${curr.name?cap_first}";
	/** ${curr.name?upper_case} Delete method name. */
	public static final String METHOD_DELETE_${curr.name?upper_case} = 
			"delete${curr.name?cap_first}";
	/** ${curr.name?upper_case} Query method name. */
	public static final String METHOD_QUERY_${curr.name?upper_case} = 
			"query${curr.name?cap_first}";

	/** ${curr.name?upper_case}_ALL. */
	protected static final int ${curr.name?upper_case}_ALL = 
			${provider_id};
	/** ${curr.name?upper_case}_ONE. */		
	protected static final int ${curr.name?upper_case}_ONE = 
			${provider_id + 1};

	/**
	 * Static constructor. 
	 */
	static {
		${curr.name?upper_case}_URI = 
				${project_name?cap_first}Provider.generateUri(
						${curr.name?uncap_first}Type);
		${project_name?cap_first}Provider.getUriMatcher().addURI(
				${project_name?cap_first}Provider.authority, 
				${curr.name?uncap_first}Type, 		
				${curr.name?upper_case}_ALL);
		${project_name?cap_first}Provider.getUriMatcher().addURI(
				${project_name?cap_first}Provider.authority, 
				${curr.name?uncap_first}Type + "/#",
				${curr.name?upper_case}_ONE);
	}
	
	/**
	 * Constructor.
	 * @param ctx context
	 * @param db database
	 */
	public ${curr.name?cap_first}ProviderAdapterBase(Context ctx, 
					SQLiteDatabase db) {
		super(ctx);
		this.adapter = new ${curr.name?cap_first}SQLiteAdapter(ctx);
		if (db != null) {
			this.db = this.adapter.open(db);
		} else {
			this.db = this.adapter.open();
		}
	}

	/**
	 * Delete the entities matching with uri from the DB.
	 * @param uri URI
	 * @param selection SELECT clause for SQL
	 * @param selectionArgs SELECT arguments for SQL
	 * @return how many token deleted
	 */
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int matchedUri = ${project_name?cap_first}ProviderBase
					.getUriMatcher().match(uri);
		int result = -1;
		switch (matchedUri) {
			case ${curr.name?upper_case}_ONE:
				int id = Integer.parseInt(uri.getPathSegments().get(1));
				selection = ${curr.name?cap_first}SQLiteAdapter.COL_ID 
						+ " = ?";
				selectionArgs = new String[1];
				selectionArgs[0] = String.valueOf(id);
				result = this.adapter.delete(
						selection, 
						selectionArgs);
				break;
			case ${curr.name?upper_case}_ALL:
				result = this.adapter.delete(
							selection, 
							selectionArgs);
				break;
			default:
				result = -1;
				break;
		}
		return result;
	}

	/**
	 * Insert the entities matching with uri from the DB.
	 * @param uri URI
	 * @param values ContentValues to insert
	 * @return how many token inserted
	 */
	public Uri insert(Uri uri, ContentValues values) {
		int matchedUri = ${project_name?cap_first}ProviderBase
				.getUriMatcher().match(uri);
		Uri result = null;
		int id = 0;
		switch (matchedUri) {
			case ${curr.name?upper_case}_ALL:
				id = (int) this.adapter.insert(null, values);
				if (id > 0) {
					result = ContentUris.withAppendedId(
							${curr.name?upper_case}_URI,
							id);
				}
				break;
			default:
				result = null;
				break;
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
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		int matchedUri = ${project_name?cap_first}ProviderBase.getUriMatcher()
				.match(uri);
		Cursor result = null;
		int id = 0;

		switch (matchedUri) {
			case ${curr.name?upper_case}_ONE:
				id = Integer.parseInt(uri.getPathSegments().get(1));
				selection = ${curr.name?cap_first}SQLiteAdapter.COL_ID 
						+ " = ?";
				selectionArgs = new String[1];
				selectionArgs[0] = String.valueOf(id);
				projection = this.adapter.getCols();
				result = this.adapter.query(
							projection, 
							selection,
							selectionArgs, 
							sortOrder,
							null,
							null);
				break;
			case ${curr.name?upper_case}_ALL:
				result = this.adapter.query(
							projection, 
							selection,
							selectionArgs, 
							sortOrder,
							null,
							null);
				break;
			default:
				result = null;
				break;
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
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		int matchedUri = ${project_name?cap_first}ProviderBase.getUriMatcher()
				.match(uri);
		int result = -1;
		switch (matchedUri) {
			case ${curr.name?upper_case}_ONE:
				String id = uri.getPathSegments().get(1);
				result = this.adapter.update(
						values, 
						${curr.name?cap_first}SQLiteAdapter.COL_ID + " = " 
						+ id, 
						selectionArgs);
				break;
			case ${curr.name?upper_case}_ALL:
				result = this.adapter.update(
							values, 
							selection, 
							selectionArgs);
				break;
			default:
				result = -1;
				break;
		}
		return result;
	}
	
	/**
	 * Get the item Key.
	 * @return the item key
	 */
	@Override
	public String getItemKey() {
		return ITEM_KEY;
	}

	/**
	 * Send a query to the DB.
	 * @param arg Argument
	 * @param extras Bundle to get
	 * @return A cursor pointing to the result of the query
	 */
	public Bundle query(String arg, Bundle extras) {
		<#if !internal>
		Bundle result = new Bundle();

		${curr.name?cap_first} ${curr.name?uncap_first} = 
				((${curr.name?cap_first}SQLiteAdapter) this.adapter)
				.getByID(extras.getInt("id"));
		result.putSerializable(ITEM_KEY, ${curr.name?uncap_first});

		return result;
		<#else>
		return null;
		</#if>
	}
}

