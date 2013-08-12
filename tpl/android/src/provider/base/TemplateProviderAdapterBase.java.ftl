<#include utilityPath + "all_imports.ftl" />
<#assign curr = entities[current_entity] />
<#assign internal = (curr.internal?? && curr.internal == "true") />
<#assign inherited = false />
<#assign ext = curr.name?cap_first />
<#if (internal)>
	<#assign ext = "Void" />
</#if>
<#if (curr.extends?? && entities[curr.extends]??)>
	<#assign extends = curr.extends />
	<#assign inherited = true />
</#if>
<@header?interpret />
package ${local_namespace}.base;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import ${data_namespace}.${curr.name}SQLiteAdapter;
<#if (!internal)>
import ${entity_namespace}.${curr.name};
</#if>import ${local_namespace}.${project_name?cap_first}Provider;
<#if (inherited)>

import ${local_namespace}.${extends?cap_first}ProviderAdapter;
import ${data_namespace}.${extends?cap_first}SQLiteAdapter;

import ${project_namespace}.criterias.${curr.name}Criterias;
import ${project_namespace}.criterias.base.Criteria;
import ${project_namespace}.criterias.base.Criteria.Type;
import ${project_namespace}.criterias.base.CriteriasBase;
import ${project_namespace}.criterias.base.CriteriasBase.GroupType;
import ${project_namespace}.criterias.base.value.ArrayValue;
</#if>

/**
 * ${curr.name?cap_first}ProviderAdapterBase.
 */
public abstract class ${curr.name?cap_first}ProviderAdapterBase 
				extends ProviderAdapterBase<${ext}> {
	
	/** TAG for debug purpose. */
	protected static final String TAG = "${curr.name?cap_first}ProviderAdapter";

	/** ${curr.name?upper_case}_URI. */
	public	  static Uri ${curr.name?upper_case}_URI;

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
	public ${curr.name?cap_first}ProviderAdapterBase(final Context ctx, 
													 final SQLiteDatabase db) {
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
	public int delete(final Uri uri, String selection, 
									 String[] selectionArgs) {
		int matchedUri = ${project_name?cap_first}ProviderBase
					.getUriMatcher().match(uri);
		int result = -1;
		switch (matchedUri) {
			case ${curr.name?upper_case}_ONE:
				int id = Integer.parseInt(uri.getPathSegments().get(1));
				<#if inherited>
				Uri motherUri = Uri.withAppendedPath(
						${extends?cap_first}ProviderAdapter.${extends?upper_case}_URI, String.valueOf(id));
				result = this.ctx.getContentResolver().delete(motherUri,
						selection, selectionArgs);
				<#else>
				selection = ${curr.name?cap_first}SQLiteAdapter.${NamingUtils.alias(curr.ids[0].name)} 
						+ " = ?";
				selectionArgs = new String[1];
				selectionArgs[0] = String.valueOf(id);
				result = this.adapter.delete(
						selection, 
						selectionArgs);
				</#if>
				break;
			case ${curr.name?upper_case}_ALL:
				<#if inherited>
				// Query the ids of the changing fields.
				Cursor idsCursor = this.adapter.query(
						new String[]{${curr.name}SQLiteAdapter.ALIASED_${NamingUtils.alias(curr.ids[0].name)}},
						selection, 
						selectionArgs, 
						null, 
						null, 
						null);
				// If there are ids
				if (idsCursor.getCount() > 0) {
					CriteriasBase parentCrit = this.cursorToIDSelection(idsCursor, ${curr.extends}SQLiteAdapter.ALIASED_${NamingUtils.alias(curr.ids[0].name)});
					String parentSelection = parentCrit.toSQLiteSelection();
					String[] parentSelectionArgs = parentCrit.toSQLiteSelectionArgs();
					result = this.ctx.getContentResolver().delete(
							${curr.extends}ProviderAdapter.${curr.extends?upper_case}_URI,
							parentSelection, 
							parentSelectionArgs);
				}
				<#else>
				result = this.adapter.delete(
							selection, 
							selectionArgs);
				</#if>
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
	public Uri insert(final Uri uri, final ContentValues values) {
		int matchedUri = ${project_name?cap_first}ProviderBase
				.getUriMatcher().match(uri);
		<#if inherited>ContentValues ${curr.name?uncap_first}Values = 
			this.extractContentValues(values);</#if>
		Uri result = null;
		int id = 0;
		switch (matchedUri) {
			case ${curr.name?upper_case}_ALL:
				<#if inherited>
				Uri newUri = this.ctx.getContentResolver().insert(
						${extends}ProviderAdapter.${extends?upper_case}_URI, 
						values);
				int newId = Integer.parseInt(newUri.getPathSegments().get(1));
				${curr.name?uncap_first}Values.put(${curr.name}SQLiteAdapter.${NamingUtils.alias(curr.ids[0].name)}, newId);
				id = (int) this.adapter.insert(null, ${curr.name?uncap_first}Values);
				<#else>
				id = (int) this.adapter.insert(null, values);
				</#if>
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
	public Cursor query(final Uri uri, String[] projection, 
						String selection, String[] selectionArgs, 
						final String sortOrder) {
		int matchedUri = ${project_name?cap_first}ProviderBase.getUriMatcher()
				.match(uri);
		Cursor result = null;
		int id = 0;

		switch (matchedUri) {
			case ${curr.name?upper_case}_ONE:
				id = Integer.parseInt(uri.getPathSegments().get(1));
				selection = ${curr.name?cap_first}SQLiteAdapter.ALIASED_${NamingUtils.alias(curr.ids[0].name)} 
						+ " = ?";
				selectionArgs = new String[1];
				selectionArgs[0] = String.valueOf(id);
				result = this.adapter.query(
							projection, 
							selection,
							selectionArgs, 
							null,
							null,
							sortOrder);
				break;
			case ${curr.name?upper_case}_ALL:
				result = this.adapter.query(
							projection, 
							selection,
							selectionArgs, 
							null,
							null,
							sortOrder);
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
	public int update(final Uri uri, final ContentValues values, 
			final String selection,
			final String[] selectionArgs) {
		<#if inherited>ContentValues ${curr.name?uncap_first}Values = this.extractContentValues(values);</#if>
		int matchedUri = ${project_name?cap_first}ProviderBase.getUriMatcher()
				.match(uri);
		int result = -1;
		switch (matchedUri) {
			case ${curr.name?upper_case}_ONE:
				String id = uri.getPathSegments().get(1);
				<#if inherited>
				Uri parentUri = Uri.withAppendedPath(${extends?cap_first}ProviderAdapter.${extends?upper_case}_URI,
						String.valueOf(id));
				result = this.ctx.getContentResolver().update(
						parentUri,
						values, 
						null,
						null);
				result += this.adapter.update(
						${curr.name?uncap_first}Values, 
						${curr.name}SQLiteAdapter.${NamingUtils.alias(curr.ids[0].name)} + " = ?", 
						new String[]{String.valueOf(id)});
				<#else>
				result = this.adapter.update(
						values, 
						${curr.name?cap_first}SQLiteAdapter.${NamingUtils.alias(curr.ids[0].name)} + " = " 
						+ id, 
						selectionArgs);
				</#if>
				break;
			case ${curr.name?upper_case}_ALL:
				<#if inherited>
				// Query the ids of the changing fields.
				Cursor idsCursor = this.adapter.query(
						new String[]{${curr.name}SQLiteAdapter.ALIASED_${NamingUtils.alias(curr.ids[0].name)}},
						selection, 
						selectionArgs, 
						null, 
						null, 
						null);
				// If there are ids
				if (idsCursor.getCount() > 0) {
					// If there are values in this table
					if (${curr.name?uncap_first}Values.size() > 0) {
						CriteriasBase currentCrit = this.cursorToIDSelection(
								idsCursor,
								${curr.name}SQLiteAdapter.${NamingUtils.alias(curr.ids[0].name)});

						String currentSelection = currentCrit.toSQLiteSelection();
						String[] currentSelectionArgs = currentCrit
								.toSQLiteSelectionArgs();
						// Update the current table 
						result += this.adapter.update(
								${curr.name?uncap_first}Values, 
								currentSelection, 
								currentSelectionArgs);
					}
					// If there are still values to be updated in parents
					if (values.size() > 0) {
						CriteriasBase parentCrit = this.cursorToIDSelection(
								idsCursor, 
								${curr.extends}SQLiteAdapter.${NamingUtils.alias(curr.ids[0].name)});

						String parentSelection = parentCrit.toSQLiteSelection();
						String[] parentSelectionArgs = parentCrit
								.toSQLiteSelectionArgs();
						// Update the parents tables 
						result = this.ctx.getContentResolver().update(
								${curr.extends}ProviderAdapter.${curr.extends?upper_case}_URI,
								values, 
								parentSelection, 
								parentSelectionArgs);
					}
				}
				<#else>
				result = this.adapter.update(
							values, 
							selection, 
							selectionArgs);
				</#if>
				break;
			default:
				result = -1;
				break;
		}
		return result;
	}

	<#if inherited>
	protected ContentValues extractContentValues(ContentValues from) {
		ContentValues to = new ContentValues();
		for (String colName : ${curr.name}SQLiteAdapter.COLS) {
			if (from.containsKey(colName)) {
				this.transfer(from, to, colName, false);
			}
		}
		return to;
	}
	
	protected void transfer(ContentValues from, 
			ContentValues to,
			String colName,
			boolean keep) {
		to.put(colName, from.getAsString(colName));
		if (!keep) {
			from.remove(colName);
		}
	}

	protected CriteriasBase cursorToIDSelection(Cursor cursor, String key) {
		${curr.name}Criterias crit = new ${curr.name}Criterias(GroupType.AND);
		Criteria inCrit = new Criteria();
		inCrit.setKey(key);
		inCrit.setType(Type.IN);
		ArrayValue inArray = new ArrayValue();
		cursor.moveToFirst();
		do {
			inArray.addValue(cursor.getString(
				cursor.getColumnIndex(${curr.name}SQLiteAdapter.${NamingUtils.alias(curr.ids[0].name)})));
		} while (cursor.moveToNext());
		inCrit.addValue(inArray);
		crit.add(inCrit);
		return crit;
	}
	</#if>

	/**
	 * Get the entity URI.
	 * @return The URI
	 */
	@Override
	public Uri getUri() {
		return ${curr.name?upper_case}_URI;
	}
}

